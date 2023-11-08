package test;

import handheldTracker.LocalDatabase;
import org.junit.Assert;
import org.junit.Test;

import handheldTracker.BolusDelivery;
import handheldTracker.BolusMode;
import utils.Measurement;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        // Reference glycemia
        int gref = 120;
        // Delay in seconds
        int delaySeconds = 0;
        // Randomize carb between -150 and 150 put in absolute value
        int c = (int) (Math.abs(Math.random() * (150 + 150) - 150));
        // Randomize glycemia between 160 and 300 put in absolute value
        int g = (int) (Math.abs(Math.random() * (300 - 160) + 160));
        // Generate new measurement and add it to the measurements list
        db.measurements.add(new Measurement(g, LocalTime.now()));
        // Carb sensitivity
        float csens = db.carbRatioProfile.hourlyFactors[LocalTime.now().getHour()].units();
        // Insulin sensitivity
        float isens = db.insulinSensitivityProfile.hourlyFactors[LocalTime.now().getHour()].units();
        // Units expected
        float u = (c / csens);
        if (g > gref) u += ((g - gref) / isens);
        // Randomization of mode
        int m = (int) (Math.abs(Math.random() * 3)); // Randomize between 0 and 2
        BolusMode mode = null;
        switch (m) {
            case 0: // Standard bolus test
                mode = BolusMode.STANDARD;
                break;
            case 1: // Extended bolus test
                mode = BolusMode.EXTENDED;
                // Randomize delay between 1 and 10 seconds
                delaySeconds = (int) (Math.abs(Math.random() * (10 - 1)) + 1);
                System.out.println("Delay: " + delaySeconds + " seconds");
                break;
            case 2: // Manual bolus test
                mode = BolusMode.MANUAL;
                // Approximation of units to 0.5
                BigDecimal bigDecimal = new BigDecimal(u);
                bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
                u = bigDecimal.divide(new BigDecimal("0.5"), 0, RoundingMode.HALF_UP).multiply(new BigDecimal("0.5")).floatValue();
                break;
            // Pen bolus case does not exist because it is not possible to call computeAndInject() with Pen mode
        }
        BolusDelivery bd = new BolusDelivery(u, LocalTime.now().plusSeconds(delaySeconds), mode);
        // inside this it is called computeAndInject() which is private
        db.newBolus(0, delaySeconds, bd.mode, c);
        Assert.assertEquals(1, db.bolusDeliveries.size());
        Assert.assertEquals(bd.units, db.bolusDeliveries.get(db.bolusDeliveries.size() - 1).units, 0.01);
        Assert.assertEquals(bd.time.getHour(), db.bolusDeliveries.get(db.bolusDeliveries.size() - 1).time.getHour());
        Assert.assertEquals(bd.time.getMinute(), db.bolusDeliveries.get(db.bolusDeliveries.size() - 1).time.getMinute());
        Assert.assertEquals(bd.time.getSecond(), db.bolusDeliveries.get(db.bolusDeliveries.size() - 1).time.getSecond());
        Assert.assertEquals(bd.mode, db.bolusDeliveries.get(db.bolusDeliveries.size() - 1).mode);
    }

    @Test
    public void updateHourlyFactorTest() {

    }
}