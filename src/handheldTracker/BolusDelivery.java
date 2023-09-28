package handheldTracker;
import java.time.LocalTime;

public class BolusDelivery {
    public float units;
    public LocalTime time;
    public int delay;
    public int mode;

    BolusDelivery(float units, LocalTime time, int delay, int mode) {
        this.units = units;
        this.time = time;
        this.delay = delay;
        this.mode = mode;
    }

    public calculateResidualUnits() {

    }


}
