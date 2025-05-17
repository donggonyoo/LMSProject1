package controller.professor_support;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

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
		String errorMsg = request.getParameter("errorMsg");
		
		Map<String, String> map = new HashMap<>();
		map.put("professorId", professorId);
		map.put("search", search);
		
		//페이징 처리
	    PaginationDto dto = new PaginationDto();

		String pageParam = request.getParameter("page");
		Integer currentPage = 
				(pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;
		Integer offset = (currentPage - 1) * dto.getItemsPerPage();
		Integer totalRows = mDao.getCourseCountRows(map); // 데이터 총갯수 
		Integer totalPages = (int) Math.ceil((double)totalRows / dto.getItemsPerPage());
		System.out.println("sort: " + request.getParameter("sortDirection"));
		try {
		    BeanUtils.populate(dto, request.getParameterMap());
		    
		    dto.setProfessorId(professorId);
		    dto.setCurrentPage(currentPage);
		    dto.setTotalRows(totalRows);
		    dto.setTotalPages(totalPages);
		    dto.setOffset(offset);
		    
		} catch (RuntimeException e2) {
        	errorMsg = e2.getMessage();
        } catch (Exception e) {
		    e.printStackTrace();
		} 
		
		List<RegistCourseDto> result =  mDao.searchCourseInfo(dto);
		
		request.setAttribute("courses", result);
		request.setAttribute("pagination", dto);
		request.setAttribute("errorMsg", errorMsg);
		
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
	
	@RequestMapping("updateCourseInfo")
	public String updateCourseInfo (HttpServletRequest request, HttpServletResponse response) {
		
		//String professorId = (String) request.getSession().getAttribute("login");
		//테스트위한 임시 professorId 지정
		String professorId = "P001";
		String errorMsg = "";
		// 기존 페이지정보 전달
		String search = request.getParameter("search");
		String page = request.getParameter("page");
		
		//파라미터 세팅
		RegistCourseDto dto = new RegistCourseDto();
		
		try {
		    BeanUtils.populate(dto, request.getParameterMap());
		    dto.setProfessorId(professorId);
		    mDao.updateCourseInfo(dto);
		} catch (RuntimeException e2) {
        	errorMsg = e2.getMessage();
        } catch (Exception e) {
		    e.printStackTrace();
		} 
		request.setAttribute("errorMsg", errorMsg);
		
		return "redirect:manageCourse?page=" + page + "&search=" + search;
	}
	
	@RequestMapping("deleteCourseInfo")
	public String deleteCourseInfo (HttpServletRequest request, HttpServletResponse response) {
		
		String errorMsg = "";
		// 기존 페이지정보 전달
		String search = request.getParameter("search");
		String page = request.getParameter("page");
		
		String courseId = request.getParameter("courseId");
		
		mDao.deleteCourseInfo(courseId);
		request.setAttribute("errorMsg", errorMsg);
		
		return "redirect:manageCourse?page=" + page + "&search=" + search;
	}
	
}
