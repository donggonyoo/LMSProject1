package model.dao.learning_support;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import config.MyBatisConnection;
import model.dto.learning_support.AttendanceDto;
import model.dto.learning_support.CourseDto;
import model.dto.learning_support.DeptDto;
import model.dto.learning_support.RegistrationDto;
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

	public List<DeptDto> getDepartments(String college) {
		
		SqlSession session = MyBatisConnection.getConnection(); 
		List<DeptDto> result = null;
		
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

	public int addCourse(Map<String, Object> map) {
		
		SqlSession session = MyBatisConnection.getConnection(); 
		int result = 0;
		long maxId = -1;
		
		// 등록된 수강신청id 최대값 조회
		maxId = session.selectOne("getMaxRegistrationIdNumber");
		String registrationId = "R" + (++maxId);
		map.put("registrationId", registrationId);
		
		try {
			session.insert("course.addCourse", map);
			map.remove("registrationId");
			addAttendance(map, session);		
			session.commit();
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback(); // 커밋 안하고 그냥 종료함으로써 둘중 하나 오류시 둘다 롤백처리.
			throw new RuntimeException("수강신청 실패: " + e.getMessage(), e);
		} finally {
			session.close();
		}
		
		return result;
	}
	
	public void addAttendance(Map<String, Object> map, SqlSession session) {
		
		int result = 0;
		long maxId = -1;
		
		// 등록된 수강신청id 최대값 조회
		maxId = session.selectOne("getMaxAttendanceIdNumber");
		String attendanceId = "A" + (++maxId);
		map.put("attendanceId", attendanceId);
		
		try {
			session.insert("course.addAttendance", map);
		} catch (Exception e) {	
			e.printStackTrace();
			throw new RuntimeException("시간표 데이터 등록 실패; " + e.getMessage(), e);
		}	
	}

	public List<RegistrationDto> searchRegistrationCourses(String studentId) {
		
		SqlSession session = MyBatisConnection.getConnection(); 
		List<RegistrationDto> result = null;
		
		try {
			 result = session.selectList("course.searchRegistrationCourses", studentId);
		} catch (Exception e) {	
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		
		return result;
	}

	public int deleteCourse(String registrationId, String courseId) {
		
		SqlSession session = MyBatisConnection.getConnection(); 
		int num = 0;

		try {
			session.delete("course.deleteCourse", registrationId);
			deleteAttendance(courseId, session);
			MyBatisConnection.close(session);
			num=1;
		} catch (Exception e) {	
			e.printStackTrace();
			session.close();
		}
		
		return num;
		
	}
	
	public void deleteAttendance(String courseId, SqlSession session) {
		
		try {
			session.delete("course.deleteAttendance", courseId); 
		} catch (Exception e) {	
			e.printStackTrace();
			throw new RuntimeException("시간표 데이터 삭제 실패; " + e.getMessage(), e);
		}	
	}
	
	public List<AttendanceDto> viewCourseTime(String studentId) {
		
		SqlSession session = MyBatisConnection.getConnection(); 
		List<AttendanceDto> result = null;
		
		try {
			result = session.selectList("course.viewCourseTime", studentId);
		} catch (Exception e) {	
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		
		return result;
	}

	

}
