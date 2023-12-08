package utils;

import java.time.LocalTime;

public class Measurement {
    private int glycemia;
    private LocalTime time;

    public Measurement(int glycemia, LocalTime time) {
        this.glycemia = glycemia;
        this.time = time;
    }

    public int getGlycemia() {
        return this.glycemia;
    }

    public void setGlycemia(int glycemia) {
        this.glycemia = glycemia;
    }

    public LocalTime getTime() {
        return this.time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "glycemia=" + glycemia +
                ", time=" + time +
                '}';
    }
}
