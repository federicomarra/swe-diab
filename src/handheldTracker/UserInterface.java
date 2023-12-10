package handheldTracker;

import database.DBManager;
import utils.*;

public class UserInterface {
    private final LocalDatabase db;

    public UserInterface() {
        // One localDb for one user
        db = new LocalDatabase();
    }

    public LocalDatabase getDb() {
        return db;
    }

    public HistoryEntry[] getHistory() {
        return DBManager.readHistoryTable();
    }

    public void backup() {
        DBManager.backupDatabase();
    }

    public void newStandardBolus(int carb) {
        db.newBolus(0, 0, BolusMode.STANDARD, carb);
    }

    public void newExtendedBolus(int carb, int delayMinutes) { // delay in minutes
        db.newBolus(0, delayMinutes * 60, BolusMode.EXTENDED, carb); // delay in seconds
    }

    public void howManyUnits(int carb) {
        db.newBolus(0, 0, BolusMode.MANUAL, carb);
    }

    public void newPenBolus(float units) {
        db.newBolus(units, 0, BolusMode.PEN, 0);
    }

    public void updateBasalProfile(float units, int hour) {
        db.updateHourlyFactor(new HourlyFactor(units, hour), ProfileMode.BASAL);
    }

    public void updateCarbRatioProfile(float units, int hour) {
        db.updateHourlyFactor(new HourlyFactor(units, hour), ProfileMode.CR);
    }

    public void updateInsulinSensitivityProfile(float units, int hour) {
        db.updateHourlyFactor(new HourlyFactor(units, hour), ProfileMode.IS);
    }

}
