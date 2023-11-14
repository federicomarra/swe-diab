package handheldTracker;

import utils.*;

import java.util.Scanner;

public class UserInterface {
    private final LocalDatabase localDb;
    // TODO: put this instead?
    // private final LocalDatabase localDb;

    public UserInterface() {
        // One localDb for one user
        localDb = new LocalDatabase();
    }

    public void newStandardBolus(int carb) {
        localDb.newBolus(0, 0, BolusMode.STANDARD, carb);
    }

    public void newExtendedBolus(int carb, int delayMinutes) { // delay in minutes
        localDb.newBolus(0, delayMinutes * 60, BolusMode.EXTENDED, carb);  // delay in seconds
    }

    public void howManyUnits(int carb) {
        localDb.newBolus(0, 0, BolusMode.MANUAL, carb);
    }

    public void newPenBolus(float units) {
        localDb.newBolus(units, 0, BolusMode.PEN, 0);
    }

    public void updateBasalProfile(float units, int hour) {
        localDb.updateHourlyFactor(new HourlyFactor(units, hour), ProfileMode.BASAL);
    }

    public void updateCarbRatioProfile(int units, int hour) {
        localDb.updateHourlyFactor(new HourlyFactor(units, hour), ProfileMode.IC);
    }

    public void updateInsulinSensitivityProfile(int units, int hour) {
        localDb.updateHourlyFactor(new HourlyFactor(units, hour), ProfileMode.IG);
    }

    public static float safeInput(String nameVar, int min, int max, float sensitivity) {
        float value;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Insert " + nameVar + ": ");
        while (true) {
            if (scanner.hasNextFloat()) {
                value = scanner.nextFloat();
                if (value < min || value > max || value % sensitivity != 0) {
                    System.out.print("Insert a number between " + min + " and " + max + (sensitivity == 1 ? "" : " with a sensitivity of " + sensitivity) + ". Try again: ");
                } else {
                    break;
                }
            } else {
                System.out.println("Invalid input. Please enter a valid float number.");
                scanner.next(); // Consume invalid input
                System.out.print("Insert " + nameVar + ": ");
            }
        }
        return value;
    }
}
