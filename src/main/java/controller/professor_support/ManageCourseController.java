package controller.professor_support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import model.dao.learning_support.CourseDao;
import model.dao.professor_support.CourseByProDao;
import model.dao.professor_support.ManageCourseDao;
import model.dto.learning_support.RegistrationDto;
import model.dto.professor_support.PagenationDto;

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
		
		
		
		
		//페이징 처리 하다말음
		/*
		 * 
		<select id="selectPagedCourses" parameterType="com.example.dto.PagenationDto" resultType="com.example.dto.Course">
        SELECT
        c.courseId,
        c.courseName,
        c.coursePeriod,
        c.creditCategory,
        c.courseTimeYoil,
        c.courseTimeStart,
        c.courseTimeEnd,
        c.currentEnrollment,
        c.courseMaxCnt,
        c.courseScore,
        c.courseStatus,
        c.coursePlan
        FROM
        course c
        INNER JOIN course_time ct
        ON c.course_id = ct.course_id AND c.professor_id = ct.professor_id
        WHERE c.professor_id = #{professorId}
        <if test="search != null and search != ''">
            AND c.courseName LIKE '%${search}%'
        </if>
        <if test="sortDirection != null and sortDirection != ''">
            ORDER BY
            <choose>
                <when test="sortDirection.startsWith('courseName')">c.courseName</when>
                <when test="sortDirection.startsWith('courseScore')">c.courseScore</when>
                <otherwise>c.courseId</otherwise>
            </choose>
            <if test="sortDirection.endsWith('-desc')">DESC</if>
        </if>
        LIMIT #{offset}, #{itemsPerPage}
    </select>
   
		 */
		
		
		
		
		
		
		
		
		
		
		
		
		PagenationDto dto = new PagenationDto();
		dto.setProfessorId(professorId);
		dto.setSearch(request.getParameter("search"));
		dto.setCurrentPage(Integer.parseInt(request.getParameter("currentPage")));
		dto.setTotalRows(Integer.parseInt(request.getParameter("totalRows")));
		dto.setItemsPerPage(Integer.parseInt(request.getParameter("itemsPerPage")));
		dto.setTotalPages(Integer.parseInt(request.getParameter("totalPages")));
		dto.setStartPage(Integer.parseInt(request.getParameter("startPage")));
		dto.setEndPage(Integer.parseInt(request.getParameter("endPage")));
		
		List<PagenationDto> result =  mDao.searchCourseInfo(dto);
		
		
		
		return "pages/professor_support/manageCourse";
	}
	
	
	
}
