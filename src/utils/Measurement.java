package utils;

import java.time.LocalTime;

// FIXME: These are the same, if we need to use java14 for
//        something else the use of record is justified.

// public record Measurement(int glycemia, LocalTime time) {}

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
}
