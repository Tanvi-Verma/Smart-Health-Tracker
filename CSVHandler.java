import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CSVHandler {
    private static final String USERS_FILE = "users.csv";
    private static final String METRICS_FILE = "health_metrics.csv";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    
     // Save user to CSV file
     
    public static void saveUser(User user) throws IOException {
        File file = new File(USERS_FILE);
        boolean fileExists = file.exists();
        
        try (FileWriter fw = new FileWriter(file, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {
            
            // Write header if file is new
            if (!fileExists) {
                pw.println("Name,Age,Gender,Height,Weight,UserType");
            }
            
            String userType = (user instanceof AdultUser) ? "Adult" : "Child";
            pw.println(String.format("%s,%d,%s,%.2f,%.2f,%s",
                    user.getName(), user.getAge(), user.getGender(),
                    user.getHeight(), user.getWeight(), userType));
        }
    }
    
    
     // Load user by name from CSV file
     
    public static User loadUser(String name) throws IOException, UserNotFoundException {
        File file = new File(USERS_FILE);
        if (!file.exists()) {
            throw new UserNotFoundException("User file not found");
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;
            
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }
                
                String[] parts = line.split(",");
                if (parts.length >= 6 && parts[0].equalsIgnoreCase(name)) {
                    String userName = parts[0];
                    int age = Integer.parseInt(parts[1]);
                    String gender = parts[2];
                    double height = Double.parseDouble(parts[3]);
                    double weight = Double.parseDouble(parts[4]);
                    String userType = parts[5];
                    
                    if (userType.equals("Adult")) {
                        return new AdultUser(userName, age, gender, height, weight, null);
                    } else {
                        return new ChildUser(userName, age, gender, height, weight, null);
                    }
                }
            }
        }
        
        throw new UserNotFoundException("User '" + name + "' not found");
    }
    
    
    //Check if user exists
     
    public static boolean userExists(String name) {
        try {
            loadUser(name);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    
    //Save health metrics to CSV file
     
    public static void saveHealthMetrics(HealthMetrics metrics) throws IOException {
        File file = new File(METRICS_FILE);
        boolean fileExists = file.exists();
        
        try (FileWriter fw = new FileWriter(file, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {
            
            // Write header if file is new
            if (!fileExists) {
                pw.println("UserName,Date,Steps,SleepHours,WaterIntake,Mood,ScreenTime,WorkStudyHours");
            }
            pw.println(String.format("%s,%s,%d,%.2f,%.2f,%s,%.2f,%.2f",
                    metrics.getUserName(),
                    metrics.getDate().format(DATE_FORMATTER),
                    metrics.getSteps(),
                    metrics.getSleepHours(),
                    metrics.getWaterIntake(),
                    metrics.getMood(),
                    metrics.getScreenTime(),
                    metrics.getWork_StudyHours()));
        }
    }
    
    
    //Get all health metrics for a user
     
    public static List<HealthMetrics> getUserMetrics(String userName) throws IOException {
        List<HealthMetrics> metricsList = new ArrayList<>();
        File file = new File(METRICS_FILE);
        
        if (!file.exists()) {
            return metricsList;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;
            
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }
                
                String[] parts = line.split(",");
                if (parts.length >= 6 && parts[0].equalsIgnoreCase(userName)) {
                    LocalDate date = LocalDate.parse(parts[1], DATE_FORMATTER);
                    int steps = Integer.parseInt(parts[2]);
                    double sleepHours = Double.parseDouble(parts[3]);
                    double waterIntake = Double.parseDouble(parts[4]);
                    String mood = parts[5];
                    double screenTime = parts.length > 6 && !parts[6].isEmpty() ? Double.parseDouble(parts[6]) : 0.0;
                    double workStudyHours = 0.0;
                    if (parts.length > 8) {
                        double workHours = !parts[7].isEmpty() ? Double.parseDouble(parts[7]) : 0.0;
                        double studyHours = !parts[8].isEmpty() ? Double.parseDouble(parts[8]) : 0.0;
                        workStudyHours = workHours > 0 ? workHours : studyHours;
                    } else if (parts.length > 7 && !parts[7].isEmpty()) {
                        workStudyHours = Double.parseDouble(parts[7]);
                    }
                    
                    metricsList.add(new HealthMetrics(userName, date, steps, sleepHours, waterIntake, mood,
                            screenTime, workStudyHours));
                }
            }
        }
        
        return metricsList;
    }
}

class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
}

