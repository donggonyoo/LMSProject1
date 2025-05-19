package controller.professor_support;

import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import model.dao.professor_support.ManageAttendanceDao;

@WebServlet(urlPatterns = { "/professor_support/attendance/*" }, initParams = {
		@WebInitParam(name = "view", value = "/dist/") })
public class ManageAttendanceController extends MskimRequestMapping {

	private ManageAttendanceDao attDao = new ManageAttendanceDao();

	ObjectMapper mapper = new ObjectMapper();

	@RequestMapping("attendance")
	public String attendance(HttpServletRequest request, HttpServletResponse response) {
		// 작업 완료시 주석풀고 교체
		// String professorId = (String) request.getSession().getAttribute("login");
		String professorId = "P001";

		List<Map<String, Object>> result = attDao.getCoursesInfoByPro(professorId);
		request.setAttribute("courses", result);

		return "pages/professor_support/manageAttendance";
	}
	
	@RequestMapping("getAttendance")
	public String getAttendance(HttpServletRequest request, HttpServletResponse response) {
		// 작업 완료시 주석풀고 교체
		// String professorId = (String) request.getSession().getAttribute("login");
		String professorId = "P001";
		
		List<Map<String, Object>> attList =  attDao.getAttendance(request.getParameter("courseId"));
		

		return "pages/return_ajax";
	}
}
