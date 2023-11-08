package glucoseDeliverySystem;

import utils.Measurement;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class GlucoseSensor {
    public List<Measurement> measurements;

    public GlucoseSensor() {
        this.measurements = new ArrayList<>();
    }

    public Measurement makeMeasurement() {
        int min = 60;
        int max = 300;
        int glycemia = (int) Math.abs(Math.random() * (max - min) + min);
        Measurement m = new Measurement(glycemia, LocalTime.now());
        measurements.add(m);
        return m;
    }
}
