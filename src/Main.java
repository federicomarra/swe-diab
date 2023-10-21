
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

        int carb = (int) (Math.abs(Math.random() * 300 - 150));
        int mode = (int) (Math.abs(Math.random() * 3));
        switch (mode) {
            case 0:
                System.out.println("Mode: Standard Bolus");
                ui.newStandardBolus(carb);
                break;
            case 1:
                System.out.println("Mode: Extended Bolus");
                ui.newExtendedBolus(carb, 3);
                break;
            case 2:
                System.out.println("Mode: Manual Bolus");
                ui.howManyUnits(carb);
                break;
        }

    }


}