package utils;

// FIXME: These are the same, if we need to use java14 for
//        something else the use of record is justified.

// public record HourlyFactor(float units, int hour) {}

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