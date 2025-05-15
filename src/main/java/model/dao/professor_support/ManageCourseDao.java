package model.dao.professor_support;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import config.MyBatisConnection;
import model.dto.professor_support.PagenationDto;

public class ManageCourseDao {

	public List<PagenationDto> searchCourseInfo(PagenationDto dto) {
		SqlSession session = MyBatisConnection.getConnection(); 
		List<PagenationDto> result = null;
		int totalRows = 0;
		
		try {
			 result = session.selectList("CourseByPro.searchCourseInfo", dto);
			 totalRows = session.selectOne("CourseByPro.countRows",dto);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		
		
		return result;
		
	}
		

}
