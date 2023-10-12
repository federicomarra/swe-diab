package glucoseDeliverySystem;

import utils.Measurement;

import java.util.List;

public class GlucoseSensor {
    public List<Measurement> measuraments;
    public Measurement makeMesurament() {
        int min = 60;
        int max = 500;
        int glycemia = (int) (Math.random() * (max - min) + min);
        var m = new Measurement(glycemia, java.time.LocalTime.now());
        measuraments.add(m);
        return m;
    }
}
