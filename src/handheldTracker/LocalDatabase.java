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
    public void update(List<Measurement> ms) {
        try {
            super.update(ms);
            backup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void computeAndInject(int carb, BolusMode mode) throws BluetoothException {
        try {
            Measurement lm = measurements.get(measurements.size()-1);  // last mesurament
            Duration diff = Duration.between(lm.time(), LocalTime.now());
            if (diff.toMinutes() > 10)  // if last mesurament is older than 10 minutes
                lm = manager.newMeasurement();   // get new mesurament
            HourlyFactor sensitivity = insulinSensitivityProfile.hourlyFactors[LocalTime.now().getHour()];
            HourlyFactor carbRatio = carbRatioProfile.hourlyFactors[LocalTime.now().getHour()];
            float units = 0;

            if (lm.glycemia() > 180)
                units += ((float)(lm.glycemia() - 120)) / sensitivity.units();
            if (carb > 0 && carb <= 150)
                units += carb / carbRatio.units();
            addBolus(new BolusDelivery(units, LocalTime.now(), mode));
            if(units > 0 && (mode == BolusMode.STANDARD || mode == BolusMode.EXTENDED))
                manager.verifyAndInject(units);
            else if (units > 0 && (mode == BolusMode.PEN || mode == BolusMode.MANUAL))
                addBolus(new BolusDelivery(units, LocalTime.now(), mode));
                System.out.println("You should manually inject " + Math.round(units) + " units");
        } catch (Exception e) {
            e.printStackTrace();
            // throw new BluetoothException();
        }
    }

    private void backup() throws InternetException {
        try {
            backupDb.update(this.measurements); //TODO: update() to implement into BackupDatabase
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateHourlyFactor(HourlyFactor hf) {
        updateHourlyFactor(hf);
    }

    public void addBolus(BolusDelivery bd) {
        bolusDeliveries.add(bd);
    }
}
