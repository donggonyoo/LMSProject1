package model.dto.professor_support;

public class RegistCourseDto {
    private String courseId;         // 강의 ID
    private String deptId;           // 학과 ID
    private String professorId;      // 교수 ID
    private String courseName;       // 강의명
    private String courseStatus;     // 강의 상태
    private Integer courseMaxCnt;    // 최대 수강 인원
    private Integer courseScore;     // 학점
    private String creditCategory;   // 이수구분
    private String coursePlan;       // 강의 계획

    private String courseTimeId;     // 강의 시간 ID
    private String courseTimeYoil;   // 요일
    private String courseTimeLoc;    // 강의 장소
    private String courseTimeStart;  // 시작 시간 (HH:00 형식)
    private String courseTimeEnd;    // 종료 시간 (HH:50 형식)
    
    // Getter 및 Setter
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getProfessorId() {
        return professorId;
    }

    public void setProfessorId(String professorId) {
        this.professorId = professorId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public Integer getCourseMaxCnt() {
        return courseMaxCnt;
    }

    public void setCourseMaxCnt(Integer string) {
        this.courseMaxCnt = string;
    }

    public Integer getCourseScore() {
        return courseScore;
    }

    public void setCourseScore(Integer courseScore) {
        this.courseScore = courseScore;
    }

    public String getCreditCategory() {
        return creditCategory;
    }

    public void setCreditCategory(String creditCategory) {
        this.creditCategory = creditCategory;
    }

    public String getCoursePlan() {
        return coursePlan;
    }

    public void setCoursePlan(String coursePlan) {
        this.coursePlan = coursePlan;
    }

    public String getCourseTimeId() {
        return courseTimeId;
    }

    public void setCourseTimeId(String courseTimeId) {
        this.courseTimeId = courseTimeId;
    }

    public String getCourseTimeYoil() {
        return courseTimeYoil;
    }

    public void setCourseTimeYoil(String courseTimeYoil) {
        this.courseTimeYoil = courseTimeYoil;
    }

    public String getCourseTimeLoc() {
        return courseTimeLoc;
    }

    public void setCourseTimeLoc(String courseTimeLoc) {
        this.courseTimeLoc = courseTimeLoc;
    }

    public String getCourseTimeStart() {
        return courseTimeStart;
    }

    public void setCourseTimeStart(String courseTimeStart) {
        this.courseTimeStart = courseTimeStart;
    }

    public String getCourseTimeEnd() {
        return courseTimeEnd;
    }

    public void setCourseTimeEnd(String courseTimeEnd) {
        this.courseTimeEnd = courseTimeEnd;
    }

    // toString (디버깅 용)
    @Override
    public String toString() {
        return "RegistCourseDto{" +
                "courseId='" + courseId + '\'' +
                ", deptId='" + deptId + '\'' +
                ", professorId='" + professorId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", courseStatus='" + courseStatus + '\'' +
                ", courseMaxCnt=" + courseMaxCnt +
                ", courseScore=" + courseScore +
                ", creditCategory='" + creditCategory + '\'' +
                ", coursePlan='" + coursePlan + '\'' +
                ", courseTimeId='" + courseTimeId + '\'' +
                ", courseTimeYoil='" + courseTimeYoil + '\'' +
                ", courseTimeLoc='" + courseTimeLoc + '\'' +
                ", courseTimeStart='" + courseTimeStart + '\'' +
                ", courseTimeEnd='" + courseTimeEnd + '\'' +
                '}';
    }
}