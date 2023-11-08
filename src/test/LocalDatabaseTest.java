package test;

import handheldTracker.LocalDatabase;
import org.junit.Assert;
import org.junit.Test;

import handheldTracker.BolusDelivery;
import handheldTracker.BolusMode;
import utils.Measurement;

import java.time.LocalTime;

public class LocalDatabaseTest {

    @Test
    public void ConstructorTest() {
        LocalDatabase db = new LocalDatabase();

        Assert.assertEquals(0, db.bolusDeliveries.size());
        Assert.assertEquals(0, db.measurements.size());
        Assert.assertEquals(24, db.carbRatioProfile.hourlyFactors.length);
        Assert.assertEquals(24, db.insulinSensitivityProfile.hourlyFactors.length);
        Assert.assertEquals(24, db.basalProfile.hourlyFactors.length);
    }

    @Test
    public void newBolusTest() {
        LocalDatabase db = new LocalDatabase();
        // Randomize units between 1 and 15 with 0.5 precision
        float u = (float) (((int) Math.abs(Math.random() * 290) + 10) * 0.5);

        // Pen bolus test
        BolusDelivery bd = new BolusDelivery(u, LocalTime.now(), BolusMode.PEN);
        db.newBolus(bd.units, 0, bd.mode, 0);

        Assert.assertEquals(1, db.bolusDeliveries.size());
        Assert.assertEquals(bd.units, db.bolusDeliveries.get(db.bolusDeliveries.size() - 1).units, 0.01);
        Assert.assertEquals(bd.time.getHour(), db.bolusDeliveries.get(db.bolusDeliveries.size() - 1).time.getHour(), 0);
        Assert.assertEquals(bd.time.getMinute(), db.bolusDeliveries.get(db.bolusDeliveries.size() - 1).time.getMinute(), 0);
        Assert.assertEquals(bd.time.getSecond(), db.bolusDeliveries.get(db.bolusDeliveries.size() - 1).time.getSecond(), 1);
        Assert.assertEquals(bd.mode, db.bolusDeliveries.get(db.bolusDeliveries.size() - 1).mode);
    }

    @Test
    public void computeAndInjectTest() {
        //computeAndInject() is private so it can be tested using newBolus()
        LocalDatabase db = new LocalDatabase();

        int gref = 120; // Reference glycemia

        int c = (int) (Math.abs(Math.random() * 300 - 150)); // Randomize carb between -150 and 150 put in absolute value
        int g = (int) (Math.abs(Math.random() * 300 - 160)); // Randomize glycemia between -150 and 150 put in absolute value
        float csens = db.carbRatioProfile.hourlyFactors[LocalTime.now().getHour()].units(); // Carb sensitivity
        float isens = db.insulinSensitivityProfile.hourlyFactors[LocalTime.now().getHour()].units();    // Insulin sensitivity
        float u = (c / csens);
        if (g > gref) u += ((g - gref) / isens);
        int m = (int) (Math.abs(Math.random() * 3)); // Randomize mode between 0 and 2
        db.measurements.add(new Measurement(g, LocalTime.now()));

        BolusDelivery bd = new BolusDelivery(u, LocalTime.now(), BolusMode.STANDARD);
        db.newBolus(0, 0, bd.mode, c);
        Assert.assertEquals(u, db.bolusDeliveries.get(db.bolusDeliveries.size() - 1).units, 0.1);
        Assert.assertEquals(bd.time.getHour(), db.bolusDeliveries.get(db.bolusDeliveries.size() - 1).time.getHour());
        Assert.assertEquals(bd.time.getMinute(), db.bolusDeliveries.get(db.bolusDeliveries.size() - 1).time.getMinute());
        Assert.assertEquals(bd.time.getSecond(), db.bolusDeliveries.get(db.bolusDeliveries.size() - 1).time.getSecond());
        Assert.assertEquals(bd.mode, db.bolusDeliveries.get(db.bolusDeliveries.size() - 1).mode);
        Assert.assertEquals(1, db.bolusDeliveries.size());

    /*
        float u = (float) (((int)Math.abs(Math.random() * 2900) + 100) * 0.01);
        float ue = (float) (((int)Math.abs(Math.random() * 2900) + 100) * 0.01);
        float um = (float) (((int)Math.abs(Math.random() * 29) + 1) * 0.5); // Randomize units between 1 and 15 with 0.5 precision
        int delay = (int) (Math.abs(Math.random() * 14) + 1); // Randomize delay between 1 and 15 with 1 second precision
        */
    }

    @Test
    public void updateHourlyFactorTest() {

    }
}