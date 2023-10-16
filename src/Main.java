
import static utils.ReadCSV.*;

//import handheldTracker.UserInterface;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome in the Glucose Monitoring System");

        float[] bf = ReadFloatCSV("csv/basalProfile.csv");
        int[] cb = ReadIntCSV("csv/carbRatio.csv");
        int[] is = ReadIntCSV("csv/insulinSensitivity.csv");

        /*
        UserInterface ui = new UserInterface();
        for (int i = 0; i < 24; i++) {
            ui.updateBasalProfile(bf[i], i);
            ui.updateCarbRatio(cb[i], i);
            ui.updateInsulinSensitivity(is[i], i);
        }
        */

    }


}