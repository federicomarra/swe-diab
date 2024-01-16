package test;

import glucoseDeliverySystem.PumpManager;
import utils.HourlyProfile;
import utils.ProfileMode;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GlucoseSensorTest {
    @Test
    public void constructorTest() {
        PumpManager pm = PumpManager.getInstance(new HourlyProfile(ProfileMode.IS));

        String m = pm.newMeasurement().toString();
        System.out.println("RandomTest: " + m);

        assertEquals(m, pm.getMeasurements().get(pm.getMeasurements().size() - 1).toString());
    }
}
