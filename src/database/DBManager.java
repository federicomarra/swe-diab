package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import io.github.cdimascio.dotenv.Dotenv;
import utils.HistoryEntry;
import utils.HourlyFactor;
import utils.ProfileMode;

public interface DBManager {

    String[] FILES = { "BasalProfile", "CarbRatio", "InsulinSensitivity", "History" };

    private static String getProfileFilename(ProfileMode mode) {
        return FILES[mode.ordinal()];
    }

    private static String getDatabaseUrl() {
        String databaseUrl;

        try {
            Dotenv dotenv = Dotenv.load();
            databaseUrl = dotenv.get("LOCAL_DB_PATH");

            if (databaseUrl == null || !databaseUrl.contains(".db")) {
                System.out.println("LOCAL_DB_PATH not found in .env file. Using default value.");
                databaseUrl = "db/data.db";
            }
        } catch (Exception e) {
            return "db/data.db";
        }

        var folders = databaseUrl.split("/");
        var folder = "";
        for (int i = 0; i < folders.length; i++) {
            if (folders[i].contains(".db")) {
                break;
            }

            folder += folders[i] + "/";
            if (!Files.exists(Paths.get(folder))) {
                try {
                    Files.createDirectories(Paths.get(folder));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return "jdbc:sqlite:" + databaseUrl;
    }

    String databaseFilePath = getDatabaseUrl().split("sqlite:")[1];

    String backupFilePath = databaseFilePath
            .substring(0, databaseFilePath.lastIndexOf("/") + 1) + "backup.db";

    private static void createProfileTable(ProfileMode mode) {
        String tableName = getProfileFilename(mode);
        try {
            Connection c = DriverManager.getConnection(getDatabaseUrl());
            System.out.println("Opened database successfully");

            Statement stmt = c.createStatement();
            String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='" + tableName.toUpperCase() + "'";
            var rs = stmt.executeQuery(sql);
            if (rs.next()) {
                rs.close();
                stmt.close();
                c.close();
                return;
            }

            System.out.println("Creating default table " + tableName.toUpperCase() + "...");

            stmt = c.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS " + tableName.toUpperCase()
                    + " (HOUR INT PRIMARY KEY NOT NULL, UNITS REAL NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();

            stmt = c.createStatement();
            switch (mode) {
                case BASAL:
                    sql = "INSERT INTO " + tableName.toUpperCase()
                            + " (HOUR, UNITS) VALUES (0, 1.60), (1, 1.60), (2, 1.60), (3, 1.60), (4, 1.60), (5, 1.60), (6, 1.60), (7, 1.50), (8, 1.50), (9, 1.50), (10, 1.50), (11, 1.60), (12, 1.60), (13, 1.60), (14, 1.60), (15, 2.75), (16, 2.75), (17, 2.75), (18, 2.75), (19, 2.75), (20, 2.75), (21, 2.75), (22, 2.25), (23, 2.25)";
                    break;
                case CR:
                    sql = "INSERT INTO " + tableName.toUpperCase()
                            + " (HOUR, UNITS) VALUES (0, 9), (1, 9), (2, 9), (3, 9), (4, 9), (5, 9), (6, 9), (7, 9), (8, 9), (9, 9), (10, 9), (11, 9), (12, 11), (13, 11), (14, 11), (15, 11), (16, 10), (17, 10), (18, 10), (19, 10), (20, 10), (21, 10), (22, 10), (23, 10)";
                    break;
                case IS:
                    sql = "INSERT INTO " + tableName.toUpperCase()
                            + " (HOUR, UNITS) VALUES (0, 30), (1, 30), (2, 30), (3, 30), (4, 30), (5, 35), (6, 35), (7, 35), (8, 35), (9, 35), (10, 35), (11, 35), (12, 35), (13, 35), (14, 30), (15, 30), (16, 30), (17, 30), (18, 28), (19, 28), (20, 28), (21, 28), (22, 28), (23, 28)";
                    break;
                default:
                    break;
            }
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
    }

    private static void createHistoryTable() {
        try {
            Connection c = DriverManager.getConnection(getDatabaseUrl());
            System.out.println("Opened database successfully");

            Statement stmt = c.createStatement();
            String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='HISTORY'";
            var rs = stmt.executeQuery(sql);
            if (rs.next()) {
                rs.close();
                stmt.close();
                c.close();
                return;
            }

            System.out.println("Creating table HISTORY...");

            stmt = c.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS HISTORY (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, TIME TEXT NOT NULL, GLYCEMIA INT NOT NULL, UNITS REAL NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
    }

    static HistoryEntry[] readHistoryTable() {
        FTPInfo info = getBackupFtpInfo();

        try {
            if (!Files.exists(Paths.get(databaseFilePath))) {
                if (info == null) {
                    if (Files.exists(Paths.get(backupFilePath))) {
                        System.out.println("Database file not found. Cloning backup...");
                        Files.copy(Paths.get(backupFilePath), Paths.get(databaseFilePath),
                                StandardCopyOption.REPLACE_EXISTING);
                    }
                } else {
                    if (checkBackupDatabase(info)) {
                        System.out.println("Database file not found. Cloning remote backup...");
                        cloneBackupDatabase(info);
                    }
                }
            }

            Connection c = DriverManager.getConnection(getDatabaseUrl());
            c.setAutoCommit(false);

            Statement stmt = c.createStatement();
            String sql = "SELECT * FROM HISTORY ORDER BY ID ASC";
            var rs = stmt.executeQuery(sql);
            var history = new ArrayList<HistoryEntry>();
            while (rs.next()) {
                String time = rs.getString("time");
                int glycemia = rs.getInt("glycemia");
                float units = rs.getFloat("units");
                history.add(new HistoryEntry(ZonedDateTime.parse(time), glycemia, units));
            }
            rs.close();
            stmt.close();
            c.close();
            return history.toArray(new HistoryEntry[history.size()]);
        } catch (Exception e) {
            if (e.getMessage().contains("no such table: HISTORY")) {
                return new HistoryEntry[0];
            }
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
            return null;
        }
    }

    static void writeHistoryTable(HistoryEntry he) {
        try {
            Connection c = DriverManager.getConnection(getDatabaseUrl());
            c.setAutoCommit(false);

            Statement stmt = c.createStatement();
            String sql = "INSERT INTO HISTORY (TIME, GLYCEMIA, UNITS) VALUES ('" + he.getTime() + "', "
                    + he.getGlycemia() + ", " + he.getUnits() + ")";
            stmt.executeUpdate(sql);
            c.commit();

            stmt.close();
            c.close();
        } catch (Exception e) {
            if (e.getMessage().contains("no such table: HISTORY")) {
                createHistoryTable();
                writeHistoryTable(he);
                return;
            }
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
    }

    static float[] readProfileTable(ProfileMode mode) {
        String tableName = getProfileFilename(mode);
        float[] units = new float[24];
        FTPInfo info = getBackupFtpInfo();

        try {
            if (!Files.exists(Paths.get(databaseFilePath))) {
                if (info == null) {
                    if (Files.exists(Paths.get(backupFilePath))) {
                        System.out.println("Database file not found. Cloning backup...");
                        Files.copy(Paths.get(backupFilePath), Paths.get(databaseFilePath),
                                StandardCopyOption.REPLACE_EXISTING);
                    }
                } else {
                    if (checkBackupDatabase(info)) {
                        System.out.println("Database file not found. Cloning remote backup...");
                        cloneBackupDatabase(info);
                    }
                }
            }

            Connection c = DriverManager.getConnection(getDatabaseUrl());
            c.setAutoCommit(false);

            Statement stmt = c.createStatement();
            String sql = "SELECT * FROM " + tableName.toUpperCase() + " ORDER BY HOUR ASC";
            var rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int hour = rs.getInt("hour");
                float unit = rs.getFloat("units");
                units[hour] = unit;
            }
            rs.close();
            stmt.close();
            c.close();
            return units;
        } catch (Exception e) {
            if (e.getMessage().contains("no such table: " + tableName.toUpperCase())) {
                createProfileTable(mode);
                return readProfileTable(mode);
            }
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
            return null;
        }
    }

    static void writeProfileTable(ProfileMode mode, HourlyFactor hf) {
        String tableName = getProfileFilename(mode);
        try {
            Connection c = DriverManager.getConnection(getDatabaseUrl());
            c.setAutoCommit(false);

            Statement stmt = c.createStatement();
            String sql = "UPDATE " + tableName.toUpperCase() + " SET UNITS = " + hf.getUnits() + " WHERE HOUR = "
                    + hf.getHour();
            stmt.executeUpdate(sql);
            c.commit();

            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
    }

    static String getRemoteFilePath() {
        String remoteFilePath;

        try {
            Dotenv dotenv = Dotenv.load();
            remoteFilePath = dotenv.get("BACKUP_DB_PATH");

            if (remoteFilePath == null || !remoteFilePath.contains(".db")) {
                System.out.println("BACKUP_DB_PATH not found in .env file. Using default value.");
                remoteFilePath = "backup.db";
            }
        } catch (Exception e) {
            return "backup.db";
        }

        return remoteFilePath;
    }

    static boolean checkBackupDatabase(FTPInfo info) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(info.server, info.port);
            ftpClient.login(info.username, info.password);
            ftpClient.enterLocalPassiveMode();

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            var remoteFileSize = ftpClient.listFiles(getRemoteFilePath());
            ftpClient.logout();

            return remoteFileSize.length > 0;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    static void cloneBackupDatabase(FTPInfo info) {
        FTPClient ftpClient = new FTPClient();
        FileOutputStream fos = null;

        try {
            ftpClient.connect(info.server, info.port);
            ftpClient.login(info.username, info.password);
            ftpClient.enterLocalPassiveMode();

            File localFile = new File(databaseFilePath);
            fos = new FileOutputStream(localFile);

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.retrieveFile(getRemoteFilePath(), fos);
            ftpClient.logout();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static FTPInfo getBackupFtpInfo() {
        String ftpInfo;

        try {
            Dotenv dotenv = Dotenv.load();
            ftpInfo = dotenv.get("BACKUP_DB_FTP");

            if (ftpInfo == null || !ftpInfo.contains(":") || !ftpInfo.contains("?") || !ftpInfo.contains("&")) {
                return null;
            }
        } catch (Exception e) {
            return null;
        }

        FTPInfo info = new FTPInfo();
        info.server = ftpInfo.split(":")[0];
        info.port = Integer.parseInt(ftpInfo.split(":")[1].split("\\?")[0]);
        info.username = ftpInfo.split("\\?")[1].split("&")[0].split("=")[1];
        info.password = ftpInfo.split("\\?")[1].split("&")[1].split("=")[1];

        return info;
    }

    static void backupDatabase() {
        FTPInfo info = getBackupFtpInfo();

        if (info == null) {
            try {
                Files.copy(Paths.get(databaseFilePath),
                        Paths.get(backupFilePath),
                        StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Database backed up successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        FTPClient ftpClient = new FTPClient();
        FileInputStream fis = null;

        try {
            ftpClient.connect(info.server, info.port);
            ftpClient.login(info.username, info.password);
            ftpClient.enterLocalPassiveMode();

            File localFile = new File(databaseFilePath);
            fis = new FileInputStream(localFile);

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.storeFile(getRemoteFilePath(), fis);
            ftpClient.logout();
            fis.close();

            System.out.println("File backed up successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}