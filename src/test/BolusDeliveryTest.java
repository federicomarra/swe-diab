package test;

import org.junit.Assert;
import org.junit.Test;

import handheldTracker.BolusDelivery;
import handheldTracker.BolusMode;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BolusDeliveryTest {

    @Test
    public void ConstructorTest() {
        // Randomize delay between 1 and 15 with 1 second precision
        int delay = (int) (Math.abs(Math.random() * 14) + 1);

        float[] units = new float[4];
        for (int i = 0; i < 2; i++) // Randomize units between 1 and 15 with 0.01 precision
            units[i] = (float) ((Math.abs(Math.random() * 1400) + 1) * 0.01);
        for (int j = 2; j < 4; j++) // Randomize units between 1 and 15 with 0.5 precision
            units[j] = (float) ((Math.abs(Math.random() * 140) + 1) * 0.5);

        // Standard bolus test
        BolusDelivery bd0 = new BolusDelivery(units[0], LocalTime.now(), BolusMode.STANDARD);
        Assert.assertEquals(units[0], bd0.units, 0);
        Assert.assertEquals(LocalTime.now().getSecond(), bd0.time.getSecond(), 0.01);
        Assert.assertEquals(BolusMode.STANDARD, bd0.mode);

        // Extended bolus test
        BolusDelivery bd1 = new BolusDelivery(units[1], LocalTime.now().plusMinutes(delay), BolusMode.EXTENDED);
        Assert.assertEquals(units[1], bd1.units, 0);
        Assert.assertEquals(LocalTime.now().plusMinutes(delay).getSecond(), bd1.time.getSecond(), 0.01);
        Assert.assertEquals(BolusMode.EXTENDED, bd1.mode);

        // Manual bolus test
        BolusDelivery bd2 = new BolusDelivery(units[2], LocalTime.now(), BolusMode.MANUAL);
        Assert.assertEquals(units[2], bd2.units, 0.01);
        Assert.assertEquals(LocalTime.now().getSecond(), bd2.time.getSecond(), 0.5);
        Assert.assertEquals(BolusMode.MANUAL, bd2.mode);

        // Pen bolus test
        BolusDelivery bd3 = new BolusDelivery(units[3], LocalTime.now(), BolusMode.PEN);
        Assert.assertEquals(units[3], bd3.units, 0.05);
        Assert.assertEquals(LocalTime.now().getSecond(), bd3.time.getSecond(), 0.5);
        Assert.assertEquals(BolusMode.PEN, bd3.mode);
    }

    @Test
    public void calculateResidualUnitsTest() {
        int hours = 3;
        int minutes = hours * 60; // 180 minutes
        List<BolusDelivery> bds = new ArrayList<>();
        float residualUnits = 0;

        // Element values
        float units = (float) (((int) Math.abs(Math.random() * 1500)) * 0.01); // Randomize units between 0 and 15 with 0.01 precision
        long delay = (long) Math.abs(Math.random() * minutes); // Randomize delay between 0 and 180 minutes
        LocalTime time = LocalTime.now().minusMinutes(delay); // Randomize time between now and 3 hours ago
        BolusDelivery bd = new BolusDelivery(units, time, BolusMode.STANDARD);
        residualUnits += units * (1 - ((float) delay / minutes));

        // Empty list test
        Assert.assertEquals(0, bd.calculateResidualUnits(bds), 0.01);

        // Not empty list test
        bds.add(bd);
        Assert.assertEquals(residualUnits, bd.calculateResidualUnits(bds), 0.01);
    }
}