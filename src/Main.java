import handheldTracker.UserInterface;
import static handheldTracker.UserInterface.safeInput;

public class Main {
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();

        System.out.println("\n\nStarting simulation:\n");

        //boolean input = true;
        ///*
        boolean input;
        if (safeInput("1 to insert values, 2 to randomize", 1, 2) == 1) input = true;
        else input = false;
        //*/

        if (!input) {     // Random mode
            System.out.println("Random mode selected: ");
            // Randomize carb between -150 and 150 put in absolute value
            int carb = (int) (Math.abs(Math.random() * 300 - 150));
            // Randomize mode between 0 and 2
            int mode = (int) (Math.abs(Math.random() * 3));
            // Randomize delay between 1 and 3
            int delay = (int) (Math.abs(Math.random() * 3) + 1);
            // Randomize residual units (still active insulin)
            ui.newPenBolus((float) (((int) Math.abs(Math.random() * 29) + 1) * 0.5));

            switch (mode) {
                case 0:
                    ui.newStandardBolus(carb);
                    break;
                case 1:
                    ui.newExtendedBolus(carb, delay);
                    break;
                case 2:
                    ui.howManyUnits(carb);
                    break;
            }

        } else {        // Input mode
            System.out.println("Input mode selected: ");
            int mode;
            do {
                System.out.println("Press 0. to Exit, 1. New bolus, 2. Update profile");
                mode = (int) safeInput("mode", 0, 2);
                if (mode == 1) {
                    System.out.print("Select bolus type: ");
                    System.out.println("1. Standard Bolus, 2. Extended Bolus, 3. Manual Bolus, 4. Pen Bolus");
                    mode = (int) safeInput("mode", 1, 4);
                } else if (mode == 2) {
                    System.out.print("Select profile type: ");
                    System.out.println("1. Basal Profile, 2. Carb Ratio Profile, 3. Insulin Sensitivity Profile");
                    mode = (int) safeInput("mode", 1, 3) + 4;
                }
                if (mode >= 1 && mode <= 3) {
                    int carb = (int) safeInput("carbohydrates", 0, 150);
                    switch (mode) {
                        case 1:
                            ui.newStandardBolus(carb);
                            break;
                        case 2:
                            int delay = (int) safeInput("delay in minutes", 1, 5);
                            ui.newExtendedBolus(carb, delay);
                            break;
                        case 3:
                            ui.howManyUnits(carb);
                            break;
                    }
                } else if (mode >= 5 && mode <= 7) {
                    int hour = (int) safeInput("hour", 0, 23);
                    float units;
                    switch (mode) {
                        case 5: // Update Basal Profile
                            units = safeInput("units", 1, 5);
                            ui.updateBasalProfile(units, hour);
                            break;
                        case 6: // Update Carb Ratio Profile
                            units = safeInput("units", 1, 15);
                            ui.updateCarbRatioProfile((int)units, hour);
                            break;
                        case 7: // Update Insulin Sensitivity Profile
                            units = safeInput("units", 1, 50);
                            ui.updateInsulinSensitivityProfile((int)units, hour);
                            break;
                    }
                }
                //long mode
                /*
                System.out.println("1. Bolus");
                System.out.println("1. Standard Bolus");
                System.out.println("2. Extended Bolus");
                System.out.println("3. Manual Bolus");
                System.out.println("4. Pen Bolus");
                System.out.println("5. Update Basal Profile");
                System.out.println("6. Update Carb Ratio Profile");
                System.out.println("7. Update Insulin Sensitivity Profile");
                mode = (int) safeInput("mode", 0, 7);

                switch (mode) {
                    case 0: // Exit
                        System.out.println("Exiting...");
                        //System.exit(0);
                        break;
                    case 1: // Standard Bolus
                        int carb1 = (int) safeInput("carbohydrates", 0, 150);
                        ui.newStandardBolus(carb1);
                        break;
                    case 2: // Extended Bolus
                        int carb2 = (int) safeInput("carbohydrates", 0, 150);
                        int delay = (int) safeInput("delay in minutes", 1, 5);
                        ui.newExtendedBolus(carb2, delay);
                        break;
                    case 3: // Manual Bolus
                        int carb3 = (int) safeInput("carbohydrates", 0, 150);
                        ui.howManyUnits(carb3);
                        break;
                    case 4: // Pen Bolus
                        float units4 = safeInput("units", 0, 15);
                        ui.newPenBolus(units4);
                        break;
                    case 5: // Update Basal Profile
                        int hour1 = (int) safeInput("hour", 0, 23);
                        float units1 = safeInput("units", 0, 5);
                        ui.updateBasalProfile(units1, hour1);
                        break;
                    case 6: // Update Carb Ratio Profile
                        int hour2 = (int) safeInput("hour", 0, 23);
                        int units2 = (int) safeInput("units", 0, 15);
                        ui.updateCarbRatioProfile(units2, hour2);
                        break;
                    case 7: // Update Insulin Sensitivity Profile
                        int hour3 = (int) safeInput("hour", 0, 23);
                        int units3 = (int) safeInput("units", 0, 50);
                        ui.updateInsulinSensitivityProfile(units3, hour3);
                        break;
                    default:
                        System.out.println("Selezione non valida");
                }

                 */
            } while (mode > 0);
        }
    }
}
