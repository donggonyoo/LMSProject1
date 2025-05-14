package model.dao.professor_support;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import config.MyBatisConnection;
import model.dto.learning_support.AttendanceDto;
import model.dto.learning_support.CourseDto;
import model.dto.learning_support.DeptDto;
import model.dto.learning_support.RegistrationDto;
import model.dto.learning_support.SearchDto;
import model.dto.professor_support.RegistCourseDto;


public class CourseByProDao {

	public int registCourseByPro(RegistCourseDto dto) {
		SqlSession session = MyBatisConnection.getConnection(); 
		int result = 0;
		
		try {
			 result = session.insert("course.insertCourseByPro", dto);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		
		return result;
		
	}

	

	

}
