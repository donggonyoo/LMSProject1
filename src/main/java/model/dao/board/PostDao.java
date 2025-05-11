package model.dao.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import config.MyBatisConnection;
import domain.Post;

public class PostDao {
    public int boardCount(String column, String find) {
        SqlSession session = MyBatisConnection.getConnection();
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("column", column);
            map.put("find", find);
            int count = session.selectOne("post.count", map);
            System.out.println("DAO boardCount - column: " + column + ", find: " + find + ", count: " + count);
            return count;
        } finally {
            MyBatisConnection.close(session);
        }
    }

    public List<Post> list(int pageNum, int limit, String column, String find) {
        SqlSession session = MyBatisConnection.getConnection();
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("startRow", (pageNum - 1) * limit);
            map.put("pageSize", limit);
            map.put("column", column);
            map.put("find", find);
            System.out.println("DAO list - startRow: " + map.get("startRow") + ", pageSize: " + map.get("pageSize") + 
                               ", column: " + column + ", find: " + find);
            List<Post> result = session.selectList("post.selectList", map);
            System.out.println("DAO list - result size: " + (result != null ? result.size() : "null"));
            return result;
        } finally {
            MyBatisConnection.close(session);
        }
    }

    public Post selectOne(String post_id) {
        SqlSession session = MyBatisConnection.getConnection();
        try {
            Post post = session.selectOne("post.selectOne", post_id);
            System.out.println("DAO selectOne - post_id: " + post_id + ", result: " + post);
            return post;
        } finally {
            MyBatisConnection.close(session);
        }
    }
}