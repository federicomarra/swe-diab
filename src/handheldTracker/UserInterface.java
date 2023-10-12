package handheldTracker;

import glucoseDeliverySystem.PumpManager;
import utils.HourlyFactor;

import java.time.LocalTime;

public class UserInterface {
    private LocalDatabase localDb;

    public UserInterface() {
        localDb = new LocalDatabase(new PumpManager());
    }

    public void newBolus(float units, LocalTime time, int delay, BolusMode mode, int carb) {
        switch (mode) {
            case STANDARD:
                localDb.computeAndInject(carb, mode);
                break;
            case EXTENDED:
                wait(delay*1000*60);    //delay in minutes
                localDb.computeAndInject(carb, mode);
                break;
            case MANUAL:
                localDb.addBolus(new BolusDelivery(units, LocalTime.now(), 0, mode));
                break;
            case PEN:
                localDb.addBolus(new BolusDelivery(units, LocalTime.now(), 0, mode));
                break;
        }
        //TODO: implement
        localDb.addBolus(new BolusDelivery(units, time, delay, mode));
    }

    public void updateBasalProfile(float units, int hour) {
        //TODO: implement
        localDb.updateHourlyFactor(new HourlyFactor(units, hour));
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
