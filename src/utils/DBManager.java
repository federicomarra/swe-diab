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

public interface DBManager {
    /**
     * @param file Path of the CSV file
     * @return Array of floats containing the hourly factors
     */
    static float[] read(String file) {
        float[] units = new float[24];
        String path = dirPath + file + extension;
        System.out.println("Reading " + file + " from " + dirPath + file);
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
            String wrongFile = pathSplit[pathSplit.length - 1].replace(extension, "");
            String values = "";
            switch (file) {
                case "BasalProfile":
                    for (int i = 0; i < 24; i++) {
                        if (i < 7)
                            values += i + ",1.60\n";
                        else if (i < 10)
                            values += i + ",1.50\n";
                        else if (i < 14)
                            values += i + ",1.60\n";
                        else if (i < 22)
                            values += i + ",2.75\n";
                        else
                            values += i + ",2.25\n";
                    }
                    break;
                case "CarbRatio":
                    for (int i = 0; i < 24; i++) {
                        if (i < 12)
                            values += i + ",9\n";
                        else if (i < 16)
                            values += i + ",11\n";
                        else
                            values += i + ",10\n";
                    }
                    break;
                case "InsulinSensitivity":
                    for (int i = 0; i < 24; i++) {
                        if (i < 5)
                            values += i + ",30\n";
                        else if (i < 12)
                            values += i + ",35\n";
                        else if (i < 16)
                            values += i + ",30\n";
                        else
                            values += i + ",28\n";
                    }
                    break;
            }
            try {
                try {
                    Files.createDirectory(Paths.get(dir));
                    System.out.println("Directory " + dir + " created");
                } catch (FileAlreadyExistsException e) {
                    System.out.println("Directory " + dir + " already exists");
                }
                Files.write(Paths.get(dirPath + wrongFile + extension), values.getBytes(), StandardOpenOption.CREATE_NEW);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Creating default " + wrongFile + "...");

            return read(path.substring(dirPath.length(), path.length() - extension.length()));
        }
        return units;
    }

    static void write(String file, HourlyFactor hf) {
        String path = dirPath + file + extension;
        System.out.println("Reading " + file + " from " + dirPath + file);
        try {
            Files.write(Paths.get(path), (hf.getHour() + "," + hf.getUnits() + "\n").getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static HistoryEntry[] readHistoryEntry() {
        String path = dirPath + "History" + extension;
        var historyEntry = new ArrayList<HistoryEntry>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            String line = br.readLine();
            while (line != null && !line.isEmpty()) {
                String[] lineSplit = line.split(",");

                historyEntry.add(new HistoryEntry(ZonedDateTime.parse(lineSplit[0]), Integer.parseInt(lineSplit[1]),
                        Float.parseFloat(lineSplit[2])));

                line = br.readLine();
            }
            br.close();
        } catch (IOException err) {
            System.out.println("Creating History file...");

            try {
                try {
                    Files.createDirectory(Paths.get(dir));
                    System.out.println("Directory " + dir + " created");
                } catch (FileAlreadyExistsException e) {
                    System.out.println("Directory " + dir + " already exists");
                }

                Files.write(Paths.get(path),
                        "".getBytes(), StandardOpenOption.CREATE_NEW);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return readHistoryEntry();
        }
        return historyEntry.toArray(new HistoryEntry[historyEntry.size()]);
    }

    static void addHistoryEntry(HistoryEntry he) {
        String path = dirPath + "History" + extension;
        try {
            readHistoryEntry();
            Files.write(Paths.get(path),
                    (he.getTime() + "," + he.getGlycemia() + "," + he.getUnits() + "\n").getBytes(),
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void backupProfiles() {
        try {
            // It also backup the history file
            try {
                Files.createDirectory(Paths.get(dirPath + "backup"));
                System.out.println("Directory " + dirPath + "backup" + " created");
            } catch (FileAlreadyExistsException e) {
                System.out.println("Directory " + dirPath + "backup" + " already exists");
            }
            for (String file : files)
                Files.copy(Paths.get(dirPath + file + extension), Paths.get(dirPath + "backup/" + file + extension),
                        StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Profiles backed up successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String dir = "db";
    String dirPath = dir + "/";
    String[] files = {"BasalProfile", "CarbRatio", "InsulinSensitivity", "History"};
    String extension = ".csv";
}