package domain;

import java.util.Date;

public class Professor {
	private String professor_Id;
    private String professor_name;
    private String professor_email;
    private Date  professor_birthday;
    private String professor_phone;
    private String professor_major;
    private String professor_img;
    private String professor_password;
    
    
	public String getProfessor_Id() {
		return professor_Id;
	}
	public void setProfessor_Id(String professor_Id) {
		this.professor_Id = professor_Id;
	}
	public String getProfessor_name() {
		return professor_name;
	}
	public void setProfessor_name(String professor_name) {
		this.professor_name = professor_name;
	}
	public String getProfessor_email() {
		return professor_email;
	}
	public void setProfessor_email(String professor_email) {
		this.professor_email = professor_email;
	}
	public Date getProfessor_birthday() {
		return professor_birthday;
	}
	public void setProfessor_birthday(Date professor_birthday) {
		this.professor_birthday = professor_birthday;
	}
	public String getProfessor_phone() {
		return professor_phone;
	}
	public void setProfessor_phone(String professor_phone) {
		this.professor_phone = professor_phone;
	}
	public String getProfessor_major() {
		return professor_major;
	}
	public void setProfessor_major(String professor_major) {
		this.professor_major = professor_major;
	}
	public String getProfessor_img() {
		return professor_img;
	}
	public void setProfessor_img(String professor_img) {
		this.professor_img = professor_img;
	}
	public String getProfessor_password() {
		return professor_password;
	}
	public void setProfessor_password(String professor_password) {
		this.professor_password = professor_password;
	}
    

}
