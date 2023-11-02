
// import static utils.ReadCSV.*;

import handheldTracker.UserInterface;

public class Main {
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        
        // Uncomment to test parsing of csv files (put in HourlyProfile constructor)
        /*
        float[] bf = ReadFloatCSV("csv/basalProfile.csv");
        int[] cb = ReadIntCSV("csv/carbRatio.csv");
        int[] is = ReadIntCSV("csv/insulinSensitivity.csv");
        for (int i = 0; i < 24; i++) {
            ui.updateBasalProfile(bf[i], i);
            ui.updateCarbRatioProfile(cb[i], i);
            ui.updateInsulinSensitivityProfile(is[i], i);
        }
        */

        // ui.newPenBolus(1.5f);
        // ui.howManyUnits(56);
        // ui.newStandardBolus(53);

        System.out.println("\n\nStarting simulation:\n");

        // Randomize carb between -150 and 150 put in absolute value
        int carb = (int) (Math.abs(Math.random() * 300 - 150));
        // Randomize mode between 0 and 2   
        int mode = (int) (Math.abs(Math.random() * 3));
        // Randomize delay between 1 and 3
        int delay = (int) (Math.abs(Math.random() * 3)+1);
        // Randomize residual units (still active insulin)
        ui.newPenBolus((float) (Math.abs(Math.random() * 5)));

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