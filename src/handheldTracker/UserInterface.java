package handheldTracker;

import utils.HourlyFactor;

import java.time.LocalTime;

public class UserInterface {
    private LocalDatabase localDatabase;

    public UserInterface() {
        localDatabase = new LocalDatabase();
    }

    public void addBolus(float units, LocalTime time, int delay, BolusMode mode) {
        //TODO: implement
    }

    public void updateBasalProfile(float units, int hour) {
        //TODO: implement
        //updateHourlyFactor(new HourlyFactor(units, hour));
    }

    public void updateCarbRatioProfile(float units, int hour) {
        //TODO: implement
        //updateHourlyFactor(new HourlyFactor(units, hour));
    }

    public void updateInsulinSensitivityProfile(float units, int hour) {
        //TODO: implement
        //updateHourlyFactor(new HourlyFactor(units, hour));
    }
}
