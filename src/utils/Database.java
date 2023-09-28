package utils;

import handheldTracker.BolusDelivery;

import java.util.List;

public class Database {
    public HourlyProfile carbRatioProfile;
    public HourlyProfile insulinSensitivityProfile;
    public HourlyProfile basalProfile;
    public List<BolusDelivery> bolusDeliveries;
    public List<Mesurament> mesuraments;
}
