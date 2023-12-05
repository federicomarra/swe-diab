package GUI;

import handheldTracker.UserInterface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

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
    private JPanel buttonPanel;
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
        frame = new JFrame("Diabetes Tracker GUI");

        // JPanel
        choosePanel = new JPanel();
        choosePanel.setLayout(new BoxLayout(choosePanel, BoxLayout.Y_AXIS));
        bolusPanel = new JPanel();
        bolusPanel.setLayout(new BoxLayout(bolusPanel, BoxLayout.Y_AXIS));
        buttonPanel = new JPanel();

        profilePanel = new JPanel();
        carbPanel = new JPanel();
        delayMinutesPanel = new JPanel();
        hourPanel = new JPanel();
        unitsPanel = new JPanel();

        /*
         * // Setto i sotto JPanel come righe orizzontali
         * choosePanel.setLayout(new BoxLayout(choosePanel, BoxLayout.X_AXIS));
         * bolusPanel.setLayout(new BoxLayout(bolusPanel, BoxLayout.X_AXIS));
         * profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.X_AXIS));
         * carbPanel.setLayout(new BoxLayout(carbPanel, BoxLayout.X_AXIS, ));
         * delayMinutesPanel.setLayout(new BoxLayout(delayMinutesPanel,
         * BoxLayout.X_AXIS));
         * hourPanel.setLayout(new BoxLayout(hourPanel, BoxLayout.X_AXIS));
         * unitsPanel.setLayout(new BoxLayout(unitsPanel, BoxLayout.X_AXIS));
         */

        // JComboBox
        chooseComboBox = new JComboBox<String>(new String[] { "Bolus", "Update Hourly Profile" });
        bolusComboBox = new JComboBox<String>(
                new String[] { "New Standard Bolus", "New Extended Bolus", "How Many Units", "New Pen Bolus" });
        profileComboBox = new JComboBox<String>(new String[] { "Update Basal Profile", "Update Carb Ratio Profile",
                "Update Insulin Sensitivity Profile" });
        executeButton = new JButton("Execute");

        // JLabel
        chooseLabel = new JLabel("Choose Option");
        bolusLabel = new JLabel("Bolus Options");
        profileLabel = new JLabel("Profile Options");
        carbLabel = new JLabel("Carb Value");
        hourLabel = new JLabel("Hour");
        unitsLabel = new JLabel("Units");

        // JTextField
        carbTextField = new JTextField(10);
        delayMinutesTextField = new JTextField(10);
        hourComboBox = new JComboBox<Integer>(
                new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18,
                        19, 20, 21, 22, 23, 24 });
        unitsTextField = new JTextField(10);

        carbTextField.setMaximumSize(carbTextField.getPreferredSize());
        delayMinutesTextField.setMaximumSize(delayMinutesTextField.getPreferredSize());
        unitsTextField.setMaximumSize(unitsTextField.getPreferredSize());
        hourComboBox.setMaximumSize(hourComboBox.getPreferredSize());
        chooseComboBox.setMaximumSize(chooseComboBox.getPreferredSize());
        bolusComboBox.setMaximumSize(bolusComboBox.getPreferredSize());
        delayMinutesTextField.setMaximumSize(delayMinutesTextField.getPreferredSize());
        profileComboBox.setMaximumSize(profileComboBox.getPreferredSize());

        initialize();
    }

    private void initialize() {
        // Configura il frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 500);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        // TODO: finire e far funzionare

        /*
         * // Aggiunge una progress bar
         * JProgressBar progressBar = new JProgressBar();
         * progressBar.setValue(0);
         * progressBar.setStringPainted(true);
         * frame.add(progressBar);
         * frame.add(Box.createVerticalStrut(10)); // Aggiungi uno spazio vuoto tra i
         * componenti
         */

        /*
         * // Aggiungo le varie componenti ai JPanel
         * profilePanel.add(profileLabel);
         * profilePanel.add(profileComboBox);
         * 
         * delayMinutesPanel.add(delayMinutesLabel);
         * delayMinutesPanel.add(delayMinutesTextField);
         * 
         * hourPanel.add(hourLabel);
         * hourPanel.add(hourComboBox);
         * 
         * unitsPanel.add(unitsLabel);
         * unitsPanel.add(unitsTextField);
         */

        profileLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        profileLabel.setBorder(new EmptyBorder(30, 0, 10, 0));
        choosePanel.add(profileLabel);

        choosePanel.add(profileComboBox);
        choosePanel.add(Box.createVerticalGlue());

        // Configura choosePanel
        chooseLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        chooseLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        choosePanel.add(chooseLabel);

        choosePanel.add(chooseComboBox);
        var spacer = Box.createVerticalGlue();
        choosePanel.add(spacer);

        // Configura bolusPanel
        bolusLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        bolusLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        choosePanel.add(bolusLabel);

        choosePanel.add(bolusComboBox);
        choosePanel.add(Box.createVerticalGlue());

        // Configura carbPanel
        carbLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        carbLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        bolusPanel.add(carbLabel);
        bolusPanel.add(carbTextField);

        // Aggiunge choosePanel e bolusPanel al frame
        frame.add(choosePanel);
        frame.add(bolusPanel);

        // Aggiunge executeButton al frame
        buttonPanel.add(executeButton);
        buttonPanel.setBorder(new EmptyBorder(20, 0, 30, 0));

        frame.add(Box.createVerticalGlue());
        frame.add(buttonPanel);

        frame.setVisible(true);

        var delayMinutesLabel = new JLabel("Delay Minutes");
        delayMinutesLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        delayMinutesLabel.setBorder(new EmptyBorder(10, 0, 10, 0));

        var unitsLabel = new JLabel("Units");
        unitsLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        unitsLabel.setBorder(new EmptyBorder(10, 0, 10, 0));

        var hourLabel = new JLabel("Hour");
        hourLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        hourLabel.setBorder(new EmptyBorder(10, 0, 10, 0));

        // Handle chooseComboBox change event
        chooseComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = chooseComboBox.getSelectedItem().toString();
                // BOLUS
                if (selectedOption.equals("Bolus")) {
                    System.out.println("Bolus");

                    choosePanel.remove(hourLabel);
                    choosePanel.remove(hourComboBox);
                    choosePanel.remove(unitsLabel);
                    choosePanel.remove(unitsTextField);

                    choosePanel.remove(spacer);
                    choosePanel.add(bolusLabel);
                    choosePanel.add(bolusComboBox);
                    // BASAL
                } else if (selectedOption.equals("Update Hourly Profile")) {
                    System.out.println("Update Hourly Profile");

                    choosePanel.remove(bolusLabel);
                    choosePanel.remove(bolusComboBox);

                    choosePanel.remove(carbLabel);
                    choosePanel.remove(carbTextField);

                    choosePanel.remove(delayMinutesLabel);
                    choosePanel.remove(delayMinutesTextField);
                    choosePanel.remove(spacer);
                }
                choosePanel.revalidate();
                choosePanel.repaint();
            }
        });

        // Handle bolusComboBox change event
        bolusComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bolusOption = bolusComboBox.getSelectedItem().toString();
                switch (bolusOption) {
                    case "New Standard Bolus":
                    case "How Many Units":
                        System.out.println("New Standard Bolus");
                        choosePanel.remove(delayMinutesLabel);
                        choosePanel.remove(delayMinutesTextField);
                        choosePanel.remove(unitsLabel);
                        choosePanel.remove(unitsTextField);

                        choosePanel.add(carbLabel);
                        choosePanel.add(carbTextField);
                        break;
                    case "New Extended Bolus":
                        System.out.println("New Extended Bolus");
                        choosePanel.remove(unitsLabel);
                        choosePanel.remove(unitsTextField);

                        choosePanel.add(carbLabel);
                        choosePanel.add(carbTextField);
                        choosePanel.add(delayMinutesLabel);
                        choosePanel.add(delayMinutesTextField);
                        break;
                    case "New Pen Bolus":
                        choosePanel.remove(delayMinutesLabel);
                        choosePanel.remove(delayMinutesTextField);

                        choosePanel.add(unitsLabel);
                        choosePanel.add(unitsTextField);
                        break;
                    default:
                        // Gestisci altri casi se necessario
                        break;
                }
                choosePanel.revalidate();
                choosePanel.repaint();
            }
        });

        // Handle profileComboBox change event
        profileComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String profileOption = profileComboBox.getSelectedItem().toString();
                switch (profileOption) {
                    case "Update Basal Profile":
                        System.out.println("Update Basal Profile");
                        choosePanel.remove(delayMinutesLabel);
                        choosePanel.remove(delayMinutesTextField);
                        choosePanel.remove(unitsLabel);
                        choosePanel.remove(unitsTextField);

                        choosePanel.add(hourLabel);
                        choosePanel.add(hourComboBox);
                        choosePanel.add(unitsLabel);
                        choosePanel.add(unitsTextField);
                        break;
                    case "Update Carb Ratio Profile":
                        System.out.println("Update Carb Ratio Profile");
                        choosePanel.remove(delayMinutesLabel);
                        choosePanel.remove(delayMinutesTextField);
                        choosePanel.remove(unitsLabel);
                        choosePanel.remove(unitsTextField);

                        choosePanel.add(hourLabel);
                        choosePanel.add(hourComboBox);
                        choosePanel.add(unitsLabel);
                        choosePanel.add(unitsTextField);
                        break;
                    case "Update Insulin Sensitivity Profile":
                        System.out.println("Update Insulin Sensitivity Profile");
                        choosePanel.remove(delayMinutesLabel);
                        choosePanel.remove(delayMinutesTextField);
                        choosePanel.remove(unitsLabel);
                        choosePanel.remove(unitsTextField);

                        choosePanel.add(hourLabel);
                        choosePanel.add(hourComboBox);
                        choosePanel.add(unitsLabel);
                        choosePanel.add(unitsTextField);
                        break;
                    default:
                        // Gestisci altri casi se necessario
                        break;
                }
                choosePanel.revalidate();
                choosePanel.repaint();
            }
        });

        // Handle executeButton click event
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
                ui.newExtendedBolus(Integer.parseInt(carbTextField.getText()),
                        Integer.parseInt(delayMinutesTextField.getText()));
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

        // Chiamate i metodi appropriati di UserInterface in base all'opzione
        // selezionata
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
