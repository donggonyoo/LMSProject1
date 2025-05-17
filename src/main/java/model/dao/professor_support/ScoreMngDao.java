package model.dao.professor_support;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

import config.MyBatisConnection;
import model.dto.professor_support.RegistCourseDto;


public class ScoreMngDao {
	
	public void updateCourseInfo(RegistCourseDto dto) {
		
	    try (SqlSession session = MyBatisConnection.getConnection()) { 
	        // course 테이블 업데이트
	        if (session.update("CourseByPro.updateCourseInfo", dto) <= 0) {
	            throw new RuntimeException("Failed to update course info");
	        }


	        // 성공 시 커밋
	        session.commit();
	    } catch (PersistenceException e) {
	        throw new RuntimeException("Course update failed: " + e.getMessage(), e);
	    }
	}

}
