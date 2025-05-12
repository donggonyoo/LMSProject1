package controller.learning_support;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import model.dao.learning_support.CourseDao;
import model.dto.learning_support.CourseDto;
import model.dto.learning_support.SearchDto;

@WebServlet(urlPatterns = {"/learning_support/*"}, 
			initParams = {@WebInitParam(name="view",value = "/dist/")}
)
public class CourseController extends MskimRequestMapping {
	
	private CourseDao courseDao = new CourseDao();
	
	@RequestMapping("registerCourse")
	public String registerCourse (HttpServletRequest request, HttpServletResponse response) {
		
		return "/pages/learning_support/registerCourse";
	}
	
	@RequestMapping("colleges")
	public String getColleges (HttpServletRequest request, HttpServletResponse response) {
		
		List<String> colleges = courseDao.getColleges();
		
        ObjectMapper mapper = new ObjectMapper();
        String json;
        
		try {
			json = mapper.writeValueAsString(colleges);
			request.setAttribute("json", json);
			
		} catch (IOException e) {
			e.printStackTrace();

		}
		return "/pages/learning_support/ajax_learning_support";
	}
	
	@RequestMapping("departments")
	public String getDepartments (HttpServletRequest request, HttpServletResponse response) {
		
		String college = request.getParameter("college");
		List<Map<String, String>> departments = courseDao.getDepartments(college);
		
        ObjectMapper mapper = new ObjectMapper();
        String json;
        
		try {
			json = mapper.writeValueAsString(departments);
			request.setAttribute("json", json);
		} catch (IOException e) {
			e.printStackTrace();

		}
		return "/pages/learning_support/ajax_learning_support";
	}
	
	@RequestMapping("searchCourse")
	public String searchCourse (HttpServletRequest request, HttpServletResponse response) {
		
		SearchDto searchDto = new SearchDto();
		searchDto.setDeptId(request.getParameter("deptId"));
		searchDto.setCourseId(request.getParameter("courseId"));
		searchDto.setCourseName(request.getParameter("courseName"));
		
		List<CourseDto> result = courseDao.searchCourse(searchDto);
        request.setAttribute("courses", result);
		
        return "/pages/learning_support/registerCourse";
	}
	
}
