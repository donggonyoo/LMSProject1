package model.dto.professor_support;

public class AttendanceDataDto {
    private String attendanceId;
    private String studentId;
    private String courseId;
    private String attendanceStatus;
    private String attendanceDate;
    private int attendanceAbsent;
    private int attendanceLate;

    // getter와 setter
    public String getAttendanceId() { return attendanceId; }
    public void setAttendanceId(String attendanceId) { this.attendanceId = attendanceId; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
    public String getAttendanceStatus() { return attendanceStatus; }
    public void setAttendanceStatus(String attendanceStatus) { this.attendanceStatus = attendanceStatus; }
    public String getAttendanceDate() { return attendanceDate; }
    public void setAttendanceDate(String attendanceDate) { this.attendanceDate = attendanceDate; }
    public int getAttendanceAbsent() { return attendanceAbsent; }
    public void setAttendanceAbsent(int attendanceAbsent) { this.attendanceAbsent = attendanceAbsent; }
    public int getAttendanceLate() { return attendanceLate; }
    public void setAttendanceLate(int attendanceLate) { this.attendanceLate = attendanceLate; }
}
