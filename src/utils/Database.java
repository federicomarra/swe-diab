package utils;

import handheldTracker.BolusDelivery;
import utils.ProfileMode;

import java.util.List;


public class Database {
    public HourlyProfile carbRatioProfile;
    public HourlyProfile insulinSensitivityProfile;
    public HourlyProfile basalProfile;
    public List<BolusDelivery> bolusDeliveries;
    public List<Measurement> measurements;

    public Database() {
        carbRatioProfile = new HourlyProfile(ProfileMode.IC);
        insulinSensitivityProfile = new HourlyProfile(ProfileMode.IG);
        basalProfile = new HourlyProfile(ProfileMode.BASAL);
    }

    public void updateHourlyFactor(HourlyFactor hf) {}

    protected void update(List<Measurement> ms) {
        for (Measurement m : ms)
            measurements.add(m);
    }

    public void newBolus(float units, int delay, BolusMode mode, int carb) {}

    private void computeAndInject() {}

    private void backup() {}

    private void addBolus(BolusDelivery bd) {}

}
