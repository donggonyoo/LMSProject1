package model.dao.professor_support;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

import config.MyBatisConnection;
import model.dto.professor_support.PaginationDto;
import model.dto.professor_support.RegistCourseDto;

public class ManageCourseDao {

	public List<RegistCourseDto> searchCourseInfo(PaginationDto dto) {
		
		SqlSession session = MyBatisConnection.getConnection(); 
		List<RegistCourseDto> result = null;
		
		try {
			 result = session.selectList("CourseByPro.searchCourseInfo", dto);
			 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return result;
		
	}
	
	public int getCourseCountRows(Map<String, String> map) {
		
		SqlSession session = MyBatisConnection.getConnection(); 
		int totalRows = 0;
		
		try {
			 totalRows = session.selectOne("CourseByPro.getCourseCountRows",map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		
		
		return totalRows;
		
	}

	public int changeCourse(Map<String, Object> paramMap) {
		
		SqlSession session = MyBatisConnection.getConnection(); 
		int result = 0;
		
		try {
			 if (session.update("CourseByPro.changeCourse",paramMap) > 0) {
				 return result + 1;
			 } else {
				 throw new RuntimeException("chgCourseFail");
			 }
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw new RuntimeException("chgCourseFail", e);
		} finally {
			MyBatisConnection.close(session);
		}

	}
		

}
