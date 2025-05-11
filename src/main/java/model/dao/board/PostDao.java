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
            return session.selectOne("post.count", map);
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
            return session.selectList("post.selectList", map);
        } finally {
            MyBatisConnection.close(session);
        }
    }

    public Post selectOne(String post_id) {
        SqlSession session = MyBatisConnection.getConnection();
        try {
            return session.selectOne("post.selectOne", post_id);
        } finally {
            MyBatisConnection.close(session);
        }
    }

    public void incrementReadCount(String post_id) {
        SqlSession session = MyBatisConnection.getConnection();
        try {
            session.update("post.incrementReadCount", post_id);
            session.commit();
        } finally {
            MyBatisConnection.close(session);
        }
    }

    public void insert(Post post) {
        SqlSession session = MyBatisConnection.getConnection();
        try {
            session.insert("post.insert", post);
            session.commit();
        } finally {
            MyBatisConnection.close(session);
        }
    }
}