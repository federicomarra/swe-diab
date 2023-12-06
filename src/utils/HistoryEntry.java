package utils;

import java.time.ZonedDateTime;

public class HistoryEntry {
    private final ZonedDateTime time;
    private final float glycemia;
    private final float units;

    public HistoryEntry(ZonedDateTime time, float glycemia, float units) {
        this.units = units;
        this.time = time;
        this.glycemia = glycemia;
    }

    public float getUnits() {
        return units;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public float getGlycemia() {
        return glycemia;
    }
}