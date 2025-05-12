package model.dao.mypage;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import config.MyBatisConnection;
import domain.Student;

public class StudentDao {
	
	
	public boolean idchk(String id) {
		SqlSession connection = MyBatisConnection.getConnection();
		int x  = (Integer)connection.selectOne("student.cnt",id);
		if(x!=0) {
			return false;
		}
		return true;
	}
	
	public void list() {
		SqlSession connection = MyBatisConnection.getConnection();
		List<Student> list = connection.selectList("student.list");
		for (Student student : list) {
			System.out.println(student);
		}
	}
	
	public static void main(String[] args) {
		StudentDao studentDao = new StudentDao();
		studentDao.list();
	}

}
