import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;


//Registration → Adult/Child details → Daily log with rich summary.
public class HealthTrackerGUI extends JFrame {
    private static final String CARD_REGISTRATION = "REGISTRATION";
    private static final String CARD_ADULT = "ADULT";
    private static final String CARD_CHILD = "CHILD";
    private static final String CARD_DAILY_LOG = "DAILY_LOG";

    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    // Registration
    private JTextField nameField;
    private JTextField dateField;
    private JComboBox<String> userTypeCombo;
    private JComboBox<String> genderCombo;

    // Adult details
    private JTextField adultAgeField;
    private JTextField adultHeightField;
    private JTextField adultWeightField;
    private JTextArea adultHealthIssuesArea;

    // Child details
    private JTextField childAgeField;
    private JTextField childHeightField;
    private JTextField childWeightField;
    private JComboBox<String> childVaccinationCombo;

    // Daily log
    private JTextField stepsField;
    private JTextField sleepField;
    private JTextField waterField;
    private JTextArea moodField;
    private JTextField screenTimeField;
    private JTextField workStudyField;
    private JLabel workStudyLabel;
    private JLabel userInfoLabel;
    private JTextArea summaryArea;

    // Registration hand-off
    private User currentUser;
    private String pendingName;
    private String pendingUserType;
    private String pendingGender;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public HealthTrackerGUI() {
        setTitle("Smart Health Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        cardPanel.add(createRegistrationPanel(), CARD_REGISTRATION);
        cardPanel.add(createAdultDetailsPanel(), CARD_ADULT);
        cardPanel.add(createChildDetailsPanel(), CARD_CHILD);
        cardPanel.add(createDailyLogPanel(), CARD_DAILY_LOG);
        
        add(cardPanel);
        cardLayout.show(cardPanel, CARD_REGISTRATION);
    }
    
    private JPanel createRegistrationPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel title = new JLabel("Register a user profile");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy++;
        panel.add(new JLabel("Full name"), gbc);
        nameField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(nameField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("User type"), gbc);
        userTypeCombo = new JComboBox<>(new String[]{"Adult", "Child"});
        gbc.gridx = 1;
        panel.add(userTypeCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Gender"), gbc);
        genderCombo = new JComboBox<>(new String[]{"Male", "Female", "Other", "Prefer not to say"});
        gbc.gridx = 1;
        panel.add(genderCombo, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Current date (auto-filled)"), gbc);
        dateField = new JTextField(LocalDate.now().format(dateFormatter), 20);
        dateField.setEditable(false);
        dateField.setFocusable(false);
        gbc.gridx = 1;
        panel.add(dateField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton nextButton = new JButton("Continue");
        nextButton.addActionListener(e -> handleBasicRegistration());
        panel.add(nextButton, gbc);

        return panel;
    }

    private JPanel createAdultDetailsPanel() {
        JPanel panel = createDetailsPanelTemplate("Adult details");

        adultAgeField = addLabeledField(panel, "Age (years)", 1);
        adultHeightField = addLabeledField(panel, "Height (cm)", 2);
        adultWeightField = addLabeledField(panel, "Weight (kg)", 3);

        JLabel issuesLabel = new JLabel("Health issues / allergies");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panel.add(issuesLabel, gbc);

        adultHealthIssuesArea = new JTextArea(4, 20);
        adultHealthIssuesArea.setLineWrap(true);
        adultHealthIssuesArea.setWrapStyleWord(true);
        JScrollPane issuesScroll = new JScrollPane(adultHealthIssuesArea);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(issuesScroll, gbc);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton back = new JButton("Back");
        back.addActionListener(e -> cardLayout.show(cardPanel, CARD_REGISTRATION));
        JButton continueBtn = new JButton("Go to daily log");
        continueBtn.addActionListener(e -> handleAdultDetails());
        buttons.add(back);
        buttons.add(continueBtn);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(buttons, gbc);

        return panel;
    }

    private JPanel createChildDetailsPanel() {
        JPanel panel = createDetailsPanelTemplate("Child user details");

        childAgeField = addLabeledField(panel, "Age (years)", 1);
        childHeightField = addLabeledField(panel, "Height (cm)", 2);
        childWeightField = addLabeledField(panel, "Weight (kg)", 3);

        JLabel vaccinationLabel = new JLabel("Vaccination status");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(vaccinationLabel, gbc);

        childVaccinationCombo = new JComboBox<>(new String[]{
            "Up to date",
            "Partially vaccinated",
            "Not vaccinated",
            "Unknown"
        });
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(childVaccinationCombo, gbc);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton back = new JButton("Back");
        back.addActionListener(e -> cardLayout.show(cardPanel, CARD_REGISTRATION));
        JButton continueBtn = new JButton("Go to daily log");
        continueBtn.addActionListener(e -> handleChildDetails());
        buttons.add(back);
        buttons.add(continueBtn);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(buttons, gbc);
        
        return panel;
    }
    
    private JPanel createDailyLogPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel(new BorderLayout());
        userInfoLabel = new JLabel("No user selected");
        userInfoLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        JButton backButton = new JButton("Start over");
        backButton.addActionListener(e -> resetFlow());
        topPanel.add(userInfoLabel, BorderLayout.WEST);
        topPanel.add(backButton, BorderLayout.EAST);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        JLabel title = new JLabel("Daily wellness log");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerPanel.add(title, gbc);

        stepsField = addLogField(centerPanel, "Steps", 1);
        sleepField = addLogField(centerPanel, "Sleep hours", 2);
        waterField = addLogField(centerPanel, "Water intake (liters)", 3);

        JLabel moodLabel = new JLabel("Mood / energy");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        centerPanel.add(moodLabel, gbc);
        moodField = new JTextArea(8, 32);
        moodField.setLineWrap(true);
        moodField.setWrapStyleWord(true);
        JScrollPane moodScroll = new JScrollPane(moodField);
        moodScroll.setPreferredSize(new Dimension(360, 180));
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        centerPanel.add(moodScroll, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        screenTimeField = addLogField(centerPanel, "Screen time (hours)", 5);
        workStudyLabel = new JLabel("Work / Study hours");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(workStudyLabel, gbc);
        workStudyField = new JTextField(15);
        gbc.gridx = 1;
        centerPanel.add(workStudyField, gbc);
        
        JButton submitButton = new JButton("Save daily log");
        submitButton.addActionListener(e -> handleDailyLog());
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(submitButton, gbc);
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        summaryArea = new JTextArea(15, 60);
        summaryArea.setEditable(false);
        summaryArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane summaryScroll = new JScrollPane(summaryArea);
        summaryScroll.setBorder(BorderFactory.createTitledBorder("User summary & health metrics"));
        mainPanel.add(summaryScroll, BorderLayout.SOUTH);

        return mainPanel;
    }
    
    private JPanel createDetailsPanelTemplate(String title) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel label = new JLabel(title);
        label.setFont(new Font("SansSerif", Font.BOLD, 24));
        panel.add(label, gbc);

        return panel;
    }

    private JTextField addLabeledField(JPanel panel, String label, int row) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.gridy = row;

        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel(label), gbc);

        JTextField field = new JTextField(20);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(field, gbc);
        return field;
    }

    private JTextField addLogField(JPanel panel, String label, int row) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel(label), gbc);

        JTextField field = new JTextField(15);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(field, gbc);
        return field;
    }

    private void handleBasicRegistration() {
        try {
            String name = nameField.getText().trim();
            String userType = (String) userTypeCombo.getSelectedItem();
            String gender = (String) genderCombo.getSelectedItem();

            if (name.isEmpty()) {
                throw new IllegalArgumentException("Please provide the user's name.");
            }

            if (CSVHandler.userExists(name)) {
                try {
                    currentUser = CSVHandler.loadUser(name);
                    pendingUserType = (currentUser instanceof AdultUser) ? "Adult" : "Child";
                    pendingGender = currentUser.getGender();
                    userTypeCombo.setSelectedItem(pendingUserType);
                    if (pendingGender != null) {
                        genderCombo.setSelectedItem(pendingGender);
                    }
                    JOptionPane.showMessageDialog(this,
                        "Welcome back, " + currentUser.getName() + "! Loading your daily log.",
                        "User found", JOptionPane.INFORMATION_MESSAGE);
                    enterDailyLog();
                    return;
                } catch (Exception ex) {
                    throw new IOException("Unable to load user profile: " + ex.getMessage(), ex);
                }
            }

            pendingName = name;
            pendingUserType = userType;
            pendingGender = gender;

            if ("Adult".equals(userType)) {
                clearAdultDetailFields();
                cardLayout.show(cardPanel, CARD_ADULT);
            } else {
                clearChildDetailFields();
                cardLayout.show(cardPanel, CARD_CHILD);
            }

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Registration error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                "Unable to look up user records: " + e.getMessage(),
                "Lookup error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleAdultDetails() {
        try {
            ensurePendingUser();

            int age = Integer.parseInt(adultAgeField.getText().trim());
            double height = Double.parseDouble(adultHeightField.getText().trim());
            double weight = Double.parseDouble(adultWeightField.getText().trim());

            validateDemographics(age, height, weight, true);

            String issues = adultHealthIssuesArea.getText().trim();
            currentUser = new AdultUser(pendingName, age, pendingGender, height, weight, issues);
            CSVHandler.saveUser(currentUser);

            enterDailyLog();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Age, height, and weight must be numeric.", "Input error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to save adult user: " + e.getMessage(), "Storage error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Validation error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleChildDetails() {
        try {
            ensurePendingUser();

            int age = Integer.parseInt(childAgeField.getText().trim());
            double height = Double.parseDouble(childHeightField.getText().trim());
            double weight = Double.parseDouble(childWeightField.getText().trim());

            validateDemographics(age, height, weight, false);

            String vaccination = (String) childVaccinationCombo.getSelectedItem();
            currentUser = new ChildUser(pendingName, age, pendingGender, height, weight, vaccination);
            CSVHandler.saveUser(currentUser);

            enterDailyLog();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Age, height, and weight must be numeric.", "Input error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to save child user: " + e.getMessage(), "Storage error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Validation error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void enterDailyLog() {
        if (currentUser instanceof AdultUser) {
            pendingUserType = "Adult";
            pendingGender = currentUser.getGender();
        } else if (currentUser instanceof ChildUser) {
            pendingUserType = "Child";
            pendingGender = currentUser.getGender();
        }
        updateDailyLogFieldAvailability();
        updateUserSummaryLabel();
        clearDailyLogFields(false);
        cardLayout.show(cardPanel, CARD_DAILY_LOG);
    }

    private void handleDailyLog() {
        try {
            if (currentUser == null) {
                throw new IllegalStateException("Please complete registration first.");
            }

            int steps = parseIntField(stepsField, "Steps");
            double sleepHours = parseDoubleField(sleepField, "Sleep hours", 0, 24);
            double waterIntake = parseNonNegativeDouble(waterField, "Water intake");
            double screenTime = parseDoubleField(screenTimeField, "Screen time", 0, 24);
            double workStudyHours = parseDoubleField(workStudyField, workStudyLabel.getText(), 0, 24);
            
            String mood = moodField.getText().trim();
            if (mood.isEmpty()) {
                mood = "Not specified";
            }
            
            HealthMetrics metrics = new HealthMetrics(
                currentUser.getName(),
                LocalDate.now(),
                steps,
                sleepHours,
                waterIntake,
                mood,
                screenTime,
                workStudyHours
            );
            
            CSVHandler.saveHealthMetrics(metrics);
            displayHealthAnalysis(metrics);
            clearDailyLogFields(true);
            JOptionPane.showMessageDialog(this, "Daily log stored and summary updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please provide numeric values only where required.", "Input error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Validation error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to save daily metrics: " + e.getMessage(), "Storage error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayHealthAnalysis(HealthMetrics metrics) {
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════════════\n");
        sb.append("User summary for ").append(currentUser.getName()).append("\n");
        sb.append("Registered as ").append(pendingUserType).append("\n");
        sb.append("Log date: ").append(metrics.getDate().format(dateFormatter)).append("\n");
        sb.append("═══════════════════════════════════════════════\n\n");

        sb.append("Profile\n");
        sb.append("───────\n");
        sb.append("Age: ").append(currentUser.getAge()).append(" years\n");
        sb.append("Height: ").append(String.format("%.1f cm", currentUser.getHeight())).append("\n");
        sb.append("Weight: ").append(String.format("%.1f kg", currentUser.getWeight())).append("\n");
        sb.append("BMI: ").append(String.format("%.2f", currentUser.calculateBMI()))
          .append(" (").append(currentUser.getBMICategory()).append(")\n\n");

        sb.append("Daily log\n");
        sb.append("─────────\n");
        sb.append("Steps: ").append(metrics.getSteps()).append("\n");
        sb.append("Sleep: ").append(String.format("%.1f h", metrics.getSleepHours())).append("\n");
        sb.append("Water: ").append(String.format("%.1f L", metrics.getWaterIntake())).append("\n");
        sb.append("Mood: ").append(metrics.getMood()).append("\n");
        sb.append("Screen time: ").append(String.format("%.1f h", metrics.getScreenTime())).append("\n");
        sb.append(workStudyLabel.getText()).append(": ").append(String.format("%.1f h", metrics.getWork_StudyHours())).append("\n\n");

        sb.append("Health metrics\n");
        sb.append("──────────────\n");
        sb.append("Calories burnt: ").append(String.format("%.0f kcal", metrics.CaloriesBurnt(metrics.getSteps()))).append("\n");
        sb.append("Sleep quality: ").append(metrics.CalculateSleepQuality(metrics.getSleepHours())).append("\n");
        sb.append("Happiness level: ").append(metrics.CalculateHappinessLevel(metrics.getMood())).append("\n");
        sb.append("Burnout level: ").append(getBurnoutLevel(metrics.getWork_StudyHours())).append("\n\n");

        sb.append("Suggestions\n");
        sb.append("───────────\n");
        sb.append(metrics.getHealthSuggestions()).append("\n\n");

        appendHistorySection(sb);

        summaryArea.setText(sb.toString());
    }

    private void appendHistorySection(StringBuilder sb) {
        if (currentUser == null) {
            return;
        }
        sb.append("History\n");
        sb.append("───────\n");
        try {
            List<HealthMetrics> history = CSVHandler.getUserMetrics(currentUser.getName());
            if (history.isEmpty()) {
                sb.append("No previous logs found. Start logging to build your history.\n");
                return;
            }
            history.sort(Comparator.comparing(HealthMetrics::getDate).reversed());
            for (HealthMetrics entry : history) {
                sb.append(entry.getDate().format(dateFormatter)).append(" | ");
                sb.append(String.format("Steps: %d, Sleep: %.1fh, Water: %.1fL, Screen: %.1fh, %s: %.1fh\n",
                    entry.getSteps(),
                    entry.getSleepHours(),
                    entry.getWaterIntake(),
                    entry.getScreenTime(),
                    (currentUser instanceof AdultUser) ? "Work" : "Study",
                    entry.getWork_StudyHours()));
                sb.append("Mood: ").append(entry.getMood()).append("\n");
                sb.append("Suggestions:\n").append(entry.getHealthSuggestions());
                sb.append("\n");
            }
        } catch (IOException e) {
            sb.append("Unable to load history: ").append(e.getMessage()).append("\n");
        }
    }

    private void clearDailyLogFields(boolean preserveSummary) {
        if (stepsField != null) stepsField.setText("");
        if (sleepField != null) sleepField.setText("");
        if (waterField != null) waterField.setText("");
        if (screenTimeField != null) screenTimeField.setText("");
        if (workStudyField != null) workStudyField.setText("");
        if (moodField != null) moodField.setText("");
        if (!preserveSummary && summaryArea != null) {
            summaryArea.setText("");
        }
    }

    private void updateDailyLogFieldAvailability() {
        if (workStudyLabel == null || workStudyField == null) {
            return;
        }
        if (currentUser instanceof AdultUser) {
            workStudyLabel.setText("Work hours");
            workStudyField.setToolTipText("Log productive / commuting hours");
        } else {
            workStudyLabel.setText("Study hours");
            workStudyField.setToolTipText("Log study / homework time");
        }
    }

    private void clearAdultDetailFields() {
        if (adultAgeField != null) adultAgeField.setText("");
        if (adultHeightField != null) adultHeightField.setText("");
        if (adultWeightField != null) adultWeightField.setText("");
        if (adultHealthIssuesArea != null) adultHealthIssuesArea.setText("");
    }

    private void clearChildDetailFields() {
        if (childAgeField != null) childAgeField.setText("");
        if (childHeightField != null) childHeightField.setText("");
        if (childWeightField != null) childWeightField.setText("");
        if (childVaccinationCombo != null) childVaccinationCombo.setSelectedIndex(0);
    }

    private void updateUserSummaryLabel() {
        if (userInfoLabel != null && currentUser != null) {
            String today = LocalDate.now().format(dateFormatter);
            userInfoLabel.setText(String.format(
                "Date: %s | %s | Age %d | BMI %.2f (%s)",
                today,
                currentUser.getName(),
                currentUser.getAge(),
                currentUser.calculateBMI(),
                currentUser.getBMICategory()
            ));
        }
    }

    private void ensurePendingUser() {
        if (pendingName == null || pendingUserType == null || pendingGender == null) {
            throw new IllegalStateException("Start from the registration screen first.");
        }
    }

    private void validateDemographics(int age, double height, double weight, boolean adult) {
        if (adult && age < 18) {
            throw new IllegalArgumentException("Adult must be at least 18 years old.");
        }
        if (!adult && age >= 18) {
            throw new IllegalArgumentException("Child must be under 18 years old.");
        }
        if (height <= 0 || height > 300) {
            throw new IllegalArgumentException("Height must be within 1 - 300 cm.");
        }
        if (weight <= 0 || weight > 500) {
            throw new IllegalArgumentException("Weight must be within 1 - 500 kg.");
        }
    }

    private int parseIntField(JTextField field, String label) {
        String value = field.getText().trim();
        if (value.isEmpty()) {
            throw new IllegalArgumentException("Please fill in " + label + ".");
        }
        int parsed = Integer.parseInt(value);
        if (parsed < 0) {
            throw new IllegalArgumentException(label + " cannot be negative.");
        }
        return parsed;
    }

    private double parseDoubleField(JTextField field, String label, double min, double max) {
        String value = field.getText().trim();
        if (value.isEmpty()) {
            throw new IllegalArgumentException("Please fill in " + label + ".");
        }
        double parsed = Double.parseDouble(value);
        if (parsed < min || parsed > max) {
            throw new IllegalArgumentException(label + " must be between " + min + " and " + max + ".");
        }
        return parsed;
    }

    private double parseNonNegativeDouble(JTextField field, String label) {
        String value = field.getText().trim();
        if (value.isEmpty()) {
            throw new IllegalArgumentException("Please fill in " + label + ".");
        }
        double parsed = Double.parseDouble(value);
        if (parsed < 0) {
            throw new IllegalArgumentException(label + " cannot be negative.");
        }
        return parsed;
    }

    private void resetFlow() {
        currentUser = null;
        pendingName = null;
        pendingUserType = null;
        pendingGender = null;
        clearDailyLogFields(false);
        if (userInfoLabel != null) {
            userInfoLabel.setText("No user selected");
        }
        nameField.setText("");
        dateField.setText(LocalDate.now().format(dateFormatter));
        if (genderCombo != null) {
            genderCombo.setSelectedIndex(0);
        }
        cardLayout.show(cardPanel, CARD_REGISTRATION);
    }

    private String getBurnoutLevel(double intenseHours) {
        if (intenseHours <= 0) {
            return "Low - No intense work/study logged";
        } else if (intenseHours < 4) {
            return "Low - Balanced workload";
        } else if (intenseHours <= 6) {
            return "Moderate - Monitor stress levels";
        } else {
            return "High - Schedule recovery time";
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            new HealthTrackerGUI().setVisible(true);
        });
    }
}

