
//import static utils.ReadCSV.*;

import handheldTracker.UserInterface;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome in the Glucose Monitoring System");

        UserInterface ui = new UserInterface();
        /*  // uncomment to test parsing of csv files (put in HourlyProfile constructor)
        float[] bf = ReadFloatCSV("csv/basalProfile.csv");
        int[] cb = ReadIntCSV("csv/carbRatio.csv");
        int[] is = ReadIntCSV("csv/insulinSensitivity.csv");
        for (int i = 0; i < 24; i++) {
            ui.updateBasalProfile(bf[i], i);
            ui.updateCarbRatioProfile(cb[i], i);
            ui.updateInsulinSensitivityProfile(is[i], i);
        }
        */

        //ui.newPenBolus(1.5f);
        //ui.howManyUnits(56);
        //ui.newStandardBolus(53);

        System.out.println("\n\nStarting simulation:\n");

        // randomization carb, mode, delay and active insulin
        int carb = (int) (Math.abs(Math.random() * 300 - 150));     // randomize carb between -150 and 150 put in absolute value
        int mode = (int) (Math.abs(Math.random() * 3));             // randomize mode between 0 and 2
        int delay = (int) (Math.abs(Math.random() * 3)+1);          // randomize delay between 1 and 3
        ui.newPenBolus((float) (Math.abs(Math.random() * 5)));      // randomize residual units (still active insulin)

        switch (mode) {
            case 0:
                System.out.println("Mode: Standard Bolus");
                ui.newStandardBolus(carb);
                break;
            case 1:
                System.out.println("Mode: Extended Bolus");
                ui.newExtendedBolus(carb, delay);
                break;
            case 2:
                System.out.println("Mode: Manual Bolus");
                ui.howManyUnits(carb);
                break;
        }

    }


}