package utils;

public class HourlyFactor {
    private float units;
    private int hour;

    public HourlyFactor(float units, int hour) {
        this.units = units;
        this.hour = hour;
    }

    public float getUnits() {
        return this.units;
    }

    public void setUnits(float units) {
        this.units = units;
    }

    public int getHour() {
        return this.hour;
    }

    public void setHour(int hour) {
        this.hour = hour % 24;
    }

}