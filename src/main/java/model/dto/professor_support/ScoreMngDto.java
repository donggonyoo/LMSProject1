package model.dto.professor_support;

public class ScoreMngDto {
	
	private String courseName;
	private String courseId;
	private Integer courseCurrentEnrollment;
	
	private String studentName;
	private String studentId;
	private String deptName;
	
	private Integer scoreMid;
	private Integer scoreFinal;
	private String scoreEtc;
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public Integer getCourseCurrentEnrollment() {
		return courseCurrentEnrollment;
	}
	public void setCourseCurrentEnrollment(Integer courseCurrentEnrollment) {
		this.courseCurrentEnrollment = courseCurrentEnrollment;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Integer getScoreMid() {
		return scoreMid;
	}
	public void setScoreMid(Integer scoreMid) {
		this.scoreMid = scoreMid;
	}
	public Integer getScoreFinal() {
		return scoreFinal;
	}
	public void setScoreFinal(Integer scoreFinal) {
		this.scoreFinal = scoreFinal;
	}
	public String getScoreEtc() {
		return scoreEtc;
	}
	public void setScoreEtc(String scoreEtc) {
		this.scoreEtc = scoreEtc;
	}
	
	@Override
	public String toString() {
		return "ScoreMngDto [courseName=" + courseName + ", courseId=" + courseId + ", courseCurrentEnrollment="
				+ courseCurrentEnrollment + ", studentName=" + studentName + ", studentId=" + studentId + ", deptName="
				+ deptName + ", scoreMid=" + scoreMid + ", scoreFinal=" + scoreFinal + ", scoreEtc=" + scoreEtc + "]";
	}
	
	

}
