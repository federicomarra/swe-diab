package handheldTracker;

public class BasalProfil {
    public basalDelivery[24] basalDeliveries;

    public void updateBasalProfil(basalDelivery[] basals) {
        this.basalDeliveries = basals;
    }
    public basalDelivery[] getBasalProfil() {
        return basalDeliveries;
    }

    public updateBasalDelivery(BasalDelivery bd) {
        basalDeliveries[bd.hour] = bd;
        // basalDeliveries[bd.hour].units = bd.units;
    }
}
