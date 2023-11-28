package glucoseDeliverySystem;

import handheldTracker.BolusDelivery;
import handheldTracker.BolusMode;
import utils.Measurement;
import utils.Observer;

import java.time.LocalTime;
import java.util.List;

public class AutomaticBolus implements Observer, Runnable {
    public List<Measurement> measurements;
    private final PumpManager manager;

    public AutomaticBolus(PumpManager manager) {
        this.manager = PumpManager.getInstance(manager.insulinSensitivityProfile);
        this.measurements = manager.getMeasurements();
        //this.run();   // TODO: See if this is needed
    }

    private void evaluate(List<Measurement> m) {
        int lastGlyc = m.get(m.size() - 1).getGlycemia();
        float sensitivity = manager.insulinSensitivityProfile.hourlyFactors[LocalTime.now().getHour()].getUnits();
        if (lastGlyc > 180) {
            float units = (float) (lastGlyc - 120) / sensitivity;
            BolusDelivery bd = new BolusDelivery(units, LocalTime.now(), BolusMode.STANDARD);
            units -= bd.calculateResidualUnits(manager.bds);
            manager.verifyAndInject(units);
        }
    }

    public void update(List<Measurement> m) {
        this.measurements = m;
        evaluate(m);
    }

    public void run() { // executed every 15 minutes
        if (LocalTime.now().getMinute() % 15 == 0 && LocalTime.now().getSecond() == 0 && LocalTime.now().getNano() == 0)
            update(manager.getMeasurements());
    }

}
