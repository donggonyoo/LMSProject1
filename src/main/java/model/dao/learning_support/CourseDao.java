package model.dao.learning_support;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import config.MyBatisConnection;
import model.dto.learning_support.CourseDto;
import model.dto.learning_support.SearchDto;


public class CourseDao {

	public List<String> getColleges() {
		
		SqlSession session = MyBatisConnection.getConnection(); 
		List<String> result = null;
		
		try {
			 result = session.selectList("course.selectColleges");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		
		return result;
	}

	public List<Map<String, String>> getDepartments(String college) {
		
		SqlSession session = MyBatisConnection.getConnection(); 
		List<Map<String, String>> result = null;
		
		try {
			 result = session.selectList("course.selectDepartments", college);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		
		return result;
	}

	public List<CourseDto> searchCourse(SearchDto searchDto) {

		SqlSession session = MyBatisConnection.getConnection(); 
		List<CourseDto> result = null;
		
		try {
			 result = session.selectList("course.searchCourse", searchDto);
		} catch (Exception e) {	
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		
		return result;
	}

}
