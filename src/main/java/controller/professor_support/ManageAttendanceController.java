package controller.professor_support;

import java.io.BufferedReader;
import java.io.IOException;
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
	/**
	 * 선택한강의 수강생들 조회
	 * @param request
	 * @param response
	 * @return
	 */
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
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> errorMap = new HashMap<>();
			errorMap.put("errorMsg", "수강생목록 불러오기 오류발생. 관리자에게 문의 하십시오.");
			try {
				request.setAttribute("json", mapper.writeValueAsString(errorMap));
			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
			}
		}
		
		return "pages/returnAjax";
	}
	
	@RequestMapping("updateAttendance")
	public String updateAttendance(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 작업 완료시 주석풀고 교체
		// String professorId = (String) request.getSession().getAttribute("login");
		String professorId = "P001";
		AttendanceDataDto attDto = new AttendanceDataDto();
		
		//클라이언트로부터 받은 json데이터 파싱
		BufferedReader reader = request.getReader();
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		String jsonData = sb.toString();
		System.out.println("==============================================================");
		System.out.println("jsonData: " + jsonData.toString());
		System.out.println("==============================================================");
		
		try {
			List<Map<String, Object>> params = 
					mapper.readValue(jsonData, mapper.getTypeFactory()
							.constructParametricType(List.class, Map.class));
			System.out.println("==============================================================");
			System.out.println("params: " + params.toString());
			System.out.println("==============================================================");
			attDao.updateAttendance(params);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error: " + e);
		}
		/*
		 * 
		 * 현재 출석관리 진입시 보여주는 강의목록에서 출석관리 버튼누를시 날짜에 따라 안나옴 로직 다시확인후 수정
		 * 
		 * 
		 * 
		 * 
		 * */
		return "pages/returnAjax";
	}
}
