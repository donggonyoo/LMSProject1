package model.dto.learning_support;

public class SearchDto {
	private String deptId;
    private String courseId;
    private String courseName;
    
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	@Override
	public String toString() {
		return "SearchDto [" + "deptId=" + deptId + ", courseId=" + courseId + ", courseName="
				+ courseName + "]";
	}
	    
    
}