package handheldTracker;

import cloudInterface.BackupDatabase;
import glucoseDeliverySystem.PumpManager;
import utils.*;
import exceptions.BluetoothException;
import exceptions.InternetException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LocalDatabase extends Database implements Observer {
    private PumpManager manager;
    private BackupDatabase backupDb;
    public HourlyProfile carbRatioProfile;
    public HourlyProfile insulinSensitivityProfile;
    public HourlyProfile basalProfile;
    public List<BolusDelivery> bolusDeliveries;
    public List<Measurement> measurements;

    public LocalDatabase() {
        this.backupDb = new BackupDatabase();

        this.insulinSensitivityProfile = new HourlyProfile(ProfileMode.IG);
        this.basalProfile = new HourlyProfile(ProfileMode.BASAL);
        this.carbRatioProfile = new HourlyProfile(ProfileMode.IC);
        this.bolusDeliveries = new ArrayList<>();
        this.measurements = new ArrayList<>();
        System.out.println("LocalDatabase created");
        this.manager = new PumpManager(insulinSensitivityProfile);
        System.out.println("PumpManager created");
        // manager.subscribe(this);
    }

    public void update(List<Measurement> ms) {
        try {
            super.update(ms);
            backup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void newBolus(float units, int delay, BolusMode mode, int carb) {
        switch (mode) {
            case STANDARD, MANUAL:  //computes and injects
                try {
                    computeAndInject(LocalTime.now(), carb, mode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case EXTENDED:  //calculates timeDelayed and computes and injects
                try {
                    LocalTime timeDelayed = LocalTime.now().plusMinutes(delay); // delay in minutes
                    computeAndInject(timeDelayed, carb, mode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case PEN:       //adds bolus to localDb
                addBolus(new BolusDelivery(units, LocalTime.now(), mode));
                break;
        }
        //TODO: add bolus to localDb?
        //localDb.addBolus(new BolusDelivery(units, time, mode));
    }

    private void computeAndInject(LocalTime time, int carb, BolusMode mode) throws BluetoothException {
        try {
            // get last measurement, if older than 10 minutes, get new measurement
            if (measurements.isEmpty())  // if no measurement is present
                addMeasurement(manager.newMeasurement());   // get new measurement
            Measurement lm = measurements.get(measurements.size() - 1);  // last measurement
            Duration diff = Duration.between(lm.time(), time);  // difference between last measurement and bolus time
            if (diff.toMinutes() > 10)  // if last measurement is older than 10 minutes ago
                lm = addMeasurement(manager.newMeasurement());   // get new measurement and add to measurements

            // get actual hourly factors from profiles
            HourlyFactor sensitivity = insulinSensitivityProfile.hourlyFactors[LocalTime.now().getHour()];
            HourlyFactor carbRatio = carbRatioProfile.hourlyFactors[LocalTime.now().getHour()];

            // set a reference glycemia of 120 mg/dL
            int glycReference = 120;

            // create BolusDelivery object
            BolusDelivery bd = new BolusDelivery(0, time, mode);

            // compute units of correction, units of carbohydrates and total units
            float glycUnits = 0;
            if (lm.glycemia() > 160)
                glycUnits = ((float) (lm.glycemia() - glycReference)) / sensitivity.units();
            float activeUnits = bd.calculateResidualUnits(bolusDeliveries);
            float correctionUnits = glycUnits - activeUnits; // units of correction = units of glycemia - units of active insulin
            float carbUnits = 0;
            if (carb > 0 && carb <= 150)
                carbUnits = carb / carbRatio.units();

            // round to 2 decimal places
            bd.units = RoundToCent(correctionUnits + carbUnits, "0.01");
            glycUnits = RoundToCent(glycUnits, "0.01");
            activeUnits = RoundToCent(activeUnits, "0.01");
            correctionUnits = RoundToCent(correctionUnits, "0.01");
            carbUnits = RoundToCent(carbUnits, "0.01");

            if (bd.units > 0) {
                // PRINTF VERSION
                System.out.printf("%-15s%-15s%-17s%-14s%n", "Glycemia:", (lm.glycemia()<100 ? " " : "") + (lm.glycemia()<10 ? " " : "") + lm.glycemia() + " mg/dL", (glycUnits > 0 ? glycUnits + " units" : ""), (correctionUnits != 0 ?  "correction" : ""));
                System.out.printf("%-15s%-15s%-17s%-15s%n", "Active units:", "" , (activeUnits > 0 ? activeUnits*(-1) + " units" : ""), (correctionUnits != 0 ? correctionUnits + " units" : ""));
                System.out.printf("%-15s%-15s%-17s%n", "Carbohydrates:", (carb<100 ? " " : "") + (carb<10 ? " " : "") + carb + " g", (carbUnits > 0 ? carbUnits + " units" : ""));
                System.out.printf("%-15s%-15s%-17s%n", "Total units:", "" , (bd.units > 0 ? bd.units + " units" : "") + "\n");

                /*  //PRINTLN VERSION
                System.out.println("With " + (lm.glycemia()<100 ? " " : "") + lm.glycemia() + " mg/dL of glycemia" + (glycUnits > 0 ? ",                   you should inject: " + glycUnits + " units" : ""));
                if (activeUnits > 0)
                    System.out.println((glycUnits > 0 ? "                                    y" : "Y") + "ou already have a total of: " + activeUnits + " units active");

                if (correctionUnits > 0)
                    System.out.println("You're going to inject: " + correctionUnits + " units of correction");
                else if (correctionUnits < 0)
                    System.out.println("You're going to subtract: " + correctionUnits + " units of surplus");
                else if (correctionUnits == 0) {
                    System.out.println("You're not going to inject units of correction");
                }
                if (carbUnits > 0)
                    System.out.println("You're going to eat: " + (carb<100 ? " " : "") + (carb<10 ? " " : "") + carb + " g of carbohydrates, you should inject: " + carbUnits + " units");
                System.out.println("You should inject: " + bd.units + " units\n");
                */

                switch (mode) {
                    case STANDARD:
                        //System.out.println("Now injecting: " + bd.units + " units" + " at " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));   //TODO: comment
                        manager.verifyAndInject(bd.units); //TODO: uncomment
                        break;
                    case EXTENDED:
                        System.out.println("Waiting " + ((Duration.between(LocalTime.now(), time)).toMinutes() + 1) + " minute" + (((Duration.between(LocalTime.now(), time)).toMinutes() == 0) ? "" : "s") + " to inject " + bd.units + " units" + " at " + time.format(DateTimeFormatter.ofPattern("HH:mm")));   //TODO: comment
                        Thread.sleep(Duration.between(LocalTime.now(), time).toMillis());
                        System.out.println("Now injecting: " + bd.units + " units"); //TODO: comment
                        //manager.verifyAndInject(bd.units); TODO: uncomment
                        break;
                    case MANUAL:
                        bd.units = RoundToCent(bd.units, "0.5"); // round to 2 decimal places
                        System.out.println("Manually inject: " + bd.units + " units");
                        break;
                }
                addBolus(bd);   // add bolus to bolusDeliveries
            } else {
                System.out.println("With " + lm.glycemia() + " mg/dL of glycemia");
                System.out.println("You don't need to inject insulin");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // throw new BluetoothException();
        }
    }

    private void backup() throws InternetException {
        try {
            backupDb.update(this.measurements); //update() to implemented into Database.java
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateHourlyFactor(HourlyFactor hf, ProfileMode mode) {
        switch (mode) {
            case BASAL:
                basalProfile.updateHourlyFactor(hf);
                break;
            case IC:
                insulinSensitivityProfile.updateHourlyFactor(hf);
                manager = new PumpManager(this.insulinSensitivityProfile);
                break;
            case IG:
                carbRatioProfile.updateHourlyFactor(hf);
                break;
        }
        try {
            backup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addBolus(BolusDelivery bd) {
        bolusDeliveries.add(bd);
    }

    private Measurement addMeasurement(Measurement m) {
        measurements.add(m);
        return m;
    }

    private float RoundToCent(float f, String sensibility) {    // replacable with String.format("%.2f", units)
        BigDecimal n = new BigDecimal(f);
        n = n.setScale(2, RoundingMode.HALF_UP);
        n = n.divide(new BigDecimal(sensibility), 0, RoundingMode.HALF_UP).multiply(new BigDecimal(sensibility));
        return n.floatValue();
    }
}
