package model.dto.learning_support;

public class RegistrationDto {
    private String registrationId;
    private String creditCategory;
    private String courseId;
    private String courseName;
    private Integer courseScore;
    private String professorName;
    private String timeSlot;

    // Getters and Setters
    
    public String getregistrationId() { return registrationId; }
    public void setregistrationId(String registrationId) { this.registrationId = registrationId; }
    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public String getCreditCategory() { return creditCategory; }
    public void setCreditCategory(String creditCategory) { this.creditCategory = creditCategory; }
    public Integer getCourseScore() { return courseScore; }
    public void setCourseScore(Integer courseScore) { this.courseScore = courseScore; }
    public String getProfessorName() { return professorName; }
    public void setProfessorName(String professorName) { this.professorName = professorName; }
    public String getTimeSlot() { return timeSlot; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }
}