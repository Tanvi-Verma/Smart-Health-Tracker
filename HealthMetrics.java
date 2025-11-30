import java.time.LocalDate;

public class HealthMetrics implements HealthCalculator {

    private String userName;
    private LocalDate date;
    private int steps;
    private double sleepHours;
    private double waterIntake; 
    private String mood;
    private double screenTime; 
    private double Work_StudyHours; 
     
    
    public HealthMetrics(String userName, LocalDate date, int steps, 
                        double sleepHours, double waterIntake, String mood,
                        double screenTime, double Work_StudyHours) {
        this.userName = userName;
        this.date = date;
        this.steps = steps;
        this.sleepHours = sleepHours;
        this.waterIntake = waterIntake;
        this.mood = mood;
        this.screenTime = screenTime;
        this.Work_StudyHours = Work_StudyHours;
    }
    public String getUserName() {
        return userName;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public int getSteps() {
        return steps;
    }
    
    public double getSleepHours() {
        return sleepHours;
    }
    
    public double getWaterIntake() {
        return waterIntake;
    }
    
    public String getMood() {
        return mood;
    }
    
    public double getScreenTime() {
        return screenTime;
    }
    
    public double getWork_StudyHours() {
        return Work_StudyHours;
    }

    @Override
    public double CaloriesBurnt(int steps) {
        return steps * 0.04;
    }
    @Override
    public String CalculateSleepQuality(double SleepHours) {
        if (SleepHours >= 7 && SleepHours <= 9) {
            return "Excellent - Optimal sleep duration";
        } else if (SleepHours >= 6 && SleepHours < 7) {
            return "Good - Slightly below optimal";
        } else if (SleepHours > 9 && SleepHours <= 10) {
            return "Good - Slightly above optimal";
        } else if (SleepHours >= 5 && SleepHours < 6) {
            return "Fair - Below recommended";
        } else if (SleepHours > 10) {
            return "Fair - Above recommended";
        } else {
            return "Poor - Insufficient sleep";
        }
    }
    @Override
    public String CalculateHappinessLevel(String mood){
        String moodLower = mood.toLowerCase();
        switch (moodLower) {
            case "happy":
            case "joyful":
            case "excited":
                return "High Happiness Level";
            case "content":
            case "satisfied":
                return "Moderate Happiness Level";
            case "sad":
            case "anxious":
            case "stressed":
                return "Low Happiness Level";
            default:
                return "Neutral Happiness Level";
        }
    }

    public String getHealthSuggestions() {
        StringBuilder suggestions = new StringBuilder();

        // STEPS SUGGESTION
        if(steps < 5000){
            suggestions.append("Aim to increase your daily steps to at least 5000 for better health.\n");
        }
        else if(steps < 10000){
            suggestions.append("Great job! Try to reach 10,000 steps for optimal health benefits.\n");
        }
        else{
            suggestions.append("Excellent! You are exceeding the recommended daily steps.\n");
        }

        // SLEEP SUGGESTION
        if(sleepHours < 7){
            suggestions.append("Try to get at least 7 hours of sleep for better rest and recovery.\n");
        }
        else if(sleepHours <= 9){
            suggestions.append("You are getting optimal sleep. Keep it up!\n");
        }
        else{
            suggestions.append("Consider reducing your sleep duration to avoid oversleeping.\n");
        }

        // WATER INTAKE SUGGESTION
        if(waterIntake < 2.0){
            suggestions.append("Increase your water intake to at least 2 liters per day.\n");
        }
        else if(waterIntake > 4.0){
            suggestions.append("Be cautious of excessive water intake which can lead to overhydration.\n");
        }
        else{
            suggestions.append("Good job on staying hydrated!\n");
        }

        // MOOD SUGGESTION
        String HappinessLevel = CalculateHappinessLevel(mood);
        if(HappinessLevel.contains("Low")){
            suggestions.append("Consider engaging in activities that boost your mood, such as exercise or hobbies.\n");
        }
        else if(HappinessLevel.contains("Moderate")){
            suggestions.append("Maintain your current activities that contribute to your contentment.\n");
        }
        else if(HappinessLevel.contains("High")){
            suggestions.append("Keep up the positive mindset and continue doing what makes you happy!\n");
        }
        else{
            suggestions.append("Try to identify factors affecting your mood and address them accordingly.\n");
        }

        // SCREEN TIME SUGGESTION
        if(screenTime > 6.0){
            suggestions.append("Reduce screen time to less than 6 hours a day to prevent eye strain and improve sleep quality.\n");
        }
        else if(screenTime > 4.0){
            suggestions.append("Consider limiting screen time to enhance productivity and well-being.\n");
        }
        else{
            suggestions.append("Good job on managing your screen time effectively!\n");
        }

        // BurnOut SUGGESTION
        if(Work_StudyHours > 10.0){
            suggestions.append("Be cautious of burnout. Ensure to take breaks and manage your workload effectively.\n");
        }
        else if(Work_StudyHours > 6.0){
            suggestions.append("Maintain a balanced work-study schedule to avoid stress.\n");
        }
        else{
            suggestions.append("Great job on balancing work and study hours!\n");
        }
        
        return suggestions.toString();
    }
    
}
