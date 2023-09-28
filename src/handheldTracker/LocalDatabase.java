package handheldTracker;

public class LocalDatabase {
    private manager PumpManager;
    private backupDb BackupDatabase;

    public update(Mesurament[] mesurament) {
        PumpManager.update(mesurament[]);
        BackupDatabase.update(mesurament[]);
    }
    public computeAndInject(){

    }

    private backup(){
        backupDb.update();
    }
}
