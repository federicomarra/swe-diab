package glucoseDeliverySystem;

import utils.*;

import java.util.ArrayList;
import java.util.List;

public class PumpManager {
    private GlucoseSensor sensor;
    private InsulinPump pump;
    private List<Observer> observers = new ArrayList<>();
    public HourlyProfile insulinSensitivityProfile;

    private static PumpManager SingletonInstance;

    private PumpManager(HourlyProfile isp) {
        this.sensor = new GlucoseSensor();
        this.pump = new InsulinPump();
        this.insulinSensitivityProfile = isp;
    }

    public static PumpManager getInstance(HourlyProfile isp) {
        if(SingletonInstance == null) {
            SingletonInstance = PumpManager.getInstance(isp);
        } else {
            SingletonInstance.insulinSensitivityProfile = isp;
        }
        return SingletonInstance;
    }

    public void verifyAndInject(float units) {
        if (units > 0 && units <= 20) {
            pump.inject(units);
        } else if (units > 20) {
            System.out.println("Too many units");
        } else {
            System.out.println("Invalid units");
        }
    }

    public Measurement newMeasurement() {
        return sensor.makeMeasurement();
    }

    private List<Measurement> getMeasurements() {
        return sensor.measurements;
    }

    public void subscribe(Observer o) {
        observers.add(o);
    }

    public void unsubscribe(Observer o) {
        observers.remove(o);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(getMeasurements());
        }
    }
}
