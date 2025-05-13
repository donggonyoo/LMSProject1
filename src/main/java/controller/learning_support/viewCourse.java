package controller.learning_support;

import java.util.List;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import model.dao.learning_support.CourseDao;
import model.dto.learning_support.RegistrationDto;

@WebServlet(urlPatterns = {"/learning_support/viewCourse/*"}, 
initParams = {@WebInitParam(name="view",value = "/dist/")}
)
public class viewCourse extends MskimRequestMapping{
	private CourseDao courseDao = new CourseDao();
	
	@RequestMapping("viewCourse")
	public String registerCourse (HttpServletRequest request, HttpServletResponse response) {
//		String studentId = (String) request.getSession().getAttribute("login");
//		테스트위한 임시 studentId 지정
		String studentId = "S001";

		//화면 로드시 수강신청내역 불러오기
		List<RegistrationDto> result = courseDao.searchRegistrationCourses(studentId);
		request.setAttribute("registration", result);
		
		return "/pages/learning_support/viewCourse";
	}
	
	@RequestMapping("deleteCourse")
	public String deleteCourse (HttpServletRequest request, HttpServletResponse response) {

		String registrationId = request.getParameter("registrationId");
		courseDao.deleteCourse(registrationId);
		
		return "/pages/dummy";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
