import handheldTracker.UserInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI {
    private JFrame frame;
    private UserInterface ui;
    private JPanel choosePanel;
    private JLabel chooseLabel;
    private JComboBox<String> chooseComboBox;
    private JPanel bolusPanel;
    private JLabel bolusLabel;
    private JComboBox<String> bolusComboBox;
    private JPanel profilePanel;
    private JLabel profileLabel;
    private JComboBox<String> profileComboBox;
    private JPanel carbPanel;
    private JLabel carbLabel;
    private JTextField carbTextField;
    private JPanel delayMinutesPanel;
    private JLabel delayMinutesLabel;
    private JTextField delayMinutesTextField;
    private JPanel hourPanel;
    private JLabel hourLabel;
    private JComboBox<Integer> hourComboBox;
    private JPanel unitsPanel;
    private JLabel unitsLabel;
    private JTextField unitsTextField;
    private JButton executeButton;

    public MainGUI() {
        ui = new UserInterface();
        frame = new JFrame("Your GUI Title");

        // JPanel
        choosePanel = new JPanel();
        bolusPanel = new JPanel();
        profilePanel = new JPanel();
        carbPanel = new JPanel();
        delayMinutesPanel = new JPanel();
        hourPanel = new JPanel();
        unitsPanel = new JPanel();

        /*
        // Setto i sotto JPanel come righe orizzontali
        choosePanel.setLayout(new BoxLayout(choosePanel, BoxLayout.X_AXIS));
        bolusPanel.setLayout(new BoxLayout(bolusPanel, BoxLayout.X_AXIS));
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.X_AXIS));
        carbPanel.setLayout(new BoxLayout(carbPanel, BoxLayout.X_AXIS, ));
        delayMinutesPanel.setLayout(new BoxLayout(delayMinutesPanel, BoxLayout.X_AXIS));
        hourPanel.setLayout(new BoxLayout(hourPanel, BoxLayout.X_AXIS));
        unitsPanel.setLayout(new BoxLayout(unitsPanel, BoxLayout.X_AXIS));
         */

        // JComboBox
        chooseComboBox = new JComboBox<>(new String[]{"Bolus", "Update Hourly Profile"});
        bolusComboBox = new JComboBox<>(new String[]{"New Standard Bolus", "New Extended Bolus", "How Many Units", "New Pen Bolus"});
        profileComboBox = new JComboBox<>(new String[]{"Update Basal Profile", "Update Carb Ratio Profile", "Update Insulin Sensitivity Profile"});
        executeButton = new JButton("Execute");

        // JLabel
        chooseLabel = new JLabel("Choose Option:");
        bolusLabel = new JLabel("Bolus Options:");
        profileLabel = new JLabel("Profile Options:");
        carbLabel = new JLabel("Carb Value:");
        hourLabel = new JLabel("Hour:");
        unitsLabel = new JLabel("Units:");

        // JTextField
        carbTextField = new JTextField(10);
        delayMinutesTextField = new JTextField(10);
        hourComboBox = new JComboBox<>(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24});
        unitsTextField = new JTextField(10);

        initialize();
    }

    private void initialize() {
        // Configura il frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        // TODO: finire e far funzionare

        /*
        // Aggiunge una progress bar
        JProgressBar progressBar = new JProgressBar();
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        frame.add(progressBar);
        frame.add(Box.createVerticalStrut(10)); // Aggiungi uno spazio vuoto tra i componenti
         */

        /*
        // Aggiungo le varie componenti ai JPanel
        profilePanel.add(profileLabel);
        profilePanel.add(profileComboBox);

        delayMinutesPanel.add(delayMinutesLabel);
        delayMinutesPanel.add(delayMinutesTextField);

        hourPanel.add(hourLabel);
        hourPanel.add(hourComboBox);

        unitsPanel.add(unitsLabel);
        unitsPanel.add(unitsTextField);
        */

        // Configura i choosePanel
        choosePanel.add(chooseLabel);
        choosePanel.add(chooseComboBox);
        // Configura bolusPanel
        bolusPanel.add(bolusLabel);
        bolusPanel.add(bolusComboBox);
        // Configura carbPanel
        bolusPanel.add(carbLabel);
        bolusPanel.add(carbTextField);
        // Aggiunge choosePanel al frame
        frame.add(choosePanel);
        // Aggiunge carbPanel a bolusPanel
        //bolusPanel.add(carbPanel);
        // Aggiunge bolusPanel al frame
        frame.add(bolusPanel);
        // Aggiunge executeButton al frame
        frame.add(executeButton);


        // Handle chooseOptionComboBox
        chooseComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = chooseComboBox.getSelectedItem().toString();
                // BOLUS
                if (selectedOption.equals("Bolus")) {
                    System.out.println("Bolus");

                    choosePanel.remove(profileComboBox);
                    choosePanel.remove(hourComboBox);
                    choosePanel.remove(unitsTextField);

                    choosePanel.add(bolusLabel);
                    choosePanel.add(bolusComboBox);



                    // BASAL
                } else if (selectedOption.equals("UpdateHourlyProfile")) {
                    System.out.println("UpdateHourlyProfile");

                    choosePanel.remove(bolusComboBox);
                    choosePanel.remove(carbTextField);
                    choosePanel.remove(delayMinutesTextField);



                }
            }

        });

        bolusComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bolusOption = bolusComboBox.getSelectedItem().toString();
                switch (bolusOption) {
                    case "New Standard Bolus", "How Many Units":
                        System.out.println("New Standard Bolus");
                        choosePanel.remove(delayMinutesTextField);
                        choosePanel.remove(unitsTextField);

                        choosePanel.add(new JLabel("Carb Value:"));
                        choosePanel.add(carbTextField);
                        break;
                    case "New Extended Bolus":
                        System.out.println("New Extended Bolus");
                        choosePanel.remove(unitsTextField);

                        choosePanel.add(new JLabel("Carb Value:"));
                        choosePanel.add(carbTextField);
                        choosePanel.add(new JLabel("Delay Minutes:"));
                        choosePanel.add(delayMinutesTextField);
                        break;
                    case "New Pen Bolus":
                        choosePanel.add(new JLabel("Units:"));
                        choosePanel.add(unitsTextField);
                        break;
                    default:
                        // Gestisci altri casi se necessario
                        break;
                }
            }
        });

        profileComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String profileOption = profileComboBox.getSelectedItem().toString();
                switch (profileOption) {
                    case "Update Basal Profile":
                        System.out.println("Update Basal Profile");
                        choosePanel.remove(delayMinutesTextField);
                        choosePanel.remove(unitsTextField);

                        choosePanel.add(hourLabel);
                        choosePanel.add(hourComboBox);
                        choosePanel.add(unitsLabel);
                        choosePanel.add(unitsTextField);
                        break;
                    case "Update Carb Ratio Profile":
                        System.out.println("Update Carb Ratio Profile");
                        choosePanel.remove(delayMinutesTextField);
                        choosePanel.remove(unitsTextField);

                        choosePanel.add(new JLabel("Hour:"));
                        choosePanel.add(hourComboBox);
                        choosePanel.add(new JLabel("Units:"));
                        choosePanel.add(unitsTextField);
                        break;
                    case "Update Insulin Sensitivity Profile":
                        System.out.println("Update Insulin Sensitivity Profile");
                        choosePanel.remove(delayMinutesTextField);
                        choosePanel.remove(unitsTextField);

                        choosePanel.add(new JLabel("Hour:"));
                        choosePanel.add(hourComboBox);
                        choosePanel.add(new JLabel("Units:"));
                        choosePanel.add(unitsTextField);
                        break;
                    default:
                        // Gestisci altri casi se necessario
                        break;
                }
            }
        });
        frame.update(frame.getGraphics());

        /*
        // Aggiungi il pannello al frame
        frame.getContentPane().add(choosePanel);


         */
        // Rendi il frame visibile
        frame.setVisible(true);

        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = chooseComboBox.getSelectedItem().toString();
                if (selectedOption.equals("Bolus")) {
                    executeBolusOption();
                } else if (selectedOption.equals("Update Hourly Profile")) {
                    executeUpdateHourlyProfileOption();
                }
            }
        });

    }

    private void executeBolusOption() {
        String bolusOption = bolusComboBox.getSelectedItem().toString();
        // Chiama i metodi appropriati di UserInterface in base all'opzione selezionata
        switch (bolusOption) {
            case "New Standard Bolus":
                ui.newStandardBolus(Integer.parseInt(carbTextField.getText()));
                break;
            case "New Extended Bolus":
                ui.newExtendedBolus(Integer.parseInt(carbTextField.getText()), Integer.parseInt(delayMinutesTextField.getText()));
                break;
            case "How Many Units":
                ui.howManyUnits(Integer.parseInt(carbTextField.getText()));
                break;
            case "New Pen Bolus":
                ui.newPenBolus(Float.parseFloat(unitsTextField.getText()));
                break;
        }
    }


    private void executeUpdateHourlyProfileOption() {
        String profileOption = profileComboBox.getSelectedItem().toString();
        int hour = (int) hourComboBox.getSelectedItem();
        float units = Float.parseFloat(unitsTextField.getText());
        // Chiamate i metodi appropriati di UserInterface in base all'opzione selezionata
        switch (profileOption) {
            case "Update Basal Profile":
                ui.updateBasalProfile(units, hour);
                break;
            case "Update Carb Ratio Profile":
                ui.updateCarbRatioProfile(units, hour);
                break;
            case "Update Insulin Sensitivity Profile":
                ui.updateInsulinSensitivityProfile(units, hour);
                break;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new MainGUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

