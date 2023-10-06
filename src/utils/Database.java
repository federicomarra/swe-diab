package utils;

import handheldTracker.BolusDelivery;

import java.time.LocalTime;
import java.util.List;

public class Database {
    public HourlyProfile carbRatioProfile;
    public HourlyProfile insulinSensitivityProfile;
    public HourlyProfile basalProfile;
    public List<BolusDelivery> bolusDeliveries;
    public List<Mesurament> mesuraments;

    public Database() {

    }

    public void updateHourlyFactor(HourlyFactor hf) {

    }

    public void update(List<Mesurament> m) {

    }

    public void computeAndInject() {

    }

    private void backup() {

    }

    public void addBolus(BolusDelivery bolusDelivery) {

    }
}
