import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

    public static float[] ReadFloatCSV(String path) {
        float[] units = new float[24];
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));   // reads file
            String line = br.readLine();                                    // reads first row
            String[] firstlinesplit = line.split(";");                      // split first row
            int h = 0;                                                      // initialize hour
            System.out.println("CSV:    Factor[h=" + firstlinesplit[0] + "] = " + firstlinesplit[1]); // print before first parsing
            units[h] = Float.parseFloat(firstlinesplit[1]);                 // parse first row
            System.out.println("PARSED: Factor[h=" + h + "] = " + units[h]);    // print after first parsing
            line = br.readLine();                                           // reads second row
            while (line != null) {
                String[] linesplit = line.split(";");                       // separator in-row
                System.out.println("CSV:    Factor[h=" + linesplit[0] + "] = " + linesplit[1]); // print before parsing
                try {
                    h = Integer.parseInt(linesplit[0]) % 24;                // parse hour
                    units[h] = Float.parseFloat(linesplit[1]);              // parse units
                    System.out.println("PARSED: Factor[h=" + h + "] = " + units[h]);    // print after parsing
                } catch (NumberFormatException e) {
                    // TODO: fix first iteration error parsing "0" as int
                    System.out.println("The string cannot be converted. Logs: " + "h: " + linesplit[0] + "->" + Integer.parseInt(linesplit[0]) + "  u: " + linesplit[1] + "->" + Float.parseFloat(linesplit[1]));
                }
                line = br.readLine();
            }
        }
        catch (IOException e) {
            System.out.println("File not found");
        }
        return units;
    }
    public static int[] ReadIntCSV(String path) {
        int[] units = new int[24];
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));   // reads file
            String line = br.readLine();                                    // reads first row
            String[] firstlinesplit = line.split(";");                      // split first row
            int h = 0;                                                      // initialize hour
            System.out.println("CSV:    Factor[h=" + firstlinesplit[0] + "] = " + firstlinesplit[1]); // print before first parsing
            units[h] = Integer.parseInt(firstlinesplit[1]);                 // parse first row
            System.out.println("PARSED: Factor[h=" + h + "] = " + units[h]);    // print after first parsing
            line = br.readLine();                                           // reads second row
            while (line != null) {  //returns a Boolean value
                String[] linesplit = line.split(";");    // use comma as separator
                System.out.println("CSV:    Factor[h=" + linesplit[0] + "] = " + linesplit[1]);
                try {
                    h = Integer.parseInt(linesplit[0]) % 24;
                    units[h] = Integer.parseInt(linesplit[1]);
                    System.out.println("PARSED: Factor[h=" + h + "] = " + units[h]);
                } catch (NumberFormatException e) {
                    System.out.println("The string cannot be converted. Logs: " + "h: " + linesplit[0] + "->" + Integer.parseInt(linesplit[0]) % 24 + "  u: " + linesplit[1] + "->" + Integer.parseInt(linesplit[1]));
                }
                line = br.readLine();
            }
        }
        catch (IOException e) {
            System.out.println("File not found");
        }
        return units;
    }
}