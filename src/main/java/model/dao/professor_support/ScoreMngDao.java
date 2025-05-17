package model.dao.professor_support;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

import config.MyBatisConnection;
import model.dto.professor_support.RegistCourseDto;
import model.dto.professor_support.ScoreMngDto;


public class ScoreMngDao {
	
	public List<Map<String, Object>> getCoursesInfo(String professorId) {
		
		SqlSession session = MyBatisConnection.getConnection();
		
		List<Map<String, Object>> result = null;
	    
		try { 
	        // course 테이블 업데이트
	        result = session.selectList("ScoreMng.getCoursesInfo", professorId);
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	    	MyBatisConnection.close(session);
	    }
		
		return result;
	}

	public List<Map<String, Object>> getScoreInfo(String professorId) {
		
		SqlSession session = MyBatisConnection.getConnection();
		
		List<Map<String, Object>> result = null;
	    
		try { 
	        // course 테이블 업데이트
	        result = session.selectList("ScoreMng.getScoreInfo", professorId);
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	    	MyBatisConnection.close(session);
	    }
		
		return result;
	}

}
