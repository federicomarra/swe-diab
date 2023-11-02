package handheldTracker;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

public class BolusDelivery {
    public float units;
    public LocalTime time;
    public BolusMode mode;

    BolusDelivery(float units, LocalTime time, BolusMode mode) {
        this.units = units;
        this.time = time;
        this.mode = mode;
    }

    public float calculateResidualUnits(List<BolusDelivery> bds) {  // bds = bolusDeliveries
        int hours = 3;
        float residualUnits = 0;
        if (!bds.isEmpty()) {
            // cycle init
            int i = 0;
            BolusDelivery lb = bds.get(bds.size() - 1); // init to last bolus
            Duration diff = Duration.between(lb.time, LocalTime.now());

            while (diff.toMinutes() <= hours * 60 && i < bds.size()) {   // while last bolus is not older than 3 hours
                // cycle iteration
                residualUnits += lb.units * (1 - (float) diff.toMinutes() / (hours * 60));    // linear decay
                // cycle increment
                i++;
                lb = bds.get(bds.size() - i);  // last bolus
                diff = Duration.between(lb.time, LocalTime.now());  // difference between last bolus time and now
            }
        }
        return residualUnits;
    }

}
