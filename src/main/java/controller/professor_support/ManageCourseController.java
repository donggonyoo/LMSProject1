package controller.professor_support;

import java.io.IOException;
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
import model.dao.professor_support.CourseByProDao;
import model.dao.professor_support.ManageCourseDao;
import model.dto.professor_support.PaginationDto;
import model.dto.professor_support.RegistCourseDto;

@WebServlet(urlPatterns = {"/professor_support/manage/*"}, 
			initParams = {@WebInitParam(name="view",value = "/dist/")}
)
public class ManageCourseController extends MskimRequestMapping {
	
	private CourseDao courseDao = new CourseDao();
	private CourseByProDao byProDao = new CourseByProDao();
	private ManageCourseDao mDao = new ManageCourseDao();
	
	@RequestMapping("manageCourse")
	public String searchCourseInfo (HttpServletRequest request, HttpServletResponse response) {
//		String professorId = (String) request.getSession().getAttribute("login");
//		테스트위한 임시 professorId 지정
		String professorId = "P001";
		String search = request.getParameter("search");
		
		Map<String, String> map = new HashMap<>();
		map.put("professorId", professorId);
		map.put("search", search);
		
		//페이징 처리
	    PaginationDto dto = new PaginationDto();
		String pageParam = request.getParameter("page");
		Integer currentPage = 
				(pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;
		Integer offset = (currentPage - 1) * dto.getItemsPerPage();
		Integer totalRows = mDao.getCourseCountRows(map);
		Integer totalPages = (int) Math.ceil((double)totalRows / dto.getItemsPerPage());
		String sortDirection = request.getParameter("sort");
		
		dto.setProfessorId(professorId);
		dto.setSearch(search);
		dto.setCurrentPage(currentPage);
		dto.setTotalRows(totalRows);
		dto.setOffset(offset);
		dto.setTotalPages(totalPages);
		dto.setSortDirection(sortDirection);
		
		List<RegistCourseDto> result =  mDao.searchCourseInfo(dto);
		
		request.setAttribute("courses", result);
		request.setAttribute("pagination", dto);
		
		return "pages/professor_support/manageCourse";
	}
	
	@RequestMapping("changeCourse")
	public String changeCourse (HttpServletRequest request, HttpServletResponse response) {
		
		//파라미터 세팅
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("courseId", request.getParameter("courseId"));
		paramMap.put("courseStatus", request.getParameter("courseStatus"));
		
		//반환할 데이터 세팅
		Map<String, Object> jsonMap = new HashMap<>();
		
		try {
			if (mDao.changeCourse(paramMap) == 1) {
				jsonMap.put("result", "success");
			}
		} catch (RuntimeException e) {
			jsonMap.put("result", "fail");
			jsonMap.put("errorMsg", e.getMessage());
		}
		
		ObjectMapper mapper = new ObjectMapper();
        String json;
        
		try {
			json = mapper.writeValueAsString(jsonMap);
			request.setAttribute("json", json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "/pages/returnAjax";
	}
	
}
