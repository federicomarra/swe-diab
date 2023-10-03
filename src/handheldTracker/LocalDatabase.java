package handheldTracker;
import cloudInterface.BackupDatabase;
import glucoseDeliverySystem.PumpManager;
import utils.Database;
import utils.HourlyFactor;
import utils.Mesurament;
import utils.Observer;
import exceptions.BluetoothException;
import exceptions.InternetException;

import java.util.List;

public class LocalDatabase extends Database implements Observer {
    private PumpManager manager;
    private BackupDatabase backupDb;

    public void update(List<Mesurament> m) {
        this.mesuraments= m;
    }
    public void computeAndInject() throws BluetoothException {
        try {

            //manager.verifyAndInject(/*TODO: implement*/);


        } catch (Exception e) {
            throw new BluetoothException();
        }
    }

    private void backup() throws InternetException {
        try {
            //backupDb.update(); //TODO: update() to implement into BackupDatabase
        } catch (Exception e) {
            throw new InternetException();
        }
    }

    public void updateHourlyFactor(HourlyFactor hf) {
        updateHourlyFactor(hf);
    }
}
