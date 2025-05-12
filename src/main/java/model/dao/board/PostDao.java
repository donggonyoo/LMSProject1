package model.dao.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import config.MyBatisConnection;
import domain.Post;

public class PostDao {

    // 게시물 삽입
    public void insert(Post post) {
        SqlSession session = MyBatisConnection.getConnection();
        try {
            System.out.println("MyBatis insert 호출 전: postId=" + post.getPostId());
            session.insert("post.insert", post);
            System.out.println("MyBatis insert 성공 후: postId=" + post.getPostId());
        } catch (Exception e) {
            session.rollback();
            System.out.println("MyBatis insert 실패: " + e.getMessage());
            throw e;
        } finally {
            MyBatisConnection.close(session);
        }
    }
    
    public void update(Post post) {
        SqlSession session = MyBatisConnection.getConnection();
        try {
            session.update("post.update", post);
        } catch (Exception e) {
            session.rollback();
            throw e;
        } finally {
            MyBatisConnection.close(session);
        }
    }

    // 게시물 삭제
    public void delete(String postId) {
        SqlSession session = MyBatisConnection.getConnection();
        try {
            session.delete("post.delete", postId);
        } catch (Exception e) {
            session.rollback();
            throw e;
        } finally {
            MyBatisConnection.close(session);
        }
    }

    // 게시물 수 조회
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

    // 게시물 목록 조회 (페이징, 검색 포함)
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

    // 게시물 하나 조회
    public Post selectOne(String postId) {
        SqlSession session = MyBatisConnection.getConnection();
        try {
            return session.selectOne("post.selectOne", postId);
        } finally {
            MyBatisConnection.close(session);
        }
    }

    // 조회수 증가
    public void incrementReadCount(String postId) {
        SqlSession session = MyBatisConnection.getConnection();
        try {
            session.update("post.incrementReadCount", postId);
        } finally {
            MyBatisConnection.close(session);
        }
    }

    // 최대 postId 조회
    public String getMaxPostId() {
        SqlSession session = MyBatisConnection.getConnection();
        try {
            return session.selectOne("post.getMaxPostId");
        } finally {
            MyBatisConnection.close(session);
        }
    }

    // 최대 그룹 번호 조회
    public Integer getMaxGroup() {
        SqlSession session = MyBatisConnection.getConnection();
        try {
            return session.selectOne("post.getMaxGroup");
        } finally {
            MyBatisConnection.close(session);
        }
    }

    // 그룹 내 step 증가 (댓글 정렬용)
    public void updateGroupStep(int group, int step) {
        SqlSession session = MyBatisConnection.getConnection();
        try {
            Map<String, Integer> map = new HashMap<>();
            map.put("group", group);
            map.put("step", step);
            session.update("post.updateGroupStep", map);
        } finally {
            MyBatisConnection.close(session);
        }
    }
}