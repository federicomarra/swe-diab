package glucoseDeliverySystem;

import utils.Mesurament;
import utils.Observer;

import java.time.LocalTime;
import java.util.List;

public class AutomaticBolus implements Observer {
    public List<Mesurament> mesuraments;
    private PumpManager manager;

    public void update(List<Mesurament> m) {
        this.mesuraments = m;
        evaluate(m);
    }

    private evaluate(List<Mesurament> m){
        //TODO: implement get insulinSensitivity based on time from insulinSensitivityProfile
        var lm = m.get(m.size()-1);
        var sensitivity = lm.insulinSensitivityProfile[(int)(LocalTime.now().getHour())];
        if (lm.glycemia() > 180){
            var units = (lm.glycemia() - 120) / sensitivity;
            sendBolus(units);
        }
    }

    private void sendBolus(float units){
        manager.verifyAndInject(units);
    }

    /* da esempio task che lavora ogni 3h:
        import java.util.concurrent.Executors;
        import java.util.concurrent.ScheduledExecutorService;
        import java.util.concurrent.TimeUnit;

        public class Main {

            public static void main(String[] args) {
                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

                // Runnable che contiene la tua logica da eseguire ogni 3 ore
                Runnable task = () -> {
                    // Inserisci qui la logica che vuoi eseguire
                    System.out.println("Funzione eseguita ogni 3 ore");
                };

                // Pianifica l'esecuzione del task ogni 3 ore
                scheduler.scheduleAtFixedRate(task, 0, 3, TimeUnit.HOURS);
            }
        }

     */
}
