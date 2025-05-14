package model.dto.professor_support;

public class RegistCourseDto {
    private String majorName;        // 전공명 (deptId)
    private String courseName;       // 강의명
    private String professorName;    // 교수명
    private String creditCategory;       // 이수구분
    private String courseDay;        // 강의 요일
    private Integer startTimeHour;   // 시작 시간
    private Integer endTimeHour;     // 종료 시간
    private Integer score;           // 학점
    private String description;      // 강의 설명

    // 기본 생성자
    public RegistCourseDto() {
    }

    // 모든 필드를 포함한 생성자
    public RegistCourseDto(String majorName, String courseName, String professorName, String creditCategory,
                           String courseDay, Integer startTimeHour, Integer endTimeHour, Integer score,
                           String description) {
        this.majorName = majorName;
        this.courseName = courseName;
        this.professorName = professorName;
        this.creditCategory = creditCategory;
        this.courseDay = courseDay;
        this.startTimeHour = startTimeHour;
        this.endTimeHour = endTimeHour;
        this.score = score;
        this.description = description;
    }

    // Getter 및 Setter
    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public String getCreditCategory() {
        return creditCategory;
    }

    public void setCreditCategory(String creditCategory) {
        this.creditCategory = creditCategory;
    }

    public String getCourseDay() {
        return courseDay;
    }

    public void setCourseDay(String courseDay) {
        this.courseDay = courseDay;
    }

    public Integer getStartTimeHour() {
        return startTimeHour;
    }

    public void setStartTimeHour(Integer startTimeHour) {
        this.startTimeHour = startTimeHour;
    }

    public Integer getEndTimeHour() {
        return endTimeHour;
    }

    public void setEndTimeHour(Integer endTimeHour) {
        this.endTimeHour = endTimeHour;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // timeSlot 생성 메서드
    public String getTimeSlot() {
        if (courseDay == null || startTimeHour == null || endTimeHour == null) {
            return null;
        }
        return courseDay + "/ " + startTimeHour + " - " + endTimeHour;
    }

    // toString (디버깅 용)
    @Override
    public String toString() {
        return "RegistCourseDto{" +
                "majorName='" + majorName + '\'' +
                ", courseName='" + courseName + '\'' +
                ", professorName='" + professorName + '\'' +
                ", creditCategory='" + creditCategory + '\'' +
                ", courseDay='" + courseDay + '\'' +
                ", startTimeHour=" + startTimeHour +
                ", endTimeHour=" + endTimeHour +
                ", score=" + score +
                ", description='" + description + '\'' +
                ", timeSlot='" + getTimeSlot() + '\'' +
                '}';
    }
}