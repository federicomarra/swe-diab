import handheldTracker.UserInterface;

import static handheldTracker.UserInterface.safeInput;

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
                System.out.println("0. to Exit, 1. Basal Profile, 2. Carb Ratio Profile, 3. Insulin Sensitivity Profile");
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
                int hour = (int) safeInput("hour", 0, 23, 1f);
                float units;
                switch (mode) {
                    case 5: // Update Basal Profile
                        units = safeInput("units", 0, 5, 0.05f);
                        ui.updateBasalProfile(units, hour);
                        break;
                    case 6: // Update Carb Ratio Profile
                        units = safeInput("units", 1, 15, 1f);
                        ui.updateCarbRatioProfile((int) units, hour);
                        break;
                    case 7: // Update Insulin Sensitivity Profile
                        units = safeInput("units", 20, 50, 1f);
                        ui.updateInsulinSensitivityProfile((int) units, hour);
                        break;
                }
            }
        } while (mode > 0);
        System.out.println("Exiting...");
    }
}

