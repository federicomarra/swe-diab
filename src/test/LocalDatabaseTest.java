package test;

import handheldTracker.LocalDatabase;
import org.junit.Test;

import static org.junit.Assert.*;

import handheldTracker.BolusDelivery;
import handheldTracker.BolusMode;
import utils.HourlyFactor;
import utils.Measurement;
import utils.ProfileMode;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;

public class LocalDatabaseTest {

    @Test
    public void ConstructorTest() {
        LocalDatabase db = new LocalDatabase();

        assertEquals(0, db.bolusDeliveries.size());
        assertEquals(0, db.measurements.size());
        assertEquals(24, db.carbRatioProfile.hourlyFactors.length);
        assertEquals(24, db.insulinSensitivityProfile.hourlyFactors.length);
        assertEquals(24, db.basalProfile.hourlyFactors.length);
    }

    @Test
    public void newBolusTest() {
        LocalDatabase db = new LocalDatabase();
        // Randomize units between 1 and 15 with 0.5 precision
        float u = (float) (((int) Math.abs(Math.random() * 290) + 10) * 0.5);

        // Pen bolus test
        BolusDelivery bd = new BolusDelivery(u, LocalTime.now(), BolusMode.PEN);
        db.newBolus(bd.units, 0, bd.mode, 0);

        assertEquals(1, db.bolusDeliveries.size());
        assertEquals(bd.units, db.bolusDeliveries.get(db.bolusDeliveries.size() - 1).units, 0.01);
        assertEquals(bd.time.getHour(), db.bolusDeliveries.get(db.bolusDeliveries.size() - 1).time.getHour(), 0);
        assertEquals(bd.time.getMinute(), db.bolusDeliveries.get(db.bolusDeliveries.size() - 1).time.getMinute(), 0);
        assertEquals(bd.time.getSecond(), db.bolusDeliveries.get(db.bolusDeliveries.size() - 1).time.getSecond(), 1);
        assertEquals(bd.mode, db.bolusDeliveries.get(db.bolusDeliveries.size() - 1).mode);
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
        float csens = db.carbRatioProfile.hourlyFactors[LocalTime.now().getHour()].getUnits();
        // Insulin sensitivity
        float isens = db.insulinSensitivityProfile.hourlyFactors[LocalTime.now().getHour()].getUnits();
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
                // Randomize delay between 1 and 5 seconds
                delaySeconds = (int) (Math.abs(Math.random() * (5 - 1)) + 1);
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
        // inside newBolus() it is called computeAndInject() which being private, this is the only way to test it
        db.newBolus(0, delaySeconds, bd.mode, c);
        assertEquals(1, db.bolusDeliveries.size());
        assertEquals(bd.units, db.bolusDeliveries.get(db.bolusDeliveries.size() - 1).units, 0.01);
        assertEquals(bd.time.getHour(), db.bolusDeliveries.get(db.bolusDeliveries.size() - 1).time.getHour());
        assertEquals(bd.time.getMinute(), db.bolusDeliveries.get(db.bolusDeliveries.size() - 1).time.getMinute());
        assertEquals(bd.time.getSecond(), db.bolusDeliveries.get(db.bolusDeliveries.size() - 1).time.getSecond());
        assertEquals(bd.mode, db.bolusDeliveries.get(db.bolusDeliveries.size() - 1).mode);
    }

    @Test
    public void updateHourlyFactorTest() {
        LocalDatabase db = new LocalDatabase();
        int hours[] = new int[3];
        float units[] = new float[3];
        for (int i = 0; i < 3; i++)
            hours[i] = (int) (Math.abs(Math.random() * 24));

        // Basal profile test
        units[0] = (float) ((float) (Math.random() * (5 - 0.01) + 0.1) / 0.05 * 0.05);
        System.out.println("Basal: " + units[0]);
        HourlyFactor hf0 = new HourlyFactor(units[0], hours[0]);
        db.updateHourlyFactor(hf0, ProfileMode.BASAL);
        assertEquals(hours[0], db.basalProfile.hourlyFactors[hours[0]].getHour(), 0);
        assertEquals(units[0], db.basalProfile.hourlyFactors[hours[0]].getUnits(), 0.05);

        // Carb ratio profile test
        units[1] = (float) ((int) Math.abs(Math.random() * (15 - 1) + 1));
        HourlyFactor hf1 = new HourlyFactor(units[1], hours[1]);
        System.out.println("Carb: " + units[1]);
        db.updateHourlyFactor(hf1, ProfileMode.IC);
        assertEquals(hours[1], db.carbRatioProfile.hourlyFactors[hours[1]].getHour(), 0);
        assertEquals(units[1], db.carbRatioProfile.hourlyFactors[hours[1]].getUnits(), 0);

        // Insulin sensitivity profile test
        units[2] = (float) ((int) Math.abs(Math.random() * (50 - 20) + 20));
        HourlyFactor hf2 = new HourlyFactor(units[2], hours[2]);
        System.out.println("Insulin: " + units[2]);
        db.updateHourlyFactor(hf2, ProfileMode.IG);
        assertEquals(hf2.getHour(), db.insulinSensitivityProfile.hourlyFactors[hours[2]].getHour(), 0);
        assertEquals(units[2], db.insulinSensitivityProfile.hourlyFactors[hours[2]].getUnits(), 5);
    }
}