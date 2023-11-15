package test;

import handheldTracker.UserInterface;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserInterfaceTest {
    @Test
    public void ConstructorTest() {
        UserInterface ui = new UserInterface();
        assertEquals(0, ui.bolusDeliveries.size());
        assertEquals(0, ui.measurements.size());
        assertEquals(24, ui.carbRatioProfile.hourlyFactors.length);
        assertEquals(24, ui.insulinSensitivityProfile.hourlyFactors.length);
        assertEquals(24, ui.basalProfile.hourlyFactors.length);
    }
}
