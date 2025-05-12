package controller.board;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Post;
import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import model.dao.board.PostDao;

@WebServlet(urlPatterns = {"/post/*"}, initParams = {@WebInitParam(name = "view", value = "/dist/pages/board/")})
public class PostController extends MskimRequestMapping {
    private PostDao dao = new PostDao();

    @RequestMapping("getPosts")
    public String getPosts(HttpServletRequest request, HttpServletResponse response) {
        int limit = 10;
        int pageNum = 1;

        try {
            String pageNumParam = request.getParameter("pageNum");
            if (pageNumParam != null && !pageNumParam.trim().isEmpty()) {
                pageNum = Integer.parseInt(pageNumParam);
                if (pageNum < 1) pageNum = 1;
            }
        } catch (NumberFormatException e) {
            pageNum = 1;
        }

        String column = request.getParameter("column");
        String find = request.getParameter("find");

        if (column == null || column.trim().isEmpty() || find == null || find.trim().isEmpty()) {
            column = null;
            find = null;
        }

        int boardcount = dao.boardCount(column, find);
        List<Post> list = dao.list(pageNum, limit, column, find);
        if (list == null) {
            list = new ArrayList<>();
        }

        int maxpage = (int) Math.ceil((double) boardcount / limit);
        int startpage = ((int) (pageNum / 10.0 + 0.9) - 1) * 10 + 1;
        int endpage = startpage + 9;
        if (endpage > maxpage) endpage = maxpage;

        int boardNum = boardcount - (pageNum - 1) * limit;

        request.setAttribute("boardcount", boardcount);
        request.setAttribute("pageNum", pageNum);
        request.setAttribute("list", list);
        request.setAttribute("startpage", startpage);
        request.setAttribute("endpage", endpage);
        request.setAttribute("maxpage", maxpage);
        request.setAttribute("boardNum", boardNum);
        request.setAttribute("today", new Date());
        request.setAttribute("column", column);
        request.setAttribute("find", find);

        return "post/getPosts";
    }

    @RequestMapping("createPost")
    public String createPost(HttpServletRequest request, HttpServletResponse response) {
        return "post/createPost";
    }

    @RequestMapping("write")
    public String write(HttpServletRequest request, HttpServletResponse response) {
        String authorId = request.getParameter("author_id");
        String pass = request.getParameter("post_password");
        String postTitle = request.getParameter("post_title");
        String postContent = request.getParameter("post_content");
        String postFile = request.getParameter("post_file");

        if (authorId == null || authorId.trim().isEmpty() ||
            pass == null || pass.trim().isEmpty() ||
            postTitle == null || postTitle.trim().isEmpty()) {
            request.setAttribute("error", "글쓴이, 비밀번호, 제목은 필수입니다.");
            return "post/createPost";
        }

        Post post = new Post();
        post.setAuthor_id(authorId);
        post.setPost_password(pass);
        post.setPost_title(postTitle);
        post.setPost_content(postContent);
        post.setPost_file(postFile);
        post.setPost_created_at(new Date());
        post.setPost_read_count(0);
        post.setPost_group(0);
        post.setPost_group_level(0);
        post.setPost_group_step(0);

        try {
            dao.insert(post);
            return "redirect:/post/getPosts";
        } catch (Exception e) {
            request.setAttribute("error", "게시물 등록 실패: " + e.getMessage());
            return "post/createPost";
        }
    }

    @RequestMapping("getPostDetail")
    public String getPostDetail(HttpServletRequest request, HttpServletResponse response) {
        String postId = request.getParameter("post_id");
        String readcnt = request.getParameter("readcnt");
        if (postId == null || postId.trim().isEmpty()) {
            request.setAttribute("error", "게시물 ID가 필요합니다.");
            return "post/getPosts";
        }

        Post post = dao.selectOne(postId);
        if (post == null) {
            request.setAttribute("error", "게시물을 찾을 수 없습니다.");
            return "post/getPosts";
        }

        if (readcnt == null || !readcnt.trim().equals("f")) {
            dao.incrementReadCount(postId);
            post.setPost_read_count(post.getPost_read_count() + 1);
        }
        request.setAttribute("post", post);
        return "post/getPostDetail";
    }
}