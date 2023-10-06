package handheldTracker;

import utils.HourlyFactor;

import java.time.LocalTime;

public class UserInterface {
    private LocalDatabase localDatabase;

    public UserInterface() {
        localDatabase = new LocalDatabase();
    }

    public void newBolus(float units, LocalTime time, int delay, BolusMode mode) {
        switch (mode) {
            case STANDARD:
                localDatabase.computeAndInject(carb, mode);
                break;
            case EXTENDED:
                wait(delay*1000*60);    //delay in minutes
                localDatabase.computeAndInject(carb, mode);
                break;
            case MANUAL:
                addBolus(new BolusDelivery(units, time, delay, mode));
                break;
            case PEN:
                addBolus(new BolusDelivery(units, time, delay, mode));
                break;
        }
        //TODO: implement
        localDatabase.addBolus(new BolusDelivery(units, time, delay, mode));
    }

    public void updateBasalProfile(float units, int hour) {
        //TODO: implement
        localDatabase.updateHourlyFactor(new HourlyFactor(units, hour));
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
