package controller.professor_support;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import model.dao.learning_support.CourseDao;
import model.dao.professor_support.CourseByProDao;
import model.dao.professor_support.ScoreMngDao;
import model.dto.learning_support.DeptDto;
import model.dto.professor_support.RegistCourseDto;
import model.dto.professor_support.ScoreMngDto;

@WebServlet(urlPatterns = {"/professor_support/score/*"}, 
			initParams = {@WebInitParam(name="view",value = "/dist/")}
)
public class ScoreMngController extends MskimRequestMapping {
	
	private CourseDao courseDao = new CourseDao();
	private CourseByProDao byProDao = new CourseByProDao();
	private CourseByProDao mDao = new CourseByProDao();
	private ScoreMngDao scoreDao = new ScoreMngDao();
	
	@RequestMapping("scoreMng")
	public String score (HttpServletRequest request, HttpServletResponse response) {
		
		return "pages/professor_support/manageScore";
	}
	
	@RequestMapping("getCoursesInfo")
	public String getCoursesInfo (HttpServletRequest request, HttpServletResponse response) {
		// 작업 완료시 주석풀고 교체
		//String professorId = (String) request.getSession().getAttribute("login");
		String professorId = "P001";
		ObjectMapper mapper = new ObjectMapper();
        String json = "";
        
		List<Map<String, Object>> result =  scoreDao.getCoursesInfo(professorId);
		
		try {
			json = mapper.writeValueAsString(result);
			request.setAttribute("json", json);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "pages/returnAjax";
	}
	
	@RequestMapping("getScoreInfo")
	public String getScore (HttpServletRequest request, HttpServletResponse response) {
		// 작업 완료시 주석풀고 교체
		//String professorId = (String) request.getSession().getAttribute("login");
		String professorId = "P001";
		ObjectMapper mapper = new ObjectMapper();
        String json = "";
        
		List<Map<String, Object>> result =  scoreDao.getScoreInfo(professorId);
		
		
		
		
		try {
			json = mapper.writeValueAsString(result);
			request.setAttribute("json", json);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "pages/returnAjax";
	}
	
}
