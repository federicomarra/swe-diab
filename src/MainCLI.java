import handheldTracker.UserInterface;

import java.util.Scanner;

public class MainCLI {
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();

        System.out.println("\n\nStarting simulation:\n");

        int mode;
        do {
            System.out.println("Press 0. to Exit, 1. New bolus, 2. Update profile");
            mode = (int) safeInput("mode", 0, 2, 1f);
            if (mode == 1) {
                System.out.print("Select bolus type: ");
                System.out.println("0. to Exit, 1. Standard Bolus, 2. Extended Bolus, 3. Manual Bolus, 4. Pen Bolus");
                mode = (int) safeInput("mode", 0, 4, 1f);
            } else if (mode == 2) {
                System.out.print("Select profile type: ");
                System.out
                        .println("0. to Exit, 1. Basal Profile, 2. Carb Ratio Profile, 3. Insulin Sensitivity Profile");
                mode = (int) safeInput("mode", 0, 3, 1f);
                mode += (mode == 0 ? 0 : 4);
            }
            if (mode >= 1 && mode <= 3) {
                int carb = (int) safeInput("carbohydrates", 0, 150, 1f);

                switch (mode) {
                    case 1:
                        ui.newStandardBolus(carb);
                        break;
                    case 2:
                        int delay = (int) safeInput("delay in minutes", 1, 60, 1f);
                        ui.newExtendedBolus(carb, delay);
                        break;
                    case 3:
                        ui.howManyUnits(carb);
                        break;
                }
            } else if (mode == 4) {
                float units = safeInput("units", 0, 15, 1f);
                ui.newPenBolus(units);
            } else if (mode >= 5 && mode <= 7) {
                int hour = (int) safeInput("hour", 0, 24, 1f);
                float units;

                switch (mode) {
                    // Update Basal Profile
                    case 5:
                        units = safeInput("units", 0, 5, 0.05f);
                        ui.updateBasalProfile(units, hour);
                        break;
                    // Update Carb Ratio Profile
                    case 6:
                        units = safeInput("units", 1, 15, 1f);
                        ui.updateCarbRatioProfile(units, hour);
                        break;
                    // Update Insulin Sensitivity Profile
                    case 7:
                        units = safeInput("units", 20, 50, 1f);
                        ui.updateInsulinSensitivityProfile(units, hour);
                        break;
                }
            }
            System.out.println();
        } while (mode > 0);
        System.out.println("Exiting...");
    }

    public static float safeInput(String nameVar, int min, int max, float sensitivity) {
        float value;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Insert " + nameVar + ": ");
        while (true) {
            if (scanner.hasNextFloat()) {
                value = scanner.nextFloat();
                if (value < min || value > max || value % sensitivity != 0) {
                    System.out.print("Insert a number between " + min + " and " + max
                            + (sensitivity == 1 ? "" : " with a sensitivity of " + sensitivity) + ". Try again: ");
                } else {
                    return value;
                }
            } else {
                System.out.println("Invalid input. Please enter a valid float number.");
                // Consume invalid input
                scanner.next();
                System.out.print("Insert " + nameVar + ": ");
            }
        }

    }
}
