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

@WebServlet(urlPatterns = {"/professor_support/*"}, 
			initParams = {@WebInitParam(name="view",value = "/dist/")}
)
public class CourseByProController extends MskimRequestMapping {
	
	private CourseDao courseDao = new CourseDao();
	private CourseByProDao dao = new CourseByProDao();
	
	@RequestMapping("registCourse")
	public String registerCourse (HttpServletRequest request, HttpServletResponse response) {
		List<DeptDto> departments = courseDao.getDepartments("");
		request.setAttribute("departments", departments);
		return "/pages/professor_support/registCourseByPro";
	}
	
	@RequestMapping("registCourseForm")
	public String registCourseForm (HttpServletRequest request, HttpServletResponse response) {
		
		RegistCourseDto dto = new RegistCourseDto();
        
//		dto.setCourseId(generateCourseId()); // 예: 고유 ID 생성 로직 필요
//        dto.setDeptId(request.getParameter("majorName"));
//        dto.setProfessorId(convertToProfessorId(request.getParameter("professorName"))); // 교수명 → ID 변환 로직 필요
//        dto.setCourseName(request.getParameter("courseName"));
//        dto.setCourseStatus("OPEN"); // 기본값 설정 (필요 시 폼에서 입력)
//        dto.setCourseMaxCnt(30); // 기본값 설정 (필요 시 폼에서 입력)
//        dto.setCourseScore(Integer.valueOf(request.getParameter("score")));
//        dto.setCreditCategory(request.getParameter("courseType"));
//        dto.setCoursePlan(request.getParameter("description"));
//
//        dto.setCourseTimeId(generateCourseTimeId()); // 예: 고유 ID 생성 로직 필요
//        dto.setCourseTimeYoil(request.getParameter("courseDay"));
//        dto.setCourseTimeLoc("Room 101"); // 기본값 설정 (필요 시 폼에서 입력)
//        dto.setCourseTimeStart(request.getParameter("startTimeHour"));
//        dto.setCourseTimeEnd(request.getParameter("endTimeHour"));
        /*
         dto 컬럼구성 변경해야함
         db에는 교수id를 저장해야되는데 교수name만 받아오고있음 
         이부분 어찌처리할지 생각해야함.
         
         */
        
        dao.registCourseByPro(dto);
        
		return "/pages/professor_support/registCourseByPro";
	}
	
}
