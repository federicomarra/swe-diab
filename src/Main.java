import handheldTracker.UserInterface;

public class Main {
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();

        System.out.println("\nWelcome to \nStarting bolus simulation:\n");


        // Bolus simulation

        // Randomize carb between -150 and 150 put in absolute value
        int carb = (int) (Math.abs(Math.random() * 300 - 150));
        // Randomize mode between 0 and 2
        int mode = (int) (Math.abs(Math.random() * 3));
        // Randomize delay between 1 and 3
        int delay = (int) (Math.abs(Math.random() * 3) + 1);
        // Randomize residual units (still active insulin)
        ui.newPenBolus((float) (((int) Math.abs(Math.random() * (30 - 1)) + 1) * 0.5));

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

        System.out.println("\n\nStarting profile update simulation:\n");

        // Profile update simulation

        // Randomize hour between 0 and 23
        int hour[] = new int[3];
        for (int i = 0; i < 3; i++)
            hour[i] = (int) Math.abs(Math.random() * 24);

        // Randomize units between 0.05 and 5 with 0.05 precision
        ui.updateBasalProfile((float) ((float) Math.abs(Math.random() * (5 - 0.01) + 0.1) / 0.05 * 0.05), hour[0]);
        ui.updateCarbRatioProfile((float) (int) Math.abs(Math.random() * (15 - 1) + 1), hour[1]);
        ui.updateInsulinSensitivityProfile((float) ((int) Math.abs(Math.random() * (50 - 20)) + 20), hour[2]);

        System.out.println("\n\nSimulation ended.");
    }
}

