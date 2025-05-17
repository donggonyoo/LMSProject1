package controller.professor_support;

import java.util.List;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import model.dao.learning_support.CourseDao;
import model.dao.professor_support.CourseByProDao;
import model.dto.learning_support.DeptDto;
import model.dto.professor_support.RegistCourseDto;

@WebServlet(urlPatterns = {"/professor_support/score/*"}, 
			initParams = {@WebInitParam(name="view",value = "/dist/")}
)
public class ScoreMngController extends MskimRequestMapping {
	
	private CourseDao courseDao = new CourseDao();
	private CourseByProDao byProDao = new CourseByProDao();
	
	@RequestMapping("scoreMng")
	public String score (HttpServletRequest request, HttpServletResponse response) {
		
		return "pages/professor_support/manageScore";
	}
	
	
	
}
