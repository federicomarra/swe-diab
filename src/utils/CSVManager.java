package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.time.ZonedDateTime;
import java.util.ArrayList;

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
            String[] pathSplit = path.split("/");
            String wrongPath = pathSplit[pathSplit.length - 1].replace(".csv", "");

            if (wrongPath.equals("basalProfile")) {
                System.out.println("Creating default basal profile...");
                try {
                    try {
                        Files.createDirectory(Paths.get("csv"));
                    } catch (FileAlreadyExistsException e) {
                        System.out.println("Directory already exists");
                    }

                    Files.write(Paths.get("csv/basalProfile.csv"),
                            "0,1.60\n1,1.60\n2,1.60\n3,1.60\n4,1.60\n5,1.60\n6,1.60\n7,1.50\n8,1.50\n9,1.50\n10,1.60\n11,1.60\n12,1.60\n13,1.60\n14,2.75\n15,2.75\n16,2.75\n17,2.75\n18,2.75\n19,2.75\n20,2.75\n21,2.75\n22,2.25\n23,2.25"
                                    .getBytes(),
                            StandardOpenOption.CREATE_NEW);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (wrongPath.equals("carbRatio")) {
                System.out.println("Creating default carb ratio profile...");
                try {
                    try {
                        Files.createDirectory(Paths.get("csv"));
                    } catch (FileAlreadyExistsException e) {
                        System.out.println("Directory already exists");
                    }

                    Files.write(Paths.get("csv/carbRatio.csv"),
                            "0,9\n1,9\n2,9\n3,9\n4,9\n5,9\n6,9\n7,9\n8,9\n9,9\n10,9\n11,9\n12,11\n13,11\n14,11\n15,11\n16,10\n17,10\n18,10\n19,10\n20,10\n21,10\n22,10\n23,10"
                                    .getBytes(),
                            StandardOpenOption.CREATE_NEW);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (wrongPath.equals("insulinSensitivity")) {
                System.out.println("Creating default insulin sensitivity profile...");
                try {
                    try {
                        Files.createDirectory(Paths.get("csv"));
                    } catch (FileAlreadyExistsException e) {
                        System.out.println("Directory already exists");
                    }

                    Files.write(Paths.get("csv/insulinSensitivity.csv"),
                            "0,30\n1,30\n2,30\n3,30\n4,30\n5,30\n6,35\n7,35\n8,35\n9,35\n10,35\n11,35\n12,30\n13,30\n14,30\n15,30\n16,28\n17,28\n18,28\n19,28\n20,28\n21,28\n22,28\n23,28"
                                    .getBytes(),
                            StandardOpenOption.CREATE_NEW);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return read(path);
        }
        return units;
    }

    static void write(String path, HourlyFactor hf) {
        try {
            Files.write(Paths.get(path), (hf.getHour() + "," + hf.getUnits() + "\n").getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static HistoryEntry[] readHistoryEntry() {
        var path = "csv/history.csv";
        var historyEntry = new ArrayList<HistoryEntry>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            String line = br.readLine();
            while (line != null && !line.equals("")) {
                String[] lineSplit = line.split(",");

                historyEntry.add(new HistoryEntry(ZonedDateTime.parse(lineSplit[0]), Integer.parseInt(lineSplit[1]),
                        Float.parseFloat(lineSplit[2])));

                line = br.readLine();
            }
            br.close();
        } catch (IOException err) {
            System.out.println("Creating history file...");

            try {
                try {
                    Files.createDirectory(Paths.get("csv"));
                } catch (FileAlreadyExistsException e) {
                    System.out.println("Directory already exists");
                }

                Files.write(Paths.get("csv/history.csv"),
                        "".getBytes(), StandardOpenOption.CREATE_NEW);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return readHistoryEntry();
        }
        return historyEntry.toArray(new HistoryEntry[historyEntry.size()]);
    }

    static void addHistoryEntry(HistoryEntry he) {
        var path = "csv/history.csv";
        try {
            Files.write(Paths.get(path),
                    (he.getTime() + "," + he.getGlycemia() + "," + he.getUnits() + "\n").getBytes(),
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void backupProfiles() {
        try {
            Files.copy(Paths.get("csv/basalProfile.csv"), Paths.get("csv/backupBasalProfile.csv"),
                    StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Paths.get("csv/carbRatio.csv"), Paths.get("csv/backupCarbRatio.csv"),
                    StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Paths.get("csv/insulinSensitivity.csv"), Paths.get("csv/backupInsulinSensitivity.csv"),
                    StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Profiles backed up successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}