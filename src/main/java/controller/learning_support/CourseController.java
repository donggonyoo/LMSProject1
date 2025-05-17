package controller.learning_support;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import model.dao.learning_support.CourseDao;
import model.dto.learning_support.CourseDto;
import model.dto.learning_support.DeptDto;
import model.dto.learning_support.RegistrationDto;
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
		System.out.println("college: " + college);
		List<DeptDto> departments = courseDao.getDepartments(college);
		
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
		
		String studentId = (String) request.getSession().getAttribute("login");
		
		SearchDto searchDto = new SearchDto();
		searchDto.setCollege(request.getParameter("college"));
		searchDto.setDeptId(request.getParameter("deptId"));
		searchDto.setCourseId(request.getParameter("courseId"));
		searchDto.setCourseName(request.getParameter("courseName"));
		
		List<RegistrationDto> registrationCourses = courseDao.searchRegistrationCourses(studentId);
		List<CourseDto> result = courseDao.searchCourse(searchDto);
		
		Iterator<CourseDto> iter = result.iterator();
		while(iter.hasNext()) {
			CourseDto c = iter.next();
			for (RegistrationDto r : registrationCourses) {
				if (c.getCourseId().equals(r.getCourseId())) {
					iter.remove();
				}
			}
		}
		
		ObjectMapper mapper = new ObjectMapper();
        String json;
        
		try {
			json = mapper.writeValueAsString(result);
			request.setAttribute("json", json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        return "/pages/learning_support/ajax_learning_support";
	}
	
	@RequestMapping("addCourse")
	public String addCourse (HttpServletRequest request, HttpServletResponse response) {
		
		ObjectMapper mapper = new ObjectMapper();
        String json;
	
        String studentId = (String) request.getSession().getAttribute("login");
		
		Map<String, Object> map = new HashMap<>();
		map.put("studentId", studentId);
		map.put("courseId", request.getParameter("courseId"));
		map.put("professorId", request.getParameter("professorId"));
		
		//트랜젝션처리를 위해 수강신청테이블 insert시 시간표테이블도 같이 insert 처리.
		courseDao.addCourse(map);
		
		return "/pages/dummy";

	}
	
	@RequestMapping("searchRegistrationCourses")
	public String searchRegistrationCourses (HttpServletRequest request, HttpServletResponse response) {
		
		String studentId = (String) request.getSession().getAttribute("login");
		
		List<RegistrationDto> result = courseDao.searchRegistrationCourses(studentId);
		ObjectMapper mapper = new ObjectMapper();
        String json;
        
		try {
			json = mapper.writeValueAsString(result);
			request.setAttribute("json", json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        return "/pages/learning_support/ajax_learning_support";
	}
	
	@RequestMapping("deleteCourse")
	public String deleteCourse (HttpServletRequest request, HttpServletResponse response) {
		
		String studentId = (String) request.getSession().getAttribute("login");
		
		String registrationId = request.getParameter("registrationId");
		String courseId = request.getParameter("courseId");
		
		courseDao.deleteCourse(registrationId,courseId, studentId);
		
		return "/pages/dummy";
	}
	
}
