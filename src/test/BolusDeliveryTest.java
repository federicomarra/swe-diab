package test;

import org.junit.Test;

import static org.junit.Assert.*;

import handheldTracker.BolusDelivery;
import handheldTracker.BolusMode;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BolusDeliveryTest {

    @Test
    public void ConstructorTest() {
        float[] units = new float[4];
        // Randomize units between 1 and 15 with 0.01 precision (Standard and Extended modes)
        for (int i = 0; i < 2; i++)
            units[i] = (float) (((int) Math.abs(Math.random() * 2900) + 100) * 0.01);
        // Randomize units between 1 and 15 with 0.5 precision (Manual and Pen modes)
        for (int j = 2; j < 4; j++)
            units[j] = (float) (((int) Math.abs(Math.random() * 29) + 1) * 0.5);
        // Randomize delay between 1 and 15 with 1 minute precision (Extended mode)
        int delay = (int) (Math.abs(Math.random() * 14) + 1);

        // Standard bolus test
        BolusDelivery bd0 = new BolusDelivery(units[0], LocalTime.now(), BolusMode.STANDARD);
        assertEquals(units[0], bd0.units, 0);
        assertEquals(LocalTime.now().getHour(), bd0.time.getHour(), 0.01);
        assertEquals(LocalTime.now().getMinute(), bd0.time.getMinute(), 0.01);
        assertEquals(LocalTime.now().getSecond(), bd0.time.getSecond(), 0.01);
        assertEquals(BolusMode.STANDARD, bd0.mode);

        // Extended bolus test
        BolusDelivery bd1 = new BolusDelivery(units[1], LocalTime.now().plusMinutes(delay), BolusMode.EXTENDED);
        assertEquals(units[1], bd1.units, 0);
        assertEquals(LocalTime.now().getHour(), bd1.time.getHour(), 0.01);
        assertEquals(LocalTime.now().plusMinutes(delay).getMinute(), bd1.time.getMinute(), 0.01);
        assertEquals(LocalTime.now().getSecond(), bd1.time.getSecond(), 0.01);
        assertEquals(BolusMode.EXTENDED, bd1.mode);

        // Manual bolus test
        BolusDelivery bd2 = new BolusDelivery(units[2], LocalTime.now(), BolusMode.MANUAL);
        assertEquals(units[2], bd2.units, 0.01);
        assertEquals(LocalTime.now().getHour(), bd2.time.getHour(), 0.01);
        assertEquals(LocalTime.now().getMinute(), bd2.time.getMinute(), 0.01);
        assertEquals(LocalTime.now().getSecond(), bd2.time.getSecond(), 0.5);
        assertEquals(BolusMode.MANUAL, bd2.mode);

        // Pen bolus test
        BolusDelivery bd3 = new BolusDelivery(units[3], LocalTime.now(), BolusMode.PEN);
        assertEquals(units[3], bd3.units, 0.05);
        assertEquals(LocalTime.now().getHour(), bd3.time.getHour(), 0.01);
        assertEquals(LocalTime.now().getMinute(), bd3.time.getMinute(), 0.01);
        assertEquals(LocalTime.now().getSecond(), bd3.time.getSecond(), 0.5);
        assertEquals(BolusMode.PEN, bd3.mode);
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
        assertEquals(0, bd.calculateResidualUnits(bds), 0.01);

        // Not empty list test
        bds.add(bd);
        assertEquals(residualUnits, bd.calculateResidualUnits(bds), 0.01);
    }
}