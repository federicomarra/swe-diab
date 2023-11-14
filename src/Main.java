import handheldTracker.UserInterface;

public class Main {
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();

        System.out.println("\n\nStarting simulation:\n");

        // Randomize carb between -150 and 150 put in absolute value
        int carb = (int) (Math.abs(Math.random() * 300 - 150));
        // Randomize mode between 0 and 2
        int mode = (int) (Math.abs(Math.random() * 3));
        // Randomize delay between 1 and 3
        int delay = (int) (Math.abs(Math.random() * 3) + 1);
        // Randomize residual units (still active insulin)
        ui.newPenBolus((float) (((int) Math.abs(Math.random() * 29) + 1) * 0.5));

        switch (mode) {
            case 0:
                ui.newStandardBolus(carb);
                break;
            case 1:
                ui.newExtendedBolus(carb, delay);
                break;
            case 2:
                ui.howManyUnits(carb);
                break;
        }


        System.out.println("Exiting...");
    }
}

