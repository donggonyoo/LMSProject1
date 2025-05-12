package domain;

import java.util.Date;

public class Student {
	private String student_id;
    private String student_name;
    private String student_num;
    private String dept_id;
    private String student_email;
    private String student_password;
    private String student_status;
    private Date student_birthday;
    private String student_phone;
    private String student_img;
	public String getStudent_id() {
		return student_id;
	}
	public void setStudent_id(String student_id) {
		this.student_id = student_id;
	}
	public String getStudent_name() {
		return student_name;
	}
	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}
	public String getStudent_num() {
		return student_num;
	}
	public void setStudent_num(String student_num) {
		this.student_num = student_num;
	}
	public String getDept_id() {
		return dept_id;
	}
	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	public String getStudent_email() {
		return student_email;
	}
	public void setStudent_email(String student_email) {
		this.student_email = student_email;
	}
	public String getStudent_password() {
		return student_password;
	}
	public void setStudent_password(String student_password) {
		this.student_password = student_password;
	}
	public String getStudent_status() {
		return student_status;
	}
	public void setStudent_status(String student_status) {
		this.student_status = student_status;
	}
	public Date getStudent_birthday() {
		return student_birthday;
	}
	public void setStudent_birthday(Date student_birthday) {
		this.student_birthday = student_birthday;
	}
	public String getStudent_phone() {
		return student_phone;
	}
	public void setStudent_phone(String student_phone) {
		this.student_phone = student_phone;
	}
	public String getStudent_img() {
		return student_img;
	}
	public void setStudent_img(String student_img) {
		this.student_img = student_img;
	}
	@Override
	public String toString() {
		return "Student [student_id=" + student_id + ", student_name=" + student_name + ", student_num=" + student_num
				+ ", dept_id=" + dept_id + ", student_email=" + student_email + ", student_password=" + student_password
				+ ", student_status=" + student_status + ", student_birthday=" + student_birthday + ", student_phone="
				+ student_phone + ", student_img=" + student_img + "]";
	}
    
	
	
	

    

}
