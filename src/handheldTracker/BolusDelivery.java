package handheldTracker;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

public class BolusDelivery {
    public float units;
    public LocalTime time;
    public BolusMode mode;

    public BolusDelivery(float units, LocalTime time, BolusMode mode) {
        this.units = units;
        this.time = time;
        this.mode = mode;
    }

    public float calculateResidualUnits(List<BolusDelivery> bds) {
        int hours = 3;
        float residualUnits = 0;
        if (!bds.isEmpty()) {
            // Cycle init
            int i = 0;
            // Init the last bolus
            BolusDelivery lb = bds.get(bds.size() - 1);
            Duration diff = Duration.between(lb.time, LocalTime.now());

            // While last bolus is not older than 3 hours
            while (diff.toMinutes() <= hours * 60 && i < bds.size()) {
                // Linear decay
                residualUnits += lb.units * (1 - (float) diff.toMinutes() / (hours * 60));
                // Cycle increment
                i++;
                // Last bolus
                lb = bds.get(bds.size() - i);
                // Difference between last bolus time and now
                diff = Duration.between(lb.time, LocalTime.now());
            }
        }
        return residualUnits;
    }
}
