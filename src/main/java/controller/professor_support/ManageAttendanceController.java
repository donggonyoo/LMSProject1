package controller.professor_support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import model.dao.professor_support.ManageAttendanceDao;
import model.dto.professor_support.AttendanceDataDto;

@WebServlet(urlPatterns = { "/professor_support/attendance/*" }, 
			initParams = {@WebInitParam(name = "view", value = "/dist/") })
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
		AttendanceDataDto attDto = new AttendanceDataDto();
		
		try {
			BeanUtils.populate(attDto, request.getParameterMap());
			List<Map<String, Object>> attList =  attDao.getAttendance(attDto);
			String json = mapper.writeValueAsString(attList);
			request.setAttribute("json", json);
			System.out.println("====================================================");
			System.out.println(json.toString());
			System.out.println("====================================================");
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> errorMap = new HashMap<>();
			errorMap.put("errorMsg", "시간표 불러오기 오류발생. 관리자에게 문의 하십시오.");
			try {
				request.setAttribute("json", mapper.writeValueAsString(errorMap));
			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
			}
		}
		
		return "pages/return_ajax";
	}
}
