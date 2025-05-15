package model.dao.professor_support;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

import config.MyBatisConnection;
import model.dto.professor_support.RegistCourseDto;


public class CourseByProDao {
	
	// 커스텀 예외 클래스 정의
	public class DuplicateKeyException extends RuntimeException {
	    public DuplicateKeyException(String message) {
	        super(message);
	    }

	    public DuplicateKeyException(String message, Throwable cause) {
	        super(message, cause);
	    }
	}

	public String getMaxcourseIdNumber() {
		SqlSession session = MyBatisConnection.getConnection(); 
		String result = "";
		Long num = 0L;
		try {
			 num = session.selectOne("getMaxcourseIdNumber");
			 result = "C" + (++num);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		
		
		return result;
	}

	public String getMaxcourseTimeIdNumber() {
		SqlSession session = MyBatisConnection.getConnection(); 
		String result = "";
		Long num = 0L;
		try {
			 num = session.selectOne("getMaxcourseTimeIdNumber");
			 result = "CT" + (++num);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		
		
		return result;
	}

	public void insertCourseInfo(RegistCourseDto dto) {
		SqlSession session = MyBatisConnection.getConnection(); 
		
		try {
			 int result = session.insert("insertCourseInfo",dto);
			 
			 if (result > 0) {
				 insertCourseTime(dto, session);
				 session.commit();
			 } else  {
				 throw new RuntimeException("강의 등록 실패");
			 }
		} catch (PersistenceException e) {
			// 중복 키 오류 확인 (MyBatis는 SQL 상태 코드를 제공하지 않으므로 직접 확인 어려움)
	        if (e.getCause() != null && e.getCause().getMessage().contains("Duplicate entry")) {
	            throw new DuplicateKeyException("Duplicate", e);
	        }
	        throw new RuntimeException("데이터베이스 오류", e);
	    } finally {
			session.close();
		}
	}
	
	public void insertCourseTime(RegistCourseDto dto, SqlSession session) {
		
		try {
			 if (session.insert("insertCourseTime",dto) < 1) {
				 throw new RuntimeException("강의시간 데이터 등록 실패");
			 }
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("강의시간 데이터 등록 실패");
		}
	}

	

	

}
