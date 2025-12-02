# Smart Health Tracker - Java GUI Application 

A comprehensive GUI-based health tracking application built with Java Swing that allows users to register, log daily health metrics, and receive personalized health analysis and suggestions. 


## Features 

### 1. User Registration 

- Register new users with: 

  - Name 

  - Age 

  - Gender (Male/Female/Other) 

  - Height (in cm) 

  - Weight (in kg) 

- Automatic user detection: If a user with the same name exists, they are automatically redirected to the daily log page 

- User classification: Automatically creates AdultUser (18+) or ChildUser (<18) based on age 

 

### 2. Daily Health Logging 

- **Steps**: Track daily step count 

- **Sleep**: Log sleep hours 

- **Water Intake**: Record daily water consumption in liters 

- **Mood**: Write about your feelings (e.g., happy, anxious, grateful, stressed) 

 

### 3. Health Analysis & Calculations 

- **BMI Calculation**: Automatically calculates Body Mass Index 

- **BMI Categories**: 

  - Underweight (below 18.5) 

  - Healthy weight (18.5–24.9) 

  - Overweight (25.0–29.9) 

  - Obese Class I (30.0–34.9) 

  - Obese Class II (35.0–39.9) 

  - Obese Class III (40.0 and above) 

- **Calories Burnt**: Calculated based on steps (0.04 calories per step) 

- **Sleep Quality**: Analyzes sleep duration and provides quality assessment 

- **Happiness Level**: Analyzes mood and provides happiness assessment 

 

### 4. Personalized Suggestions 

- Step count recommendations 

- Sleep duration advice 

- Hydration tips 

- Mood-based wellness suggestions 

 

## Project Structure 

### Object-Oriented Design 

- **Base Class**: `User` - Abstract base class with common user attributes 

- **Subclasses**:  

  - `AdultUser` - For users 18 years and above 

  - `ChildUser` - For users below 18 years 

- **Interface**: `HealthCalculator` - Defines methods for health calculations 

- **Implementation**: `HealthMetrics` - Implements HealthCalculator interface 

 

### File Handling 

- **CSVHandler**: Manages all CSV file operations 

- **users.csv**: Stores user registration data 

- **health_metrics.csv**: Stores daily health logs 

 

### Exception Handling 

- Custom exception: `UserNotFoundException` 

- Input validation for all fields 

- Number format validation 

- Range validation for age, height, weight, etc. 

## How to Run 

1. **Compile all Java files**: 

   ```bash 

   javac *.java 

   ``` 

 

2. **Run the application**: 

   ```bash 

   java HealthTrackerGUI 

   ``` 

 

## Usage Instructions 

1. **Registration**: 

   - Enter your name, age, gender, height, and weight 

   - Click "Register / Continue" 

   - If you're a returning user (same name), you'll be redirected to the daily log 

 

2. **Daily Logging**: 

   - Enter your daily steps 

   - Enter sleep hours 

   - Enter water intake in liters 

   - Write about your mood 

   - Click "Submit Daily Log" 

   - View your health analysis and suggestions in the results area 

 

3. **Viewing Results**: 

   - After submitting, the results area displays: 

     - User information 

     - BMI analysis 

     - Daily metrics 

     - Health calculations 

     - Personalized suggestions 

 

## Technical Details 

### Technologies Used 

- Java Swing for GUI 

- CSV file handling for data persistence 

- Object-oriented programming principles 

- Exception handling 

- Interface implementation 

 

### Data Storage 

- All data is stored in CSV format 

- Users can access their data across sessions 

- Historical health metrics are preserved 

 

## File Structure 

``` 

demo/ 

├── User.java                 # Base abstract class 

├── AdultUser.java            # Adult user subclass 

├── ChildUser.java            # Child user subclass 

├── HealthCalculator.java     # Interface for health calculations 

├── HealthMetrics.java        # Health metrics class implementing interface 

├── CSVHandler.java           # CSV file operations handler 

├── HealthTrackerGUI.java     # Main GUI application 

├── users.csv                 # User data (auto-generated) 

├── health_metrics.csv        # Health metrics data (auto-generated) 

└── README.md                 # This file 

``` 

## Notes 

- The application automatically creates CSV files on first use 

- All data is persisted between sessions 

- BMI calculations use standard formula: weight (kg) / (height (m)) ² 

- Child BMI categories include a note about age-specific interpretation
