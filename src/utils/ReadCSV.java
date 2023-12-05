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
    static float[] read(String path) {
        float[] units = new float[24];
        System.out.println("Reading " + path.substring(4, path.length() - 4) + " from " + path);
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            String line = br.readLine();
            int hour = 0;
            BigDecimal unit;

            while (line != null) {
                // Split by separator
                String[] lineSplit = line.split(",");

                // Parse hour handling exception
                try {
                    hour = Integer.parseInt(lineSplit[0]) % 24;
                } catch (NumberFormatException e) {
                    hour = 0;
                }

                unit = new BigDecimal(lineSplit[1]);
                // Round with 0.01 sensibility
                unit = unit.setScale(2, RoundingMode.HALF_UP);
                // Round with 0.05 sensibility
                unit = unit.divide(new BigDecimal("0.05"), 0, RoundingMode.HALF_UP).multiply(new BigDecimal("0.05"));

                units[hour] = unit.floatValue();

                // FIXME: Debug log
                // System.out.println("Read: hour=" + lineSplit[0] + " unit=" + lineSplit[1]);

                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            System.out.println("File not found");
        }
        return units;
    }
}
