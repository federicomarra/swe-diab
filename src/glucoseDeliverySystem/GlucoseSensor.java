package glucoseDeliverySystem;

import utils.Mesurament;

import java.util.List;

public class GlucoseSensor {
    public List<Mesurament> mesuraments;
    public Mesurament makeMesurament() {
        int min = 60;
        int max = 500;
        int glycemia = (int) (Math.random() * (max - min) + min);
        var m = new Mesurament(glycemia, java.time.LocalTime.now());
        mesuraments.add(m);
        return m;
    }
}
