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
import domain.PostComment;
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

        if (column == null || column.trim().isEmpty() || find == null || find.trim().isEmpty()) {
            column = null;
            find = null;
        }

        // 공지 게시물 조회 (페이지네이션 없이)
        List<Post> notices = dao.listNotices();
        if (notices == null) {
            notices = new ArrayList<>();
        }

        // 일반 게시물 개수 조회 (공지 제외)
        int boardcount = dao.boardCount(column, find) - notices.size();
        List<Post> list = dao.list(pageNum, limit, column, find);
        if (list == null) {
            list = new ArrayList<>();
        }

        int maxpage = (int) Math.ceil((double) boardcount / limit);
        int startpage = ((int) (pageNum / 10.0 + 0.9) - 1) * 10 + 1;
        int endpage = startpage + 9;
        if (endpage > maxpage) endpage = maxpage;

        int boardNum = boardcount - (pageNum - 1) * limit;

        request.setAttribute("notices", notices); // 공지 게시물 전달
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
        String postNotice = request.getParameter("post_notice"); // post_notice 파라미터

        if (authorId == null || authorId.trim().isEmpty() ||
            pass == null || pass.trim().isEmpty() ||
            postTitle == null || postTitle.trim().isEmpty()) {
            request.setAttribute("error", "글쓴이, 비밀번호, 제목은 필수입니다.");
            return "post/createPost";
        }

        String newPostId = generateNewPostId();
        Post post = new Post();
        post.setPostId(newPostId);
        post.setAuthorId(authorId);
        post.setPostPassword(pass);
        post.setPostTitle(postTitle);
        post.setPostContent(postContent);
        post.setPostFile(postFile);
        post.setPostCreatedAt(new Date());
        post.setPostUpdatedAt(new Date());
        post.setPostReadCount(0);
        post.setPostNotice("1".equals(postNotice)); // 체크박스 값이 "1"이면 공지로 설정

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
            dao.insert(post);
            return "redirect:/LMSProject1/post/getPosts";
        } catch (Exception e) {
            e.printStackTrace();
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
            post.setPostReadCount(post.getPostReadCount() + 1);
        }

        List<PostComment> commentList = dao.selectCommentList(postId);
        request.setAttribute("post", post);
        request.setAttribute("commentList", commentList);
        return "post/getPostDetail";
    }

    @RequestMapping("replyPost")
    public String replyPost(HttpServletRequest request, HttpServletResponse response) {
        String postId = request.getParameter("postId");

        if (postId == null || postId.trim().isEmpty()) {
            request.setAttribute("error", "부모 게시물 ID가 필요합니다.");
            return "post/getPosts";
        }

        Post parentPost = dao.selectOne(postId);
        if (parentPost == null) {
            request.setAttribute("error", "부모 게시물을 찾을 수 없습니다.");
            return "post/getPosts";
        }

        request.setAttribute("board", parentPost);
        return "post/replyPost";
    }

    @RequestMapping("writeReply")
    public String writeReply(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String postId = request.getParameter("num");
        String writer = request.getParameter("writer");
        String pass = request.getParameter("pass");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        int grp = Integer.parseInt(request.getParameter("grp"));
        int grplevel = Integer.parseInt(request.getParameter("grplevel"));
        int grpstep = Integer.parseInt(request.getParameter("grpstep"));

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

        if (writer == null || writer.trim().isEmpty() || pass == null || pass.trim().isEmpty() || title == null || title.trim().isEmpty()) {
            request.setAttribute("error", "글쓴이, 비밀번호, 제목은 필수입니다.");
            request.setAttribute("board", dao.selectOne(postId));
            return "post/reply";
        }

        Post post = new Post();
        String newPostId = generateNewPostId();
        post.setPostId(newPostId);
        post.setAuthorId(writer);
        post.setPostPassword(pass);
        post.setPostTitle(title);
        post.setPostContent(content);
        post.setPostFile(postFile);
        post.setPostCreatedAt(new Date());
        post.setPostUpdatedAt(new Date());
        post.setPostReadCount(0);
        post.setPostGroup(grp);
        post.setPostGroupLevel(grplevel + 1);
        post.setPostGroupStep(grpstep + 1);

        try {
            dao.updateGroupStep(grp, grpstep);
            dao.insert(post);
            return "redirect:/LMSProject1/post/getPosts";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "답글 등록 실패: " + e.getMessage());
            request.setAttribute("board", dao.selectOne(postId));
            return "post/reply";
        }
    }

    @RequestMapping("writeComment")
    public String writeComment(HttpServletRequest request, HttpServletResponse response) {
        String postId = request.getParameter("postId");
        String writerId = request.getParameter("writerId");
        String commentContent = request.getParameter("commentContent");
        String parentCommentId = request.getParameter("parentCommentId");

        if (postId == null || postId.trim().isEmpty() || writerId == null || writerId.trim().isEmpty() || commentContent == null || commentContent.trim().isEmpty()) {
            request.setAttribute("error", "필수 입력값이 누락되었습니다.");
            return "redirect:/LMSProject1/post/getPostDetail?post_id=" + postId;
        }

        PostComment comment = new PostComment();
        comment.setCommentId(generateNewCommentId());
        comment.setPostId(postId);
        comment.setWriterId(writerId);
        comment.setCommentContent(commentContent);
        comment.setParentCommentId(parentCommentId);
        comment.setCreatedAt(new Date());
        comment.setUpdatedAt(new Date());

        try {
            dao.insertComment(comment);
            return "redirect:/LMSProject1/post/getPostDetail?post_id=" + postId;
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "댓글 등록 실패: " + e.getMessage());
            return "redirect:/LMSProject1/post/getPostDetail?post_id=" + postId;
        }
    }

    @RequestMapping("updatePost")
    public String updatePost(HttpServletRequest request, HttpServletResponse response) {
        String postId = request.getParameter("postId");
        if (postId == null || postId.trim().isEmpty()) {
            request.setAttribute("error", "게시물 ID가 필요합니다.");
            return "post/getPosts";
        }
        Post p = dao.selectOne(postId);
        if (p == null) {
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
        String originalFile = request.getParameter("postFile");
        Part filePart = request.getPart("postFile");
        String postNotice = request.getParameter("post_notice"); // post_notice 파라미터

        if (postId == null || postId.trim().isEmpty() || postPassword == null || postPassword.trim().isEmpty() ||
            authorId == null || authorId.trim().isEmpty() || postTitle == null || postTitle.trim().isEmpty()) {
            request.setAttribute("error", "필수 입력값이 누락되었습니다.");
            request.setAttribute("p", dao.selectOne(postId));
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
        post.setPostUpdatedAt(new Date());
        post.setPostNotice("1".equals(postNotice)); // 공지 여부 설정

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
    @RequestMapping("deletePost")
    public String deletePost(HttpServletRequest request, HttpServletResponse response) {
    	String postId = request.getParameter("postId");
    	System.out.println("deletePost called with postId: " + postId);

        if (postId == null || postId.trim().isEmpty()) {
            request.setAttribute("error", "게시물 ID가 필요합니다.");
            return "post/getPosts";
        }

        try {
            Post post = dao.selectOne(postId);
            if (post== null) {
                request.setAttribute("error", "삭제하려는 게시물이 존재하지 않습니다.");
                return "post/getPosts";
            }
            request.setAttribute("post", post);
            return "post/deletePost";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "게시물 조회 실패: " + e.getMessage());
            return "post/getPosts";
        }
    }
    @RequestMapping("delete")
    public String delete(HttpServletRequest request, HttpServletResponse response) {
        String postId = request.getParameter("postId");
        String pass = request.getParameter("pass");

        System.out.println("받은 postId: " + postId);
        System.out.println("받은 pass: " + pass);

        if (postId == null || postId.trim().isEmpty() || pass == null || pass.trim().isEmpty()) {
            System.out.println("에러: postId 또는 pass가 비어 있음");
            request.setAttribute("error", "게시물 ID와 비밀번호가 필요합니다.");
            return "post/delete";
        }

        Post post = dao.selectOne(postId);
        System.out.println("조회된 게시글: " + post);

        if (post == null) {
            System.out.println("에러: 게시물을 찾을 수 없음, postId: " + postId);
            request.setAttribute("error", "게시물을 찾을 수 없습니다.");
            return "post/delete";
        }

        if (!post.getPostPassword().equals(pass)) {
            System.out.println("저장된 비밀번호: " + post.getPostPassword());
            System.out.println("입력한 비밀번호: " + pass);
            System.out.println("에러: 비밀번호가 일치하지 않음");
            request.setAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "post/delete";
        }

        try {
            System.out.println("삭제 시작, postId: " + postId);
            if (post.getPostFile() != null && !post.getPostFile().isEmpty()) {
                String uploadPath = request.getServletContext().getRealPath("/upload/board");
                File file = new File(uploadPath, post.getPostFile());
                if (file.exists()) {
                    System.out.println("파일 삭제 시도: " + file.getPath());
                    file.delete();
                }
            }

            dao.delete(postId);
            System.out.println("삭제 성공, postId: " + postId);
            return "redirect:/LMSProject1/post/getPosts";
        } catch (Exception e) {
            System.out.println("삭제 실패, 에러 메시지: " + e.getMessage());
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

    private synchronized String generateNewCommentId() {
        String maxCommentId = dao.getMaxCommentId();
        if (maxCommentId == null || maxCommentId.isEmpty()) {
            return "CM001";
        }
        try {
            String numberPart = maxCommentId.substring(2);
            int number = Integer.parseInt(numberPart);
            number++;
            return "CM" + String.format("%03d", number);
        } catch (Exception e) {
            System.err.println("comment_id 생성 실패: " + e.getMessage());
            return "CM001";
        }
    }
}