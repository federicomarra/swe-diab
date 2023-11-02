package utils;

import java.time.LocalTime;

// FIXME: These are the same, if we need to use java14 for
//        something else the use of record is justified.

// public record Measurement(int glycemia, LocalTime time) {}

public class Measurement {
    int glycemia;
    LocalTime time;

    public Measurement(int glycemia, LocalTime time) {
        this.glycemia = glycemia;
        this.time = time;
    }
    
    public int glycemia() {
        return this.glycemia;
    }

    public LocalTime time() {
        return this.time;
    }
}
