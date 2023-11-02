package glucoseDeliverySystem;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class InsulinPump {
    public void inject(float units) {
        System.out.println("The insulin pump is delivering: " + String.format("%.2f", units) + " units at " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")) );
    }
}
