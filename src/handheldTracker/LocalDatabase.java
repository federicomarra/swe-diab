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
            if (measurements.size() == 0)  // if no mesurament is present
                addMeasurement(manager.newMeasurement());   // get new mesurament
            Measurement lm = measurements.get(measurements.size() - 1);  // last mesurament
            Duration diff = Duration.between(lm.time(), time);
            if (diff.toMinutes() > 10)  // if last mesurament is older than 10 minutes
                lm = addMeasurement(manager.newMeasurement());   // get new mesurament and add to measurements
            HourlyFactor sensitivity = insulinSensitivityProfile.hourlyFactors[LocalTime.now().getHour()];
            HourlyFactor carbRatio = carbRatioProfile.hourlyFactors[LocalTime.now().getHour()];
            int referement = 120;
            BolusDelivery bd = new BolusDelivery(0, time, mode);

            if (lm.glycemia() > 180)
                bd.units += ((float) (lm.glycemia() - referement)) / sensitivity.units();
            if (carb > 0 && carb <= 150)
                bd.units += carb / carbRatio.units();
            bd.units -= bd.calculateResidualUnits(bolusDeliveries);
            bd.units = Math.round(bd.units / 0.01f) * 0.01f; // round to 2 decimal places
            if (bd.units > 0) {
                addBolus(bd);
                BigDecimal u = new BigDecimal(bd.units);                       // parse first row into BigDecimal
                u = u.setScale(2, RoundingMode.HALF_UP);
                switch (mode) {
                    case STANDARD:
                        System.out.println("With a glycemia of " + lm.glycemia() + " and " + carb + " carbs, you should inject " + Math.round(bd.units) + " units");
                        System.out.println("...injecting " + u + " units" + " at " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));   //TODO: comment
                        //manager.verifyAndInject(bd.units); TODO: uncomment
                        break;
                    case EXTENDED:
                        //while (time.getMinute() == LocalTime.now().getMinute())   // wait until time is reached
                        //while ((Duration.between(LocalTime.now(), time)).toMinutes() <= 1); // wait until time is reached
                        System.out.println("Waiting " + (Duration.between(LocalTime.now(), time)).toMinutes() + " minutes to inject " + Math.round(bd.units) + " units");
                        Thread.sleep(Duration.between(LocalTime.now(), time).toMillis());
                        System.out.println("Injecting " + u + " units"); //TODO: comment
                        //manager.verifyAndInject(bd.units); TODO: uncomment
                        break;
                    case MANUAL:
                        u = u.divide(new BigDecimal("0.05"), 0, RoundingMode.HALF_UP).multiply(new BigDecimal("0.05")); // round with 0.05 sensibility
                        System.out.println("With a glycemia of " + lm.glycemia() + " and " + carb + " carbs, you should inject " + u + " units");
                        break;
                }
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
}
