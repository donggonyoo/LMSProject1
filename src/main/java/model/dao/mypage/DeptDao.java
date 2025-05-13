package model.dao.mypage;

import org.apache.ibatis.session.SqlSession;

import config.MyBatisConnection;

public class DeptDao {
	

	  public String selectId(String name) {
	 SqlSession connection = MyBatisConnection.getConnection();
	 String id = connection.selectOne("dept.selectId",name);
	return  id;
	}

	
	 
		
		

}
