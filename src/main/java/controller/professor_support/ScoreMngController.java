package controller.professor_support;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
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
import model.dao.professor_support.CourseByProDao;
import model.dao.professor_support.ScoreMngDao;
import model.dto.professor_support.ScoreMngDto;

@WebServlet(urlPatterns = {"/professor_support/score/*"}, 
			initParams = {@WebInitParam(name="view",value = "/dist/")}
)
public class ScoreMngController extends MskimRequestMapping {
	
	private CourseByProDao byProDao = new CourseByProDao();
	private ScoreMngDao scoreDao = new ScoreMngDao();
	ObjectMapper objectMapper = new ObjectMapper();
	
	@RequestMapping("scoreMng")
	public String score (HttpServletRequest request, HttpServletResponse response) {
		// 작업 완료시 주석풀고 교체
		//String professorId = (String) request.getSession().getAttribute("login");
		String professorId = "P001";
		
		Map<String, Object> result = byProDao.getProfessorInfo(professorId); 
		
		request.setAttribute("professorName", result.get("professor_name"));
		request.setAttribute("deptName", result.get("dept_name"));
		
		return "pages/professor_support/manageScore";
	}
	/**
	 * 페이지 로드시 해당강사의 강의들 조회
	 * @param request
	 * @param response
	 * @return Ajax
	 */
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
	
	/**
	 * 강의 관리 
	 * @param request
	 * @param response
	 * @return Ajax
	 */
	@RequestMapping("getScoreInfo")
	public String getScore (HttpServletRequest request, HttpServletResponse response) {
		// 작업 완료시 주석풀고 교체
		//String professorId = (String) request.getSession().getAttribute("login");
		Map<String, Object> params = new HashMap<String, Object>();
		String professorId = "P001";
		String courseId = request.getParameter("courseId");
		String courseName = request.getParameter("courseName");
		
		params.put("professorId", professorId);
		params.put("courseId", courseId);
		
		ObjectMapper mapper = new ObjectMapper();
        String json = "";
        
        // json 세팅
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> courseData = new HashMap<String, Object>();
        List<Map<String, Object>> gradeList = new ArrayList<Map<String,Object>>();        
		List<ScoreMngDto> scoreResult =  scoreDao.getScoreInfo(params);
		
		courseData.put("courseName", courseName);
		if (!scoreResult.isEmpty()) {
			ScoreMngDto dto = scoreResult.get(0);
			courseData.put("coursePeriod", dto.getCoursePeriod());
			courseData.put("professorName", dto.getProfessorName());
		}
		
		for (ScoreMngDto dto : scoreResult) {
			Map<String, Object> grade = new HashMap<String, Object>();
			grade.put("studentName", dto.getStudentName());
			grade.put("studentId", dto.getStudentId());
			grade.put("deptName", dto.getDeptName());
			grade.put("scoreMid", dto.getScoreMid());
			grade.put("scoreFinal", dto.getScoreFinal());
			grade.put("scoreTotal", dto.getScoreTotal());
			grade.put("scoreGrade", dto.getScoreGrade());
			gradeList.add(grade);
		}
		courseData.put("grades", gradeList);
		result.put(courseId, courseData);
		
		try {
			json = mapper.writeValueAsString(result);
			request.setAttribute("json", json);
		} catch (IOException e) {
			e.printStackTrace();
			request.setAttribute("json", "{}");
		}

		return "pages/returnAjax";
	}
	
	/**
	 * 학생 성적 입력
	 * @param request
	 * @param response
	 * @return 
	 * @throws IOException 
	 */
	@RequestMapping("updateScore")
	public String updateScore (HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 작업 완료시 주석풀고 교체
		//String professorId = (String) request.getSession().getAttribute("login");
		String professorId = "P001";		

        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String jsonData = sb.toString();
        
		try {
			// JSON 데이터를 List<Map<String, Object>> 형태로 파싱
            List<Map<String, Object>> params = 
            		objectMapper.readValue(jsonData, objectMapper.getTypeFactory()
            					.constructParametricType(List.class, Map.class));
			scoreDao.updateScore(params);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error: " + e);
		}

		return "pages/dummy";
	}
}
