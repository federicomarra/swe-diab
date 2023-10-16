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
        int i = 0;
        Duration diff;
        float residualUnits = 0;
        do {    // calculate residual units from last 3 hours (linear decay)
            i++;
            BolusDelivery lb = bds.get(bds.size()-i);  // last bolus
            diff = Duration.between(lb.time, LocalTime.now());  // difference between last bolus time and now
            residualUnits += lb.units*(1 - (float)diff.toMinutes()/180);    // linear decay
        } while (diff.toMinutes() <= 180 && i < bds.size());    // while last bolus is not older than 3 hours
        residualUnits = Math.round(residualUnits / 0.01f) * 0.01f; // round to 2 decimal places
        return residualUnits;
    }

}
