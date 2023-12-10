package database;

import handheldTracker.BolusDelivery;
import utils.HourlyProfile;
import utils.Measurement;

import java.util.List;

public class Database {
    public HourlyProfile carbRatioProfile;
    public HourlyProfile insulinSensitivityProfile;
    public HourlyProfile basalProfile;
    public List<BolusDelivery> bolusDeliveries;
    public List<Measurement> measurements;

    protected void update(List<Measurement> ms) {

        for (Measurement m : ms) {
            measurements.add(m);
        }
    }
}
