
public class AdultUser extends User {
    
    private String healthIssues;
    
    public AdultUser(String name, int age, String gender, double height, double weight, String healthIssues) {
        super(name, age, gender, height, weight);
        if (age < 18) {
            throw new IllegalArgumentException("AdultUser must be 18 years or older");
        }
        this.healthIssues = (healthIssues == null || healthIssues.isEmpty()) ? "None" : healthIssues;
    }
    
    @Override
    public String toString() {
        return super.toString() + ", Health Issues: " + healthIssues;
    }
    
    public String getHealthIssues() {
        return healthIssues;
    }
    
    public void setHealthIssues(String healthIssues) {
        this.healthIssues = (healthIssues == null || healthIssues.isEmpty()) ? "None" : healthIssues;
    }
}
