package cloudInterface;

import utils.Measurement;

import java.util.List;

import database.DBManager;
import database.Database;

public class BackupDatabase extends Database {
    public void update(List<Measurement> ms) {
        if (measurements == null) {
            measurements = ms;
            return;
        }
        super.update(ms);
        DBManager.backupDatabase();
    }
}
