package utils;

import handheldTracker.BolusDelivery;
import handheldTracker.BolusMode;

import java.util.List;

public class Database {
    public HourlyProfile carbRatioProfile;
    public HourlyProfile insulinSensitivityProfile;
    public HourlyProfile basalProfile;
    public List<BolusDelivery> bolusDeliveries;
    public List<Measurement> measurements;

    public Database() {
    }

    protected void update(List<Measurement> ms) {
        for (Measurement m : ms) {
            measurements.add(m);
        }
    }

    // TODO: check if following methods are needed

    public void updateHourlyFactor(HourlyFactor hf, ProfileMode mode) {
    }

    public void newBolus(float units, int delaySeconds, BolusMode mode, int carb) {
    }

    private void computeAndInject() {
    }

    private void backup() {
    }

    private void addBolus(BolusDelivery bd) {
    }

}
