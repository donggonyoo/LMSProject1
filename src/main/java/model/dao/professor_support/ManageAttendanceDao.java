package model.dao.professor_support;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import config.MyBatisConnection;


public class ManageAttendanceDao {
	
	public List<Map<String, Object>> getCoursesInfoByPro(String professorId) {
		
		List<Map<String, Object>> result = null;
	    
		try (SqlSession session = MyBatisConnection.getConnection()) { 
	        result = session.selectList("MngAttendance.getCoursesInfoByPro", professorId);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
		return result;
		
	}

	public List<Map<String, Object>> getAttendance(String courseId) {
		List<Map<String, Object>> result = null;
	    
		try (SqlSession session = MyBatisConnection.getConnection()) { 
	        result = session.selectList("MngAttendance.getAttendance", courseId);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
		return result;
	}

	

}
