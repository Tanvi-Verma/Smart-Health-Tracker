
public abstract class User {
    
    protected String name;
    protected int age;
    protected String gender;
    protected double height; 
    protected double weight; 
    
    public User(String name, int age, String gender, double height, double weight) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
    }
    
    // Getters
    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }
    
    public String getGender() {
        return gender;
    }
    
    public double getHeight() {
        return height;
    }
    
    public double getWeight() {
        return weight;
    }
    
    // Setters
    public void setName(String name) {
        this.name = name;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public void setHeight(double height) {
        this.height = height;
    }
    
    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    public double calculateBMI() {
        if (height <= 0 || weight <= 0) {
            throw new IllegalArgumentException("Height and weight must be positive values");
        }
        double heightInMeters = height / 100.0;
        return weight / (heightInMeters * heightInMeters);
    }
    
    public String getBMICategory() {
        double bmi = calculateBMI();
        
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 25.0) {
            return "Healthy weight";
        } else if (bmi < 30.0) {
            return "Overweight";
        } else if (bmi < 35.0) {
            return "Obese Class I";
        } else if (bmi < 40.0) {
            return "Obese Class II";
        } else {
            return "Obese Class III";
        }
    }
    
    @Override
    public String toString() {
        return "Name: " + name + ", Age: " + age + ", Gender: " + gender + 
               ", Height: " + height + " cm, Weight: " + weight + " kg";
    }
}