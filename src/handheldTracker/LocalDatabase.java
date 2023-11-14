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

    // Set the reference glycemia to 120 mg/dL
    private final int GLYC_REFERENCE = 120;

    public LocalDatabase() {
        this.backupDb = new BackupDatabase();

        this.insulinSensitivityProfile = new HourlyProfile(ProfileMode.IG);
        this.basalProfile = new HourlyProfile(ProfileMode.BASAL);
        this.carbRatioProfile = new HourlyProfile(ProfileMode.IC);
        this.bolusDeliveries = new ArrayList<>();
        this.measurements = new ArrayList<>();
        System.out.println("LocalDatabase created");
        this.manager = PumpManager.getInstance(insulinSensitivityProfile);
        System.out.println("PumpManager created");

        // TODO: Check if this is needed
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

    public void newBolus(float units, int delaySeconds, BolusMode mode, int carb) {    // delay in seconds
        switch (mode) {
            case STANDARD:
                System.out.println("Mode: Standard Bolus");
            case MANUAL:
                System.out.println("Mode: Manual Bolus");
                // Computes and injects
                try {
                    computeAndInject(LocalTime.now(), carb, mode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case EXTENDED:
                // Calculates timeDelayed, computes and injects
                System.out.println("Mode: Extended Bolus");
                try {
                    LocalTime timeDelayed = LocalTime.now().plusSeconds(delaySeconds);
                    computeAndInject(timeDelayed, carb, mode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case PEN:
                // Adds bolus to localDb
                System.out.println("Mode: Pen Bolus");
                addBolus(new BolusDelivery(units, LocalTime.now(), mode));
                System.out.println("You have injected: " + units + " units");
                System.out.println();
                break;
        }
    }

    private void computeAndInject(LocalTime time, int carb, BolusMode mode) throws BluetoothException {
        try {
            // Get the last measurement if it exists
            if (measurements.isEmpty())
                addMeasurement(manager.newMeasurement());

            Measurement lm = measurements.get(measurements.size() - 1);
            // Difference between last measurement and bolus time
            Duration diff = Duration.between(lm.time(), time);

            // Make a new measurement if the last one is older than 10 minutes from now
            if (diff.toMinutes() > 10)
                lm = addMeasurement(manager.newMeasurement());

            // Get the actual hourly factors from profiles
            HourlyFactor sensitivity = insulinSensitivityProfile.hourlyFactors[LocalTime.now().getHour()];
            HourlyFactor carbRatio = carbRatioProfile.hourlyFactors[LocalTime.now().getHour()];

            // Create the BolusDelivery object
            BolusDelivery bd = new BolusDelivery(0, time, mode);

            // Compute the units of correction, units of carbohydrates and total units
            float glycUnits = 0;
            if (lm.glycemia() > 160)
                glycUnits = ((float) (lm.glycemia() - GLYC_REFERENCE)) / sensitivity.units();
            float activeUnits = bd.calculateResidualUnits(bolusDeliveries);
            // Units of correction = Units of glycemia - Units of active insulin
            float correctionUnits = glycUnits - activeUnits;
            float carbUnits = 0;
            if (carb > 0 && carb <= 150)
                carbUnits = carb / carbRatio.units();

            // Round the results to 2 decimal places
            bd.units = RoundToCent(correctionUnits + carbUnits, "0.01");
            glycUnits = RoundToCent(glycUnits, "0.01");
            activeUnits = RoundToCent(activeUnits, "0.01");
            correctionUnits = RoundToCent(correctionUnits, "0.01");
            carbUnits = RoundToCent(carbUnits, "0.01");

            if (bd.units > 0) {
                System.out.printf("%-16s%9s%14s%-18s%n", "Glycemia:", lm.glycemia() + " mg/dL", (glycUnits > 0 ? " " + glycUnits + " units" : ""), (correctionUnits != 0 ? "    correction" : ""));
                System.out.printf("%-25s%14s%-18s%n", "Active insulin:", (activeUnits > 0 ? "-" : "") + activeUnits + " units", "    " + (correctionUnits != 0 ? correctionUnits + " units" : ""));
                System.out.printf("%-16s%9s%14s%n", "Carbohydrates:", carb + " g    ", (carbUnits > 0 ? " " + carbUnits + " units" : ""));
                System.out.printf("%-25s%14s%n%n", "Total insulin:", (bd.units > 0 ? " " + bd.units + " units" : ""));

                switch (mode) {
                    case STANDARD:
                        manager.verifyAndInject(bd.units);
                        break;
                    case EXTENDED:
                        Duration delay = Duration.between(LocalTime.now(), time);
                        System.out.print("Waiting ");
                        int hours = (int) delay.toHours();
                        int minutes = (int) delay.toMinutes() % 60;
                        int seconds = (int) delay.toSeconds() % 60;
                        if (hours > 0) {
                            System.out.print(hours + " hour" + ((hours == 1) ? "" : "s"));
                            if (minutes > 0) {
                                if (seconds > 0) System.out.print(", ");
                                else System.out.print(" and ");
                            }
                        }
                        if (minutes > 0) {
                            System.out.print(minutes + " minute" + ((minutes == 1) ? "" : "s"));
                            if (seconds > 0) System.out.print(" and ");
                        }
                        if (seconds > 0) System.out.print(seconds + 1 + " second" + ((seconds == 1) ? "" : "s"));
                        // TODO: FIX if delaySeconds is 61, it doesn't print 1 minute and 1 second

                        System.out.println(" to inject " + bd.units + " units" + " at " + time.format(DateTimeFormatter.ofPattern("HH:mm")));
                        Thread.sleep(delay.toMillis());
                        manager.verifyAndInject(bd.units);
                        break;
                    case MANUAL:
                        // Round the units to 0.5
                        bd.units = RoundToCent(bd.units, "0.5");
                        System.out.println("Manually inject: " + bd.units + " units");
                        break;
                    // FIXME: Add pen case
                }
                addBolus(bd);
            } else if (bd.units == 0) {
                System.out.println("You don't need to inject insulin, you have a glycemia of: " + lm.glycemia() + " mg/dL");
            } else if (bd.units < 0) {
                System.out.println("You don't need to inject insulin, you have " + activeUnits + " units of active insulin");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // throw new BluetoothException();
        }
    }

    private void backup() throws InternetException {
        try {
            // FIXME: update() to be implemented into Database.java
            backupDb.update(this.measurements);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateHourlyFactor(HourlyFactor hf, ProfileMode mode) {
        System.out.println("Updating " + mode.toString() + " profile...");
        switch (mode) {
            case BASAL:
                basalProfile.updateHourlyFactor(hf);
                break;
            case IC:
                carbRatioProfile.updateHourlyFactor(hf);
                break;
            case IG:
                insulinSensitivityProfile.updateHourlyFactor(hf);
                manager = PumpManager.getInstance(insulinSensitivityProfile);

                // TODO: Check if this is needed
                // manager.subscribe(this);
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

    // FIXME: Replaceable with String.format("%.2f", units)
    private float RoundToCent(float f, String sensibility) {
        BigDecimal n = new BigDecimal(f);
        n = n.setScale(2, RoundingMode.HALF_UP);
        n = n.divide(new BigDecimal(sensibility), 0, RoundingMode.HALF_UP).multiply(new BigDecimal(sensibility));
        return n.floatValue();
    }
}
