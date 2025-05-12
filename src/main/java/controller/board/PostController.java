package controller.board;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import domain.Post;
import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import model.dao.board.PostDao;

@WebServlet(urlPatterns = {"/post/*"}, initParams = {@WebInitParam(name = "view", value = "/dist/pages/board/")})
@MultipartConfig
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
        System.out.println("column: " + column + ", find: " + find); // 디버깅 로그

        if (column == null || column.trim().isEmpty() || find == null || find.trim().isEmpty()) {
            column = null;
            find = null;
        }

        int boardcount = dao.boardCount(column, find);
        List<Post> list = dao.list(pageNum, limit, column, find);
        System.out.println("boardcount: " + boardcount + ", list size: " + (list != null ? list.size() : 0)); // 디버깅 로그
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
        String parentPostId = request.getParameter("parent_post_id");
        if (parentPostId != null && !parentPostId.trim().isEmpty()) {
            request.setAttribute("parent_post_id", parentPostId);
        }
        return "post/createPost";
    }

    @RequestMapping("write")
    public String write(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Part filePart = request.getPart("post_file");
        String postFile = null;
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = filePart.getSubmittedFileName();
            String uploadPath = request.getServletContext().getRealPath("/upload/board");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();
            postFile = System.currentTimeMillis() + "_" + fileName;
            filePart.write(new File(uploadPath, postFile).getPath());
        }

        String authorId = request.getParameter("author_id");
        String pass = request.getParameter("pass");
        String postTitle = request.getParameter("post_title");
        String postContent = request.getParameter("post_content");
        String parentPostId = request.getParameter("parent_post_id");

        System.out.println("authorId: " + authorId);
        System.out.println("pass: " + pass);
        System.out.println("postTitle: " + postTitle);
        System.out.println("postContent: " + postContent);
        System.out.println("postFile: " + postFile);
        System.out.println("parentPostId: " + parentPostId);

        if (authorId == null || authorId.trim().isEmpty() ||
            pass == null || pass.trim().isEmpty() ||
            postTitle == null || postTitle.trim().isEmpty()) {
            request.setAttribute("error", "글쓴이, 비밀번호, 제목은 필수입니다.");
            return "post/createPost";
        }

        String newPostId = generateNewPostId();
        System.out.println("생성된 postId: " + newPostId);

        Post post = new Post();
        post.setPostId(newPostId);
        post.setAuthorId(authorId);
        post.setPostPassword(pass);
        post.setPostTitle(postTitle);
        post.setPostContent(postContent);
        post.setPostFile(postFile);
        post.setPostCreatedAt(new Date());
        post.setPostReadCount(0);

        if (parentPostId != null && !parentPostId.trim().isEmpty()) {
            Post parentPost = dao.selectOne(parentPostId);
            if (parentPost != null) {
                dao.updateGroupStep(parentPost.getPostGroup(), parentPost.getPostGroupStep());
                post.setPostGroup(parentPost.getPostGroup());
                post.setPostGroupLevel(parentPost.getPostGroupLevel() + 1);
                post.setPostGroupStep(parentPost.getPostGroupStep() + 1);
            } else {
                post.setPostGroup(getNextGroupId());
                post.setPostGroupLevel(0);
                post.setPostGroupStep(0);
            }
        } else {
            post.setPostGroup(getNextGroupId());
            post.setPostGroupLevel(0);
            post.setPostGroupStep(0);
        }

        try {
            System.out.println("게시물 등록 시도: postId=" + post.getPostId());
            dao.insert(post);
            System.out.println("게시물 등록 성공: postId=" + post.getPostId());
            return "redirect:/LMSProject1/post/getPosts";
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("게시물 등록 실패: " + e.getMessage());
            request.setAttribute("error", "게시물 등록 실패: " + e.getMessage());
            return "post/createPost";
        }
    }

    private synchronized String generateNewPostId() {
        String maxPostId = dao.getMaxPostId();
        if (maxPostId == null || maxPostId.isEmpty()) {
            return "PO001";
        }

        try {
            String numberPart = maxPostId.substring(2);
            int number = Integer.parseInt(numberPart);
            number++;
            return "PO" + String.format("%03d", number);
        } catch (Exception e) {
            System.err.println("post_id 생성 실패: " + e.getMessage());
            return "PO001";
        }
    }

    private synchronized int getNextGroupId() {
        Integer maxGroup = dao.getMaxGroup();
        if (maxGroup == null) {
            return 1;
        }
        return maxGroup + 1;
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
            post.setPostReadCount(post.getPostReadCount() + 1);
        }
        request.setAttribute("post", post);
        return "post/getPostDetail";
    }
    @RequestMapping("updatePost")
    public String updatePost(HttpServletRequest request, HttpServletResponse response) {
        String postId = request.getParameter("postId");
        System.out.println("updatePost called with postId: " + postId); // 디버깅 로그
        if (postId == null || postId.trim().isEmpty()) {
            request.setAttribute("error", "게시물 ID가 필요합니다.");
            return "post/getPosts"; // 에러 페이지로 리다이렉트
        }
        Post p = dao.selectOne(postId);
        if (p == null) {
            System.out.println("Post not found for postId: " + postId); // 디버깅 로그
            request.setAttribute("error", "게시물을 찾을 수 없습니다.");
            return "post/getPosts";
        }
        request.setAttribute("p", p); 
        return "post/updatePost"; 
    }
    @RequestMapping("update")
    public String update(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String postId = request.getParameter("postId");
        String postPassword = request.getParameter("postPassword");
        String authorId = request.getParameter("authorId");
        String postTitle = request.getParameter("postTitle");
        String postContent = request.getParameter("postContent");
        String originalFile = request.getParameter("postFile"); // 기존 파일
        Part filePart = request.getPart("postFile"); // 새 파일

        if (postId == null || postId.trim().isEmpty() || postPassword == null || postPassword.trim().isEmpty() ||
            authorId == null || authorId.trim().isEmpty() || postTitle == null || postTitle.trim().isEmpty()) {
            request.setAttribute("error", "필수 입력값이 누락되었습니다.");
            request.setAttribute("b", dao.selectOne(postId)); // 에러 시 원본 데이터 유지
            return "post/updatePost";
        }

        Post post = dao.selectOne(postId);
        if (post == null) {
            request.setAttribute("error", "게시물을 찾을 수 없습니다.");
            return "post/getPosts";
        }

        if (!post.getPostPassword().equals(postPassword)) {
            request.setAttribute("error", "비밀번호가 일치하지 않습니다.");
            request.setAttribute("p", post);
            return "post/updatePost";
        }

        String newFile = originalFile;
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = filePart.getSubmittedFileName();
            String uploadPath = request.getServletContext().getRealPath("/upload/board");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();
            newFile = System.currentTimeMillis() + "_" + fileName;
            filePart.write(new File(uploadPath, newFile).getPath());

            // 기존 파일 삭제
            if (originalFile != null && !originalFile.isEmpty()) {
                File oldFile = new File(uploadPath, originalFile);
                if (oldFile.exists()) {
                    oldFile.delete();
                }
            }
        }

        post.setAuthorId(authorId);
        post.setPostPassword(postPassword);
        post.setPostTitle(postTitle);
        post.setPostContent(postContent);
        post.setPostFile(newFile);
        post.setPostCreatedAt(new Date()); // 수정 시 생성일 갱신 여부는 요구사항에 따라 조정

        try {
            dao.update(post); 
            return "redirect:/LMSProject1/post/getPosts";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "게시물 수정 실패: " + e.getMessage());
            request.setAttribute("p", post);
            return "post/updatePost";
        }
    }

    @RequestMapping("delete")
    public String delete(HttpServletRequest request, HttpServletResponse response) {
        String postId = request.getParameter("postId");
        String pass = request.getParameter("pass");

        if (postId == null || postId.trim().isEmpty() || pass == null || pass.trim().isEmpty()) {
            request.setAttribute("error", "게시물 ID와 비밀번호가 필요합니다.");
            return "post/delete";
        }

        Post post = dao.selectOne(postId);
        if (post == null) {
            request.setAttribute("error", "게시물을 찾을 수 없습니다.");
            return "post/delete";
        }

        if (!post.getPostPassword().equals(pass)) {
            request.setAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "post/delete";
        }

        try {
            if (post.getPostFile() != null && !post.getPostFile().isEmpty()) {
                String uploadPath = request.getServletContext().getRealPath("/upload/board");
                File file = new File(uploadPath, post.getPostFile());
                if (file.exists()) {
                    file.delete();
                }
            }

            dao.delete(postId);
            return "redirect:/LMSProject1/post/getPosts";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "게시물 삭제 실패: " + e.getMessage());
            return "post/delete";
        }
    }

    @RequestMapping("uploadImage")
    public void uploadImage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Part filePart = request.getPart("file");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = filePart.getSubmittedFileName();
            String uploadPath = request.getServletContext().getRealPath("/upload/board");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();
            String newFileName = System.currentTimeMillis() + "_" + fileName;
            filePart.write(new File(uploadPath, newFileName).getPath());
            String fileUrl = request.getContextPath() + "/upload/board/" + newFileName;
            response.getWriter().write(fileUrl);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("파일 업로드 실패");
        }
    }
}