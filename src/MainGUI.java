import handheldTracker.UserInterface;
import utils.HourlyProfile;
import utils.HistoryEntry;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI {
    private JFrame frame;
    private UserInterface ui;
    private JPanel mainPanel;
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
    private JPanel basalProfileCsv;
    private JPanel carbRatioProfileCsv;
    private JPanel insulinSensitivityProfileCsv;
    private Component horizontalSpacer;
    private JPanel historyPanel;
    private JPanel historyList;
    private JButton backupButton;

    public MainGUI() {
        ui = new UserInterface();

        frame = new JFrame("Diabetes Tracker GUI");

        // JPanel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        choosePanel = new JPanel();
        choosePanel.setLayout(new BoxLayout(choosePanel, BoxLayout.Y_AXIS));

        bolusPanel = new JPanel();
        bolusPanel.setLayout(new BoxLayout(bolusPanel, BoxLayout.Y_AXIS));

        buttonPanel = new JPanel();

        profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));

        carbPanel = new JPanel();
        carbPanel.setLayout(new BoxLayout(carbPanel, BoxLayout.Y_AXIS));

        delayMinutesPanel = new JPanel();
        delayMinutesPanel.setLayout(new BoxLayout(delayMinutesPanel, BoxLayout.Y_AXIS));

        hourPanel = new JPanel();
        hourPanel.setLayout(new BoxLayout(hourPanel, BoxLayout.Y_AXIS));

        unitsPanel = new JPanel();
        unitsPanel.setLayout(new BoxLayout(unitsPanel, BoxLayout.Y_AXIS));

        historyPanel = new JPanel();
        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));

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
        backupButton = new JButton("Backup");

        // JLabel
        chooseLabel = new JLabel("Choose Option");
        bolusLabel = new JLabel("Bolus Options");
        profileLabel = new JLabel("Profile Options");
        carbLabel = new JLabel("Carb Value");
        hourLabel = new JLabel("Hour");
        unitsLabel = new JLabel("Units");

        // JTextField
        carbTextField = new JTextField(10);
        carbTextField.setText("0");
        delayMinutesTextField = new JTextField(10);
        delayMinutesTextField.setText("1");
        hourComboBox = new JComboBox<Integer>(
                new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18,
                        19, 20, 21, 22, 23, 24 });
        unitsTextField = new JTextField(10);
        unitsTextField.setText("0");

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
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));

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

        // Configura choosePanel
        chooseLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        chooseLabel.setBorder(new EmptyBorder(30, 0, 10, 0));

        choosePanel.add(chooseLabel);
        choosePanel.add(chooseComboBox);

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(choosePanel);
        var spacer = Box.createVerticalGlue();
        mainPanel.add(spacer);

        // Configura mainPanel
        profileLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        profileLabel.setBorder(new EmptyBorder(10, 0, 10, 0));

        profilePanel.add(profileLabel);
        profilePanel.add(profileComboBox);

        // Configura bolusPanel
        bolusLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        bolusLabel.setBorder(new EmptyBorder(10, 0, 10, 0));

        bolusPanel.add(bolusLabel);
        bolusPanel.add(bolusComboBox);

        mainPanel.add(bolusPanel);
        mainPanel.add(Box.createVerticalGlue());

        // Configura carbPanel
        carbLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        carbLabel.setBorder(new EmptyBorder(10, 0, 10, 0));

        carbPanel.add(carbLabel);
        carbPanel.add(carbTextField);

        mainPanel.add(carbPanel);

        // Aggiunge executeButton al frame
        buttonPanel.add(executeButton);
        buttonPanel.setBorder(new EmptyBorder(20, 0, 30, 0));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(backupButton);

        var delayMinutesLabel = new JLabel("Delay Minutes");
        delayMinutesLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        delayMinutesLabel.setBorder(new EmptyBorder(10, 0, 10, 0));

        delayMinutesPanel.add(delayMinutesLabel);
        delayMinutesPanel.add(delayMinutesTextField);

        var unitsLabel = new JLabel("Units");
        unitsLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        unitsLabel.setBorder(new EmptyBorder(10, 0, 10, 0));

        unitsPanel.add(unitsLabel);
        unitsPanel.add(unitsTextField);

        var hourLabel = new JLabel("Hour");
        hourLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        hourLabel.setBorder(new EmptyBorder(10, 0, 10, 0));

        hourPanel.add(hourLabel);
        hourPanel.add(hourComboBox);

        // Crea un JPanel che contiene mainPanel e buttonPanel
        var mainAndButtonPanel = new JPanel();
        mainAndButtonPanel.setLayout(new BoxLayout(mainAndButtonPanel, BoxLayout.Y_AXIS));
        mainAndButtonPanel.add(mainPanel);
        mainAndButtonPanel.add(Box.createVerticalGlue());
        mainAndButtonPanel.add(buttonPanel);

        // Create a Jpanel that contains the rows of db.basalProfile
        var basalProfile = ui.getDb().basalProfile;
        basalProfileCsv = createHourList(basalProfile, "Basal Profile");

        // Create a Jpanel that contains the rows of db.carbRatioProfile
        var carbRatioProfile = ui.getDb().carbRatioProfile;
        carbRatioProfileCsv = createHourList(carbRatioProfile, "Carb Ratio Profile");

        // Create a Jpanel that contains the rows of db.insulinSensitivityProfile
        var insulinSensitivity = ui.getDb().insulinSensitivityProfile;
        insulinSensitivityProfileCsv = createHourList(insulinSensitivity, "Insulin Sensitivity Profile");

        // Aggiunge i JPanel laterali al frame principale
        historyList = createHistoryList(ui.getHistory(), "History");
        historyPanel.add(historyList);

        frame.add(Box.createHorizontalGlue());
        frame.add(historyPanel);
        frame.add(Box.createHorizontalGlue());

        frame.add(mainAndButtonPanel);
        frame.add(Box.createHorizontalGlue());
        // frame.add(basalProfileCsv);

        horizontalSpacer = Box.createHorizontalGlue();

        frame.setVisible(true);
        frame.setSize(850, 650);

        var hourSpacer = Box.createVerticalGlue();
        var unitSpacer = Box.createVerticalGlue();

        // Handle chooseComboBox change event
        chooseComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = chooseComboBox.getSelectedItem().toString();
                // BOLUS
                if (selectedOption.equals("Bolus")) {
                    System.out.println("Bolus");

                    mainPanel.remove(hourSpacer);
                    mainPanel.remove(hourPanel);
                    mainPanel.remove(unitSpacer);
                    mainPanel.remove(unitsPanel);
                    mainPanel.remove(profilePanel);

                    frame.remove(basalProfileCsv);
                    frame.remove(carbRatioProfileCsv);
                    frame.remove(insulinSensitivityProfileCsv);
                    frame.remove(horizontalSpacer);

                    mainPanel.add(spacer);
                    mainPanel.add(bolusPanel);
                    mainPanel.add(spacer);
                    mainPanel.add(carbPanel);
                }

                // BASAL
                if (selectedOption.equals("Update Hourly Profile")) {
                    System.out.println("Update Hourly Profile");

                    mainPanel.remove(bolusPanel);
                    mainPanel.remove(carbPanel);
                    mainPanel.remove(delayMinutesPanel);
                    mainPanel.remove(spacer);

                    frame.add(basalProfileCsv);
                    frame.add(horizontalSpacer);

                    mainPanel.add(profilePanel);
                    mainPanel.add(hourSpacer);
                    mainPanel.add(hourPanel);
                    mainPanel.add(unitSpacer);
                    mainPanel.add(unitsPanel);
                }
                frame.revalidate();
                frame.repaint();
            }
        });

        var basalProfileSpacer = Box.createVerticalGlue();

        // Handle bolusComboBox change event
        bolusComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bolusOption = bolusComboBox.getSelectedItem().toString();
                switch (bolusOption) {
                    case "New Standard Bolus":
                    case "How Many Units":
                        System.out.println("New Standard Bolus");

                        mainPanel.remove(delayMinutesPanel);
                        mainPanel.remove(hourPanel);
                        mainPanel.remove(unitsPanel);
                        mainPanel.remove(basalProfileSpacer);

                        mainPanel.add(carbPanel);
                        break;
                    case "New Extended Bolus":
                        System.out.println("New Extended Bolus");

                        mainPanel.remove(hourPanel);
                        mainPanel.remove(unitsPanel);

                        mainPanel.add(carbPanel);
                        mainPanel.add(basalProfileSpacer);
                        mainPanel.add(delayMinutesPanel);
                        break;
                    case "New Pen Bolus":
                        System.out.println("New Pen Bolus");

                        mainPanel.remove(delayMinutesPanel);
                        mainPanel.remove(hourPanel);
                        mainPanel.remove(carbPanel);
                        mainPanel.remove(basalProfileSpacer);

                        mainPanel.add(unitsPanel);
                        break;
                    default:
                        // Gestisci altri casi se necessario
                        break;
                }
                frame.revalidate();
                frame.repaint();
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

                        frame.remove(carbRatioProfileCsv);
                        frame.remove(insulinSensitivityProfileCsv);

                        frame.add(basalProfileCsv);
                        frame.add(horizontalSpacer);
                        break;
                    case "Update Carb Ratio Profile":
                        System.out.println("Update Carb Ratio Profile");

                        frame.remove(basalProfileCsv);
                        frame.remove(insulinSensitivityProfileCsv);

                        frame.add(carbRatioProfileCsv);
                        frame.add(horizontalSpacer);
                        break;
                    case "Update Insulin Sensitivity Profile":
                        System.out.println("Update Insulin Sensitivity Profile");

                        frame.remove(basalProfileCsv);
                        frame.remove(carbRatioProfileCsv);

                        frame.add(insulinSensitivityProfileCsv);
                        frame.add(horizontalSpacer);
                        break;
                    default:
                        // Gestisci altri casi se necessario
                        break;
                }
                frame.revalidate();
                frame.repaint();
            }
        });

        // Handle executeButton click event
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = chooseComboBox.getSelectedItem().toString();
                if (selectedOption.equals("Bolus")) {
                    executeBolusOption();

                    historyPanel.remove(historyList);
                    historyList = createHistoryList(ui.getHistory(), "History");
                    historyPanel.add(historyList);

                    frame.revalidate();
                    frame.repaint();
                } else if (selectedOption.equals("Update Hourly Profile")) {
                    executeUpdateHourlyProfileOption();
                }
            }
        });

        // Handle backupButton click event
        backupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: implement backup
            }
        });
    }

    private void executeBolusOption() {
        String bolusOption = bolusComboBox.getSelectedItem().toString();

        // Chiama i metodi appropriati di UserInterface in base all'opzione selezionata
        switch (bolusOption) {
            case "New Standard Bolus":
                if (Float.parseFloat(carbTextField.getText()) >= 0
                        && Float.parseFloat(carbTextField.getText()) <= 150) {
                    ui.newStandardBolus(Integer.parseInt(carbTextField.getText()));
                    carbTextField.setText("0");
                    break;
                }

                JOptionPane.showMessageDialog(frame, "Wrong bolus value, should be between 1 and 150.", "Bolus",
                        JOptionPane.ERROR_MESSAGE);
                carbTextField.setText("0");

                break;
            case "New Extended Bolus":
                if (Float.parseFloat(carbTextField.getText()) >= 0
                        && Float.parseFloat(carbTextField.getText()) <= 150
                        && Integer
                                .parseInt(delayMinutesTextField.getText()) >= 1
                        && Integer
                                .parseInt(delayMinutesTextField.getText()) <= 60) {
                    ui.newExtendedBolus(Integer.parseInt(carbTextField.getText()),
                            Integer.parseInt(delayMinutesTextField.getText()));
                    carbTextField.setText("0");
                    delayMinutesTextField.setText("1");
                    break;
                }

                if (Integer.parseInt(delayMinutesTextField.getText()) < 1
                        || Integer.parseInt(delayMinutesTextField.getText()) > 60) {
                    JOptionPane.showMessageDialog(frame, "Wrong delay value, should be between 1 and 60 minutes.",
                            "Delay",
                            JOptionPane.ERROR_MESSAGE);
                    delayMinutesTextField.setText("1");
                    break;
                }

                JOptionPane.showMessageDialog(frame, "Wrong bolus value, should be between 1 and 150.", "Bolus",
                        JOptionPane.ERROR_MESSAGE);
                carbTextField.setText("0");
                delayMinutesTextField.setText("1");
                break;
            case "How Many Units":
                if (Float.parseFloat(carbTextField.getText()) >= 0
                        && Float.parseFloat(carbTextField.getText()) <= 150) {
                    ui.howManyUnits(Integer.parseInt(carbTextField.getText()));
                    carbTextField.setText("0");
                    break;
                }

                JOptionPane.showMessageDialog(frame, "Wrong bolus value, should be between 1 and 150.", "Bolus",
                        JOptionPane.ERROR_MESSAGE);
                carbTextField.setText("0");
            case "New Pen Bolus":
                if (Float.parseFloat(unitsTextField.getText()) >= 0
                        && Float.parseFloat(unitsTextField.getText()) <= 15) {
                    ui.newPenBolus(Float.parseFloat(unitsTextField.getText()));
                    unitsTextField.setText("0");
                    break;
                }

                JOptionPane.showMessageDialog(frame, "Wrong units value, should be between 0 and 15.", "Units",
                        JOptionPane.ERROR_MESSAGE);
                unitsTextField.setText("0");
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
                frame.remove(horizontalSpacer);
                frame.remove(basalProfileCsv);

                ui.updateBasalProfile(units, hour);
                var basalProfile = ui.getDb().basalProfile;
                basalProfileCsv = createHourList(basalProfile, "Basal Profile");

                frame.add(basalProfileCsv);
                frame.add(horizontalSpacer);
                break;
            case "Update Carb Ratio Profile":
                frame.remove(horizontalSpacer);
                frame.remove(carbRatioProfileCsv);

                ui.updateCarbRatioProfile(units, hour);
                var carbRatioProfile = ui.getDb().carbRatioProfile;
                carbRatioProfileCsv = createHourList(carbRatioProfile, "Carb Ratio Profile");

                frame.add(carbRatioProfileCsv);
                frame.add(horizontalSpacer);
                break;
            case "Update Insulin Sensitivity Profile":
                frame.remove(horizontalSpacer);
                frame.remove(insulinSensitivityProfileCsv);

                ui.updateInsulinSensitivityProfile(units, hour);
                var insulinSensitivity = ui.getDb().insulinSensitivityProfile;
                insulinSensitivityProfileCsv = createHourList(insulinSensitivity, "Insulin Sensitivity Profile");

                frame.add(insulinSensitivityProfileCsv);
                frame.add(horizontalSpacer);
                break;
        }
        frame.revalidate();
        frame.repaint();
    }

    private JPanel createHourList(HourlyProfile profile, String title) {
        var profileCsv = new JPanel();
        profileCsv.setLayout(new BoxLayout(profileCsv, BoxLayout.Y_AXIS));
        profileCsv.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        var profileLabel = new JLabel(title);
        profileLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        profileLabel.setBorder(new EmptyBorder(30, 0, 10, 0));

        profileCsv.add(profileLabel);

        for (int i = 0; i < profile.hourlyFactors.length; i++) {
            var profileRow = new JPanel();
            profileRow.setLayout(new BoxLayout(profileRow, BoxLayout.X_AXIS));
            var profileHour = new JLabel("Hour " + profile.hourlyFactors[i].getHour());
            var profileUnits = new JLabel("Units " + profile.hourlyFactors[i].getUnits());

            profileHour.setBorder(new EmptyBorder(0, 0, 0, 10));
            profileRow.add(profileHour);
            profileRow.add(profileUnits);
            profileCsv.add(profileRow);
        }
        profileCsv.setBorder(new EmptyBorder(0, 0, 30, 30));

        return profileCsv;
    }

    private JPanel createHistoryList(HistoryEntry[] list, String title) {
        var listCsv = new JPanel();
        listCsv.setLayout(new BoxLayout(listCsv, BoxLayout.Y_AXIS));
        listCsv.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        var listLabel = new JLabel(title);
        listLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        listLabel.setBorder(new EmptyBorder(30, 0, 10, 0));

        listCsv.add(listLabel);

        for (int i = 0; i < list.length; i++) {
            var listRow = new JPanel();
            listRow.setLayout(new BoxLayout(listRow, BoxLayout.X_AXIS));
            var time = list[i].getTime();
            var listTime = new JLabel(time.getDayOfMonth() + "/" + time.getMonthValue() + "/" + time.getYear() +
                    " " + String.format("%02d", time.getHour())
                    + ":" + String.format("%02d", time.getMinute()));
            var listGlycemia = new JLabel(list[i].getGlycemia() + " mg/dL");
            var listUnits = new JLabel(list[i].getUnits() + " U");

            listTime.setBorder(new EmptyBorder(0, 0, 0, 10));
            listRow.add(listTime);
            listGlycemia.setBorder(new EmptyBorder(0, 0, 0, 10));
            listRow.add(listGlycemia);
            listRow.add(listUnits);
            listCsv.add(listRow);
        }

        listCsv.setBorder(new EmptyBorder(0, 0, 30, 30));

        return listCsv;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.put("OptionPane.background", new Color(34, 34, 34));
                    UIManager.put("OptionPane.messageForeground", Color.WHITE);
                    UIManager.put("Panel.background", new Color(34, 34, 34));
                    UIManager.put("Label.foreground", Color.WHITE);
                    new MainGUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
