package controller.professor_support;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import model.dao.professor_support.ManageAttendanceDao;
import model.dao.professor_support.ScoreMngDao;
import model.dto.learning_support.DeptDto;
import model.dto.professor_support.RegistCourseDto;
import model.dto.professor_support.ScoreMngDto;

@WebServlet(urlPatterns = {"/professor_support/attendance/*"}, 
			initParams = {@WebInitParam(name="view",value = "/dist/")}
)
public class ManageAttendanceController extends MskimRequestMapping {
	
	private ManageAttendanceDao attDao = new ManageAttendanceDao();
	
	ObjectMapper mapper = new ObjectMapper();
	
	@RequestMapping("attendance")
	public String score (HttpServletRequest request, HttpServletResponse response) {
		// 작업 완료시 주석풀고 교체
		//String professorId = (String) request.getSession().getAttribute("login");
		String professorId = "P001";
		
		List<Map<String, Object>> result =  attDao.getCoursesInfoByPro(professorId);
		request.setAttribute("courses", result);
		
		return "pages/professor_support/manageAttendance";
	}
	
}
