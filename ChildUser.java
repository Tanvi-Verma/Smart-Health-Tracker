
public class ChildUser extends User {

    private String vaccinationStatus;

    public ChildUser(String name, int age, String gender, double height, double weight, String vaccinationStatus) {
        super(name, age, gender, height, weight);
        if (age >= 18) {
            throw new IllegalArgumentException("ChildUser must be under 18 years old");
        }
        this.vaccinationStatus = (vaccinationStatus == null || vaccinationStatus.isEmpty()) ? "Unknown" : vaccinationStatus;
    }
    
    @Override
    public String toString() {
        return super.toString() + ", Vaccination Status: " + vaccinationStatus;
    }
    
    public String getVaccinationStatus() {
        return vaccinationStatus;
    }
    
    public void setVaccinationStatus(String vaccinationStatus) {
        this.vaccinationStatus = (vaccinationStatus == null || vaccinationStatus.isEmpty()) ? "Unknown" : vaccinationStatus;
    }
}