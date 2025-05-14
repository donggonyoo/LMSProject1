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
import model.dto.professor_support.RegistCourseDto;

@WebServlet(urlPatterns = {"/professor_support/*"}, 
			initParams = {@WebInitParam(name="view",value = "/dist/")}
)
public class CourseByProController extends MskimRequestMapping {
	
	private CourseDao courseDao = new CourseDao();
	
	@RequestMapping("registCourse")
	public String registerCourse (HttpServletRequest request, HttpServletResponse response) {
		List<DeptDto> departments = courseDao.getDepartments("");
		request.setAttribute("departments", departments);
		return "/pages/professor_support/registCourseByPro";
	}
	
	@RequestMapping("registCourseForm")
	public String registCourseForm (HttpServletRequest request, HttpServletResponse response) {
		
		RegistCourseDto dto = new RegistCourseDto();
        
        dto.setMajorName(request.getParameter("majorName"));
        dto.setCourseName(request.getParameter("courseName"));
        dto.setProfessorName(request.getParameter("professorName"));
        dto.setCreditCategory(request.getParameter("creditCategory"));
        dto.setCourseDay(request.getParameter("courseDay"));
        
        // Integer 필드 처리
        try {
            String startTimeHourStr = request.getParameter("startTimeHour");
            dto.setStartTimeHour(startTimeHourStr != null && !startTimeHourStr.isEmpty() ? Integer.valueOf(startTimeHourStr) : null);
            
            String endTimeHourStr = request.getParameter("endTimeHour");
            dto.setEndTimeHour(endTimeHourStr != null && !endTimeHourStr.isEmpty() ? Integer.valueOf(endTimeHourStr) : null);
            
            String scoreStr = request.getParameter("score");
            dto.setScore(scoreStr != null && !scoreStr.isEmpty() ? Integer.valueOf(scoreStr) : null);
        } catch (NumberFormatException e) {
            // 에러 처리 (예: 사용자에게 오류 메시지 전달)
            request.setAttribute("error", "시간과 학점은 숫자로 입력해주세요.");
            
            return "/pages/error";
        }
        
        dto.setDescription(request.getParameter("description"));
        
		return "/pages/professor_support/registCourseByPro";
	}
	
}
