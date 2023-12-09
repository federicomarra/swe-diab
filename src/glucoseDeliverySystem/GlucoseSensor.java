package glucoseDeliverySystem;

import utils.Measurement;

import java.time.LocalTime;

public class GlucoseSensor {

    public Measurement makeMeasurement() {
        int min = 60;
        int max = 300;
        int glycemia = (int) Math.abs(Math.random() * (max - min) + min);
        return new Measurement(glycemia, LocalTime.now());
    }
}
