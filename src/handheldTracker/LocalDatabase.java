package handheldTracker;
import cloudInterface.BackupDatabase;
import glucoseDeliverySystem.PumpManager;
import utils.*;
import exceptions.BluetoothException;
import exceptions.InternetException;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

public class LocalDatabase extends Database implements Observer {
    private PumpManager manager;
    private BackupDatabase backupDb;

    public LocalDatabase(PumpManager manager) {
        this.manager = manager;
        this.backupDb = new BackupDatabase();
    }
    public void update(List<Mesurament> m) {
        try {
            backup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void computeAndInject(int carb, BolusMode mode) throws BluetoothException {
        try {
            Mesurament lm = mesuraments.get(mesuraments.size()-1);  // last mesurament
            Duration diff = Duration.between(lm.time(), LocalTime.now());
            if (diff.toMinutes() > 10)
                lm = manager.newMesurament();
            HourlyFactor sensitivity = insulinSensitivityProfile.hourlyFactors[LocalTime.now().getHour()];
            HourlyFactor carbRatio = carbRatioProfile.hourlyFactors[LocalTime.now().getHour()];
            float units = 0;

            if (lm.glycemia() > 180)
                units += ((float)(lm.glycemia() - 120)) / sensitivity.units();
            if (carb > 0 && carb <= 150)
                units += carb / carbRatio.units();
            addBolus(new BolusDelivery(units, LocalTime.now(), 0, mode));
            if(units > 0 && (mode == BolusMode.STANDARD || mode == BolusMode.EXTENDED))
                manager.verifyAndInject(units);
            else if (units > 0 && (mode == BolusMode.PEN || mode == BolusMode.MANUAL))
                addBolus(new BolusDelivery(units, LocalTime.now(), 0, mode));
                System.out.println("You should manually inject " + Math.round(units) + " units");
        } catch (Exception e) {
            e.printStackTrace();
            // throw new BluetoothException();
        }
    }

    private void backup() throws InternetException {
        try {
            //backupDb.update(); //TODO: update() to implement into BackupDatabase
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateHourlyFactor(HourlyFactor hf) {
        updateHourlyFactor(hf);
    }

    public void addBolus(BolusDelivery bolusDelivery) {
        bolusDeliveries.add(bolusDelivery);
    }
}
