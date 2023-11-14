package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public interface ReadCSV {
    /**
     * @param path Path of the CSV file
     * @return Array of floats containing the hourly factors
     */
    static float[] ReadCSV(String path) {
        float[] units = new float[24];
        System.out.println("Reading " + path.substring(4, path.length() - 4) + " from " + path);
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            // Reads first row
            String line = br.readLine();
            // Split first row
            String[] firstlinesplit = line.split(";");
            // Initialize hour
            int h = 0;
            // Print before first parsing
            //System.out.print("Read: Factor[h=" + firstlinesplit[0] + "] = " + firstlinesplit[1] + " -> ");
            // Parse first row into BigDecimal
            BigDecimal u = new BigDecimal(firstlinesplit[1]);
            // Round with 0.01 sensibility
            u = u.setScale(2, RoundingMode.HALF_UP);
            // Parse units into float
            units[h] = u.floatValue();
            // Print after first parsing
            //System.out.println("Wrote: Factor[h=" + h + "] = " + units[h]);
            // Reads second row
            line = br.readLine();
            while (line != null) {
                // Separator in-row
                String[] linesplit = line.split(";");
                // Print before parsing
                //System.out.print("Read: Factor[h=" + linesplit[0] + "] = " + linesplit[1] + " -> ");
                try {
                    // Parse hour
                    h = Integer.parseInt(linesplit[0]) % 24;
                    // Parse first row into BigDecimal
                    u = new BigDecimal(linesplit[1]);
                    // Round with 0.01 sensibility
                    u = u.setScale(2, RoundingMode.HALF_UP);
                    // Round with 0.05 sensibility
                    u = u.divide(new BigDecimal("0.05"), 0, RoundingMode.HALF_UP).multiply(new BigDecimal("0.05"));
                    // Parse units into float
                    units[h] = u.floatValue();
                    // Print after parsing
                    //System.out.println("Wrote: Factor[h=" + h + "] = " + units[h]);
                } catch (NumberFormatException e) {
                    // FIXME: Fix first iteration error parsing "0" as int
                    System.out.println("The string cannot be converted. Logs: " +
                            "h: " + linesplit[0] + "->" + Integer.parseInt(linesplit[0]) +
                            "  u: " + linesplit[1] + "->" + Float.parseFloat(linesplit[1]));
                }
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            System.out.println("File not found");
        }
        return units;
    }
}
