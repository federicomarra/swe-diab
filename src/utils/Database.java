package utils;

import handheldTracker.BolusDelivery;

import java.util.List;


public class Database {
    public HourlyProfile carbRatioProfile;
    public HourlyProfile insulinSensitivityProfile;
    public HourlyProfile basalProfile;
    public List<BolusDelivery> bolusDeliveries;
    public List<Measurement> measurements;

    public Database() {}

    protected void update(List<Measurement> ms) {
        for (Measurement m : ms)
            measurements.add(m);
    }

    // TODO: non so se servono questi metodi sotto

    /*
    public void updateHourlyFactor(HourlyFactor hf) {}
    
    public void newBolus(float units, int delay, BolusMode mode, int carb) {}

    private void computeAndInject() {}

    private void backup() {}

    private void addBolus(BolusDelivery bd) {}
    */
}
