package utils;

public class HourlyFactor {
    private float units;
    private int hour;

    public HourlyFactor(float units, int hour) {
        this.units = units;
        this.hour = hour;
    }

    public float getUnits() {
        return Float.parseFloat(String.format("%.2f", this.units).replace(",", "."));
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