package model.dao.mypage;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import config.MyBatisConnection;
import model.dto.mypage.FindIdDto;
import model.dto.mypage.FindPwDto;
import model.dto.mypage.LoginDto;

public class ProStuDao {
	
	public Map<String,String> login(String id ) {
		SqlSession conn = MyBatisConnection.getConnection();
		LoginDto loginDto = new LoginDto();
		loginDto.setProfessorId(id);
		loginDto.setStudentId(id);
	
		try {
			Map<String,String>  map = conn.selectOne("prostu.loginChk",loginDto);
			return map;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			MyBatisConnection.close(conn);
		}
		return null;
	}
	
	
	public String findId(String name , String email) {
		SqlSession conn = MyBatisConnection.getConnection();
		FindIdDto dto = new FindIdDto();
		dto.setProfessorEmail(email);
		dto.setProfessorName(name);
		dto.setStudentEmail(email);
		dto.setStudentName(name);
		
		try {
			String id = conn.selectOne("prostu.findId",dto);
			return id;	
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			MyBatisConnection.close(conn);
		}
		return null;
		
	}
	
	public String findPw(String id , String email) {
		SqlSession connection = MyBatisConnection.getConnection();
		FindPwDto dto = new FindPwDto();
		dto.setProfessorEmail(email);
		dto.setProfessorId(id);
		dto.setStudentEmail(email);
		dto.setStudentId(id);
		try {
			return connection.selectOne("prostu.findPw",dto);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			MyBatisConnection.close(connection);
		}
		return null;

	}

	

}
