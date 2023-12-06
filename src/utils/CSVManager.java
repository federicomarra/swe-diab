package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public interface CSVManager {
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
        } catch (IOException err) {
            System.out.println("File not found");
            String[] pathSplit = path.split("/");
            String wrongPath = pathSplit[pathSplit.length - 1].replace(".csv", "");
            if (wrongPath.equals("basalProfile")) {
                System.out.println("Creating default basal profile...");
                // copy the defaultBasalProfile.csv file into the basalProfile.csv file
                try {
                    Files.copy(Paths.get("csv/defaultBasalProfile.csv"), Paths.get("csv/basalProfile.csv"), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (wrongPath.equals("carbRatio")) {
                System.out.println("Creating default carb ratio profile...");
                try {
                    Files.copy(Paths.get("csv/defaultCarbRatio.csv"), Paths.get("csv/carbRatio.csv"), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (wrongPath.equals("insulinSensitivity")) {
                System.out.println("Creating default insulin sensitivity profile...");
                try {
                    Files.copy(Paths.get("csv/defaultInsulinSensitivity.csv"), Paths.get("csv/insulinSensitivity.csv"), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return read(path);
        }
        return units;
    }

    static void write(String path, HourlyFactor hf) {
        
    }
}

