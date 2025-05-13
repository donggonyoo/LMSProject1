package controller.learning_support;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
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
import model.dto.learning_support.AttendanceDto;
import model.dto.learning_support.RegistrationDto;

@WebServlet(urlPatterns = {"/learning_support/viewCourse/*"}, 
initParams = {@WebInitParam(name="view",value = "/dist/")}
)
public class ViewCourseController extends MskimRequestMapping{
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
		String courseId = request.getParameter("courseId");
		int row = courseDao.deleteCourse(registrationId, courseId);
		Map<String, Object> result = new HashMap<>();
		
		if(row > 0) {
			result.put("success", "true");
			result.put("message", "삭제 성공");
		} else {
			result.put("success", "false");
			result.put("message", "삭제 실패");
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
	
	@RequestMapping("viewCourseTime")
	public String viewCourseTime (HttpServletRequest request, HttpServletResponse response) {
//		String studentId = (String) request.getSession().getAttribute("login");
//		테스트위한 임시 studentId 지정
		String studentId = "S001";
		Map<String, Object> map = new HashMap<>();
		
		try {
			List<AttendanceDto> result = courseDao.viewCourseTime(studentId);

            // 시간 포맷팅
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            for (AttendanceDto item : result) {
                if (item.getCourseTimeStart() != null) {
                    item.setCourseTimeStartFormatted(timeFormat.format(item.getCourseTimeStart()));
                }
                if (item.getCourseTimeEnd() != null) {
                    item.setCourseTimeEndFormatted(timeFormat.format(item.getCourseTimeEnd()));
                }
            }

            map.put("success", true);
            map.put("timetable", result);
        } catch (Exception e) {
        	map.put("success", false);
        	map.put("message", "시간표 로드 실패: " + e.getMessage());
        }

        
		return "/pages/learning_support/ajax_learning_support";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
