package utils;

import java.time.LocalTime;
import java.time.ZonedDateTime;

public class HistoryEntry {
    private final ZonedDateTime time;
    private final int glycemia;
    private final float units;

    public HistoryEntry(ZonedDateTime time, int glycemia, float units) {
        this.time = time;
        this.glycemia = glycemia;
        this.units = units;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public String getTimeString() {
        return time.getDayOfMonth() + "/" + time.getMonthValue() + "/" + time.getYear() +
                " " + String.format("%02d", time.getHour())
                + ":" + String.format("%02d", time.getMinute());
    }

    public LocalTime toLocalTime() {
        return LocalTime.of(this.time.getHour(), this.time.getMinute());
    }

    public int getGlycemia() {
        return glycemia;
    }

    public float getUnits() {
        return Float.parseFloat(String.format("%.2f", this.units).replace(",", "."));
    }
}