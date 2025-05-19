package model.dao.professor_support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import config.MyBatisConnection;
import model.dto.professor_support.AttendanceDataDto;


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

	public List<Map<String, Object>> getAttendance(AttendanceDataDto attDto) {
		List<Map<String, Object>> result = null;
	    
		try (SqlSession session = MyBatisConnection.getConnection()) { 
	        result = session.selectList("MngAttendance.getAttendance", attDto);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
		return result;
	}

	public int updateAttendance(List<Map<String, Object>> params) {
		int result = 0;
		
		try (SqlSession session = MyBatisConnection.getConnection()) {
			for (Map<String, Object> m : params) {
				Map<String, Object> paramMap = new HashMap<>();
				paramMap.put("attendanceId", m.get("attendanceId"));
				paramMap.put("studentId", m.get("studentId"));
				paramMap.put("studentName", m.get("studentName"));
				paramMap.put("attendanceStatus", m.get("attendanceStatus"));
				
				session.insert("MngAttendance.insertAttendanceHistory", paramMap);
				session.update("MngAttendance.updateAttendance", paramMap);
			}
	        session.commit();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
		return result;
		
	}

	

}
