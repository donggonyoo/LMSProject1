package model.dao.mypage;

import org.apache.ibatis.session.SqlSession;

import config.MyBatisConnection;

public class StudentDao {
	
	public boolean idchk(String id) {
		SqlSession connection = MyBatisConnection.getConnection();
		int x  = (Integer)connection.selectOne("student.cnt",id);
		if(x!=0) {
			return false;
		}
		return true;
		
	}

}
