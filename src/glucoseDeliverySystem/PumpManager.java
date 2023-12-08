package glucoseDeliverySystem;

import handheldTracker.BolusDelivery;
import handheldTracker.BolusMode;
import utils.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PumpManager {
    private final GlucoseSensor sensor;
    private final InsulinPump pump;
    private final List<Observer> observers = new ArrayList<>();
    public HourlyProfile insulinSensitivityProfile;
    public List<BolusDelivery> bds = new ArrayList<>();
    // private AutomaticBolus ab;

    private static PumpManager SingletonInstance;

    private PumpManager(HourlyProfile isp) {
        this.sensor = new GlucoseSensor();
        this.pump = new InsulinPump();
        this.insulinSensitivityProfile = isp;
        // FIXME: see how to make start automatic bolus
        // this.ab = new AutomaticBolus(this);
        // ab.run();
    }

    public static PumpManager getInstance(HourlyProfile isp) {
        if (SingletonInstance == null) {
            SingletonInstance = new PumpManager(isp);
        } else {
            SingletonInstance.insulinSensitivityProfile = isp; // update of insulin sensitivity;
        }
        return SingletonInstance;
    }

    public boolean verifyAndInject(float units) {
        if (units > 0 && units <= 20) {
            bds.add(new BolusDelivery(units, LocalTime.now(), BolusMode.STANDARD));
            pump.inject(units);
            return true;
        } else if (units > 20) {
            System.out.println("Too many units");
        } else {
            System.out.println("Invalid units");
        }
        return false;
    }

    public Measurement newMeasurement() {
        return sensor.makeMeasurement();
    }

    public List<Measurement> getMeasurements() {
        return sensor.measurements;
    }

    public void subscribe(Observer o) {
        observers.add(o);
    }

    public void unsubscribe(Observer o) {
        observers.remove(o);
    }

    public void unsubscribeAll() {
        observers.clear();
    }

    public void backup() {
        notifyObservers();
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(getMeasurements());
        }
    }

}
