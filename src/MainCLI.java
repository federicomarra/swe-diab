import handheldTracker.BolusDelivery;
import handheldTracker.BolusMode;
import handheldTracker.UserInterface;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class MainCLI {
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();

        System.out.println("\n\nStarting simulation:\n");

        int mode;
        int selection;
        do {
            selection = 0;
            System.out.println("0. Exit, 1. New bolus, 2. Update profile, 3. How many units left, 4. Backup");
            mode = (int) safeInput("mode", 0, 4, 1f);
            if (mode == 1) {
                System.out.print("Select bolus type: ");
                System.out.println("0. Exit, 1. Standard Bolus, 2. Extended Bolus, 3. Manual Bolus, 4. Pen Bolus");
                selection = (int) safeInput("mode", 0, 4, 1f);
            } else if (mode == 2) {
                System.out.print("Select profile type: ");
                System.out
                        .println("0. Exit, 1. Basal Profile, 2. Carb Ratio Profile, 3. Insulin Sensitivity Profile");
                selection = (int) safeInput("mode", 0, 3, 1f);
            } else if (mode == 3) {
                BolusDelivery bd = new BolusDelivery(0, null, BolusMode.STANDARD);
                System.out.print("There is " + bd.calculateResidualUnits(ui.getDb().bolusDeliveries) + " units.\n");
                selection = 1;
            } else if (mode == 4) {
                ui.backup();
                selection = 1;
            }
            if (mode == 1 && selection >= 1 && selection <= 4) {
                int carb = 0;
                if (selection != 4)
                    carb = (int) safeInput("carbohydrates", 0, 150, 1f);
                switch (selection) {
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
                    case 4:
                        float units = safeInput("units", 0, 15, 0.5f);
                        ui.newPenBolus(units);
                        break;
                }
            } else if (mode == 2 && selection >= 1 && selection <= 3) {
                int hour = (int) safeInput("hour", 0, 24, 1f);
                float units;
                switch (selection) {
                    // Update Basal Profile
                    case 1:
                        units = safeInput("units", 0, 5, 0.05f);
                        ui.updateBasalProfile(units, hour);
                        break;
                    // Update Carb Ratio Profile
                    case 2:
                        units = safeInput("units", 1, 15, 1f);
                        ui.updateCarbRatioProfile(units, hour);
                        break;
                    // Update Insulin Sensitivity Profile
                    case 3:
                        units = safeInput("units", 20, 50, 1f);
                        ui.updateInsulinSensitivityProfile(units, hour);
                        break;
                }
            }
            System.out.println();
        } while (selection > 0);
        System.out.println("Exiting...");
    }

    public static float safeInput(String nameVar, int min, int max, float sensibility) {
        float value;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Insert " + nameVar + ": ");
            try {
                value = Float.parseFloat(scanner.next().replace(",", "."));
            } catch (NumberFormatException e) {
                value = min - 1;
                System.out.println("Invalid input. Please enter a valid float number.");
            }

            BigDecimal n = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
            float delta = n.remainder(new BigDecimal(sensibility).setScale(2, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP).floatValue();

            if (value < min || value > max || delta != 0) {
                System.out.println("Insert a number between " + min + " and " + max
                        + (delta != 0 ? " with a sensibility of " + sensibility : "") + ", try again.");
            } else {
                return value;
            }
        }

    }
}
