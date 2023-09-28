package glucoseDeliverySystem;

import utils.HourlyProfile;
import utils.Mesurament;
import utils.Observer;
import java.util.ArrayList;
import java.util.List;

public class PumpManager {
    private GlucoseSensor sensor;
    private InsulinPump pump;
    private List<Observer> observers = new ArrayList<>();
    public HourlyProfile insulinSensitivityProfile;

    public void verifyAndInject(float units){
        if (units > 0 && units < 20) {
            pump.inject(units);
        } else {
            System.out.println("Invalid units");
        }
    }

    public Mesurament newMesurament(){
        return sensor.makeMesurament();
    }

    private List<Mesurament> getMesuraments() {
        return sensor.mesuraments;
    }

    public void subscribe(Observer o) {
        observers.add(o);
    }

    public void unsubscribe(Observer o) {
        observers.remove(o);
    }

    public void notifyObservers(){
        for(Observer observer: observers) {
            observer.update(getMesuraments());
        }
    }
}
