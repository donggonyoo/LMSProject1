package controller.professor_support;

import java.util.List;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import model.dao.learning_support.CourseDao;
import model.dto.learning_support.DeptDto;

@WebServlet(urlPatterns = {"/professor_support/*"}, 
			initParams = {@WebInitParam(name="view",value = "/dist/")}
)
public class CourseByProController extends MskimRequestMapping {
	
	private CourseDao courseDao = new CourseDao();
	
	@RequestMapping("registCourse")
	public String registerCourse (HttpServletRequest request, HttpServletResponse response) {
		
		List<DeptDto> departments = courseDao.getDepartments("");
		System.out.println(departments.toString());
		
		request.setAttribute("departments", departments);
		
		return "/pages/professor_support/registCourseByPro";
	}
	
	
	
}
