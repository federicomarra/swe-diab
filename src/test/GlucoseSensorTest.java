package test;

import glucoseDeliverySystem.GlucoseSensor;

import org.junit.Test;

import static org.junit.Assert.*;

public class GlucoseSensorTest {
    @Test
    public void ConstructorTest() {
        GlucoseSensor gs = new GlucoseSensor();
        assertTrue(gs.measurements.isEmpty());
        assertEquals(0, gs.measurements.size());
    }
}
