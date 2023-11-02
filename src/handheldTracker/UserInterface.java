package handheldTracker;

import glucoseDeliverySystem.PumpManager;
import utils.*;

import java.util.List;

public class UserInterface {
    private LocalDatabase localDb;

    public UserInterface() {
        // One localDb for one user
        localDb = new LocalDatabase();
    }

    public void newStandardBolus(int carb) {
        localDb.newBolus(0, 0, BolusMode.STANDARD, carb);
    }

    public void newExtendedBolus(int carb, int delay) {
        localDb.newBolus(0, delay, BolusMode.EXTENDED, carb);
    }

    public void howManyUnits(int carb) {
        localDb.newBolus(0, 0, BolusMode.MANUAL, carb);
    }

    public void newPenBolus(float units) {
        localDb.newBolus(units, 0, BolusMode.PEN, 0);
    }

    public void updateBasalProfile(float units, int hour) {
        localDb.updateHourlyFactor(new HourlyFactor(units, hour), ProfileMode.BASAL);
    }

    public void updateCarbRatioProfile(int units, int hour) {
        localDb.updateHourlyFactor(new HourlyFactor(units, hour), ProfileMode.IC);
    }

    public void updateInsulinSensitivityProfile(int units, int hour) {
        localDb.updateHourlyFactor(new HourlyFactor(units, hour), ProfileMode.IG);
    }
}
