package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public interface ReadCSV {
    static float[] ReadFloatCSV(String path) {
        float[] units = new float[24];
        System.out.println("\nReading " + path.substring(4, path.length() - 4) + " from " + path);
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));           // reads file
            String line = br.readLine();                                            // reads first row
            String[] firstlinesplit = line.split(";");                              // split first row
            int h = 0;                                                              // initialize hour
            System.out.print("Read: Factor[h=" + firstlinesplit[0] + "] = " + firstlinesplit[1] + " -> "); // print before first parsing
            BigDecimal u = new BigDecimal(firstlinesplit[1]);                       // parse first row into BigDecimal
            u = u.setScale(2, RoundingMode.HALF_UP);                                    // round with 0.01 sensibility
            units[h] = u.floatValue();                                              // parse units into float
            System.out.println("Wrote: Factor[h=" + h + "] = " + units[h]);        // print after first parsing
            line = br.readLine();                                                   // reads second row
            while (line != null) {
                String[] linesplit = line.split(";");                               // separator in-row
                System.out.print("Read: Factor[h=" + linesplit[0] + "] = " + linesplit[1] + " -> "); // print before parsing
                try {
                    h = Integer.parseInt(linesplit[0]) % 24;                            // parse hour
                    u = new BigDecimal(linesplit[1]);                        // parse first row into BigDecimal
                    u = u.setScale(2, RoundingMode.HALF_UP);                            // round with 0.01 sensibility
                    u = u.divide(new BigDecimal("0.05"), 0, RoundingMode.HALF_UP).multiply(new BigDecimal("0.05")); // round with 0.05 sensibility
                    units[h] = u.floatValue();                                          // parse units into float
                    System.out.println("Wrote: Factor[h=" + h + "] = " + units[h]);    // print after parsing
                } catch (NumberFormatException e) {
                    // TODO: fix first iteration error parsing "0" as int
                    System.out.println("The string cannot be converted. Logs: " + "h: " + linesplit[0] + "->" + Integer.parseInt(linesplit[0]) + "  u: " + linesplit[1] + "->" + Float.parseFloat(linesplit[1]));
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("File not found");
        }
        return units;
    }

    static int[] ReadIntCSV(String path) {
        int[] units = new int[24];
        System.out.println("\nReading " + path.substring(4, path.length() - 4) + " from " + path);
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));   // reads file
            String line = br.readLine();                                    // reads first row
            String[] firstlinesplit = line.split(";");                      // split first row
            int h = 0;                                                      // initialize hour
            System.out.print("Read: Factor[h=" + firstlinesplit[0] + "] = " + firstlinesplit[1] + " -> "); // print before first parsing
            units[h] = Integer.parseInt(firstlinesplit[1]);                 // parse first row
            System.out.println("Wrote: Factor[h=" + h + "] = " + units[h]);    // print after first parsing
            line = br.readLine();                                           // reads second row
            while (line != null) {  //returns a Boolean value
                String[] linesplit = line.split(";");    // use comma as separator
                System.out.print("Read: Factor[h=" + linesplit[0] + "] = " + linesplit[1] + " -> ");
                try {
                    h = Integer.parseInt(linesplit[0]) % 24;
                    units[h] = Integer.parseInt(linesplit[1]);
                    System.out.println("Wrote: Factor[h=" + h + "] = " + units[h]);
                } catch (NumberFormatException e) {
                    System.out.println("The string cannot be converted. Logs: " + "h: " + linesplit[0] + "->" + Integer.parseInt(linesplit[0]) % 24 + "  u: " + linesplit[1] + "->" + Integer.parseInt(linesplit[1]));
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("File not found");
        }
        return units;
    }
}
