package utils;

// FIXME: These are the same, if we need to use java14 for
//        something else the use of record is justified.

// public record HourlyFactor(float units, int hour) {}

public class HourlyFactor {
    float units;
    int hour;

    public HourlyFactor(float units, int hour) {
        this.units = units;
        this.hour = hour;
    }

    public float units() {
        return this.units;
    }

    public int hour() {
        return this.hour;
    }
}