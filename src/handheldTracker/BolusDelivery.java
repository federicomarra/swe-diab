package handheldTracker;

import java.time.LocalTime;

public class BolusDelivery {
    public float units;
    public LocalTime time;
    public int delay;
    public BolusMode mode;

    BolusDelivery(float units, LocalTime time, int delay, BolusMode mode) {
        this.units = units;
        this.time = time;
        this.delay = delay;
        this.mode = mode;
    }

    public float calculateResidualUnits() {
        //TODO: implement
        return 0;
    }


}
