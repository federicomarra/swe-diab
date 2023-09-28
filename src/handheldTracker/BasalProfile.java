package handheldTracker;

public class BasalProfile {
    public basalDelivery[24] basalDeliveries;

    public void updateBasalProfil(basalDelivery[] basals) {
        this.basalDeliveries = basals;
    }
    public basalDelivery[] getBasalProfile() {
        return basalDeliveries;
    }

    public updateBasalDelivery(BasalDelivery bd) {
        basalDeliveries[bd.hour] = bd;
        // basalDeliveries[bd.hour].units = bd.units;
    }
}
