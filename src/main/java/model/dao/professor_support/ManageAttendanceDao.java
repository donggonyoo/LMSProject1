package model.dao.professor_support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

import config.MyBatisConnection;
import model.dto.professor_support.RegistCourseDto;
import model.dto.professor_support.ScoreMngDto;


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

	

}
