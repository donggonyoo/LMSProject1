package controller.board;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import domain.Notice;
import domain.Professor;
import domain.Student;
import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import model.dao.board.NoticeDao;

@WebServlet(urlPatterns = {"/notice/*"}, initParams = {@WebInitParam(name="view", value="/dist/")})
public class NoticeController extends MskimRequestMapping {
    private NoticeDao dao = new NoticeDao();
    private static final String LOGIN_PAGE = "/LMSProject1/mypage/doLogin";

    // 로그인 체크 메서드
    private String checkLogin(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("login");
        if (login == null) {
            session.setAttribute("error", "로그인하시오");
            return "redirect:" + LOGIN_PAGE;
        }
        session.removeAttribute("error");
        return null;
    }

    @RequestMapping("getNotices")
    public String getNotices(HttpServletRequest request, HttpServletResponse response) {
        String loginCheck = checkLogin(request, response);
        if (loginCheck != null) return loginCheck;

        System.out.println("getNotices called with pageNum: " + request.getParameter("pageNum"));
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

        int boardcount = dao.boardCount(null, null);
        List<Notice> list = dao.list(pageNum, limit, null, null);
        if (list == null) {
            list = new ArrayList<>();
        }
        for (Notice notice : list) {
            System.out.println("getNotices - Notice ID: " + notice.getNoticeId() + ", WriterName: " + notice.getWriterName());
        }
        System.out.println("Board count: " + boardcount + ", List size: " + list.size());
        int maxpage = (int) Math.ceil((double) boardcount / limit);
        int startpage = ((int) (pageNum / 10.0 + 0.9) - 1) * 10 + 1;
        int endpage = startpage + 9;
        if (endpage > maxpage) endpage = maxpage;

        int boardNum = boardcount - (pageNum - 1) * limit;

        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("login");
        Object user = session.getAttribute("m");
        boolean isProfessor = (user instanceof Professor);

        request.setAttribute("login", login);
        request.setAttribute("isProfessor", isProfessor);
        request.setAttribute("boardcount", boardcount);
        request.setAttribute("pageNum", pageNum);
        request.setAttribute("list", list);
        request.setAttribute("startpage", startpage);
        request.setAttribute("endpage", endpage);
        request.setAttribute("maxpage", maxpage);
        request.setAttribute("boardNum", boardNum);
        request.setAttribute("today", new Date());

        return "/pages/board/notice/getNotices"; // 수정됨
    }

    @RequestMapping("searchNotice")
    public String searchNotice(HttpServletRequest request, HttpServletResponse response) {
        String loginCheck = checkLogin(request, response);
        if (loginCheck != null) return loginCheck;

        System.out.println("searchNotice called with pageNum: " + request.getParameter("pageNum"));
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

        System.out.println("Parameters - column: " + column + ", find: " + find);
        if (column == null || column.trim().isEmpty() || find == null || find.trim().isEmpty() ||
            !isValidColumn(column)) {
            column = null;
            find = null;
            request.setAttribute("error", "유효한 검색 조건과 검색어를 입력해주세요.");
        }

        int boardcount = dao.boardCount(column, find);
        List<Notice> list = dao.list(pageNum, limit, column, find);
        if (list == null) {
            list = new ArrayList<>();
        }
        for (Notice notice : list) {
            System.out.println("searchNotice - Notice ID: " + notice.getNoticeId() + ", WriterName: " + notice.getWriterName());
        }
        System.out.println("Board count: " + boardcount + ", List size: " + list.size());
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

        return "/pages/board/notice/searchNotice"; // 수정됨
    }

    private boolean isValidColumn(String column) {
        if (column == null) return false;
        String[] validColumns = {
            "writerName", "noticeTitle", "noticeContent",
            "noticeTitle,writerName", "noticeTitle,noticeContent",
            "writerName,noticeContent", "noticeTitle,writerName,noticeContent"
        };
        for (String valid : validColumns) {
            if (valid.equals(column)) return true;
        }
        return false;
    }

    @RequestMapping("createNotice")
    public String createNotice(HttpServletRequest request, HttpServletResponse response) {
        String loginCheck = checkLogin(request, response);
        if (loginCheck != null) return loginCheck;

        HttpSession session = request.getSession();
        Object user = session.getAttribute("m");
        if (user == null) {
            session.setAttribute("error", "사용자 정보가 없습니다. 다시 로그인해 주세요.");
            return "redirect:" + LOGIN_PAGE;
        }

        System.out.println("createNotice - User type: " + user.getClass().getName());
        String writerName = user instanceof Professor ? ((Professor) user).getProfessorName() :
                                    (user instanceof Student ? ((Student) user).getStudentName() : "Unknown");
        if (!(user instanceof Professor)) {
            session.setAttribute("error", "공지사항 작성은 교수만 가능합니다.");
            return "redirect:getNotices";
        }

        request.setAttribute("writerName", writerName);
        System.out.println("createNotice - Login: " + session.getAttribute("login") + ", WriterName: " + writerName);
        return "/pages/board/notice/createNotice";
    }

    @RequestMapping("write")
    public String write(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String loginCheck = checkLogin(request, response);
        if (loginCheck != null) return loginCheck;

        HttpSession session = request.getSession();
        Object user = session.getAttribute("m");
        String writerName = user != null ?
            (user instanceof Professor ? ((Professor) user).getProfessorName() : ((Student) user).getStudentName()) : "Unknown";
        System.out.println("write - Login: " + session.getAttribute("login") + ", WriterName: " + writerName);

        if (writerName == null || writerName.trim().isEmpty()) {
            session.setAttribute("error", "작성자 이름이 필요합니다.");
            return "redirect:createNotice";
        }

        String uploadPath = request.getServletContext().getRealPath("/upload/board");
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();
        int maxSize = 10 * 1024 * 1024; // 10MB
        MultipartRequest multi = new MultipartRequest(request, uploadPath, maxSize, "UTF-8");

        String noticeFile = multi.getFilesystemName("notice_file");
        if (noticeFile == null) {
            noticeFile = "";
        }

        String pass = multi.getParameter("pass");
        String noticeTitle = multi.getParameter("notice_title");
        String noticeContent = multi.getParameter("notice_content");

        System.out.println("pass: " + pass);
        System.out.println("notice_title: " + noticeTitle);
        System.out.println("notice_content: " + noticeContent);
        System.out.println("notice_file: " + noticeFile);

        String login = (String) session.getAttribute("login");
        if (login == null || login.trim().isEmpty() ||
                pass == null || pass.trim().isEmpty() ||
                noticeTitle == null || noticeTitle.trim().isEmpty()) {
            session.setAttribute("error", "글쓴이, 비밀번호, 제목은 필수입니다.");
            return "redirect:createNotice";
        }

        String newNoticeId = generateNewNoticeId();
        Notice notice = new Notice();
        notice.setNoticeId(newNoticeId);
        notice.setWriterId(login);
        notice.setWriterName(writerName); // 반드시 설정
        notice.setNoticePassword(pass);
        notice.setNoticeTitle(noticeTitle);
        notice.setNoticeContent(noticeContent);
        notice.setNoticeFile(noticeFile);
        notice.setNoticeCreatedAt(new Date());
        notice.setNoticeUpdatedAt(new Date());
        notice.setNoticeReadCount(0);

        try {
            dao.insert(notice);
            return "redirect:getNotices"; // 리다이렉트 경로는 변경하지 않아도 됨
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "게시물 등록 실패: " + e.getMessage());
            return "redirect:createNotice"; // 리다이렉트 경로는 변경하지 않아도 됨
        }
    }

    @RequestMapping("getNoticeDetail")
    public String getNoticeDetail(HttpServletRequest request, HttpServletResponse response) {
        String loginCheck = checkLogin(request, response);
        if (loginCheck != null) return loginCheck;

        String noticeId = request.getParameter("notice_id");
        String readcnt = request.getParameter("readcnt");
        System.out.println("getNoticeDetail called with notice_id: " + noticeId + ", readcnt: " + readcnt);

        HttpSession session = request.getSession();
        if (noticeId == null || noticeId.trim().isEmpty()) {
            session.setAttribute("error", "게시물 ID가 필요합니다.");
            return "redirect:getNotices";
        }

        try {
            Notice notice = dao.selectOne(noticeId);
            if (notice == null) {
                session.setAttribute("error", "게시물을 찾을 수 없습니다.");
                return "redirect:getNotices";
            }

            if (readcnt == null || !readcnt.trim().equalsIgnoreCase("f")) {
                dao.incrementReadCount(noticeId);
                notice = dao.selectOne(noticeId);
            }

            System.out.println("getNoticeDetail - Notice ID: " + notice.getNoticeId() + ", WriterName: " + notice.getWriterName());
            request.setAttribute("notice", notice);
            return "/pages/board/notice/getNoticeDetail"; // 수정됨
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "게시물 조회 실패: " + e.getMessage());
            return "redirect:getNotices";
        }
    }

    @RequestMapping("deleteNotice")
    public String deleteNotice(HttpServletRequest request, HttpServletResponse response) {
        String loginCheck = checkLogin(request, response);
        if (loginCheck != null) return loginCheck;

        String noticeId = request.getParameter("noticeId");
        System.out.println("deleteNotice called with noticeId: " + noticeId);

        HttpSession session = request.getSession();
        if (noticeId == null || noticeId.trim().isEmpty()) {
            session.setAttribute("error", "게시물 ID가 필요합니다.");
            return "redirect:getNotices";
        }

        try {
            Notice notice = dao.selectOne(noticeId);
            if (notice == null) {
                session.setAttribute("error", "삭제하려는 게시물이 존재하지 않습니다.");
                return "redirect:getNotices";
            }
            request.setAttribute("notice", notice);
            return "/pages/board/notice/deleteNotice"; // 수정됨
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "게시물 조회 실패: " + e.getMessage());
            return "redirect:getNotices";
        }
    }

    @RequestMapping("delete")
    public String delete(HttpServletRequest request, HttpServletResponse response) {
        String loginCheck = checkLogin(request, response);
        if (loginCheck != null) return loginCheck;

        String noticeId = request.getParameter("noticeId");
        String pass = request.getParameter("pass");
        System.out.println("delete called with noticeId: " + noticeId + ", pass: " + pass);

        HttpSession session = request.getSession();
        if (noticeId == null || noticeId.trim().isEmpty() ||
            pass == null || pass.trim().isEmpty()) {
            session.setAttribute("error", "게시물 ID와 비밀번호는 필수입니다.");
            return "redirect:deleteNotice?noticeId=" + (noticeId != null ? noticeId : "");
        }

        try {
            Notice notice = dao.selectOne(noticeId);
            if (notice == null) {
                session.setAttribute("error", "삭제하려는 게시물이 존재하지 않습니다.");
                return "redirect:deleteNotice?noticeId=" + noticeId;
            }

            if (!notice.getNoticePassword().equals(pass)) {
                session.setAttribute("error", "비밀번호가 일치하지 않습니다.");
                return "redirect:deleteNotice?noticeId=" + noticeId;
            }

            dao.delete(noticeId);
            return "redirect:getNotices"; // 리다이렉트 경로는 변경하지 않아도 됨
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "게시물 삭제 실패: " + e.getMessage());
            return "redirect:deleteNotice?noticeId=" + noticeId;
        }
    }

    private synchronized String generateNewNoticeId() {
        String maxNoticeId = dao.getMaxNoticeId();
        System.out.println("Max notice_id: " + maxNoticeId);
        if (maxNoticeId == null || maxNoticeId.isEmpty()) {
            System.out.println("Returning default notice_id: N001");
            return "N001";
        }
        try {
            String numberPart = maxNoticeId.substring(1);
            int number = Integer.parseInt(numberPart);
            number++;
            String newId = "N" + String.format("%03d", number);
            System.out.println("Generated notice_id: " + newId);
            return newId;
        } catch (Exception e) {
            System.err.println("Notice_id 생성 실패: " + e.getMessage());
            return "N001";
        }
    }

    @RequestMapping("uploadImage")
    public void uploadImage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String loginCheck = checkLogin(request, response);
        if (loginCheck != null) {
            response.sendRedirect("/LMSProject1/mypage/doLogin");
            return;
        }

        String uploadPath = request.getServletContext().getRealPath("/upload/board");
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();
        int maxSize = 10 * 1024 * 1024; // 10MB
        MultipartRequest multi = new MultipartRequest(request, uploadPath, maxSize, "UTF-8");

        String fileName = multi.getFilesystemName("file");
        if (fileName != null && !fileName.isEmpty()) {
            String newFileName = System.currentTimeMillis() + "_" + fileName;
            File oldFile = new File(uploadPath, fileName);
            if (oldFile.exists()) oldFile.renameTo(new File(uploadPath, newFileName));
            String fileUrl = request.getContextPath() + "/upload/board/" + newFileName;
            response.getWriter().write(fileUrl);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("파일 업로드 실패");
        }
    }

    @RequestMapping("updateNotice")
    public String updateNotice(HttpServletRequest request, HttpServletResponse response) {
        String loginCheck = checkLogin(request, response);
        if (loginCheck != null) return loginCheck;

        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("login");
        String noticeId = request.getParameter("noticeId");
        if (noticeId == null || noticeId.trim().isEmpty()) {
            session.setAttribute("error", "게시물 ID가 필요합니다.");
            return "redirect:getNotices";
        }
        Notice notice = dao.selectOne(noticeId);
        if (notice == null) {
            session.setAttribute("error", "게시물을 찾을 수 없습니다.");
            return "redirect:getNotices";
        }

        if (!notice.getWriterId().equals(login)) {
            session.setAttribute("error", "자신의 게시물만 수정할 수 있습니다.");
            return "redirect:getNotices";
        }

        Object user = session.getAttribute("m");
        String writerName = user != null ?
            (user instanceof Professor ? ((Professor) user).getProfessorName() : ((Student) user).getStudentName()) : "Unknown";
        request.setAttribute("writerName", writerName);
        System.out.println("updateNotice - Login: " + login + ", WriterName: " + writerName);
        request.setAttribute("notice", notice);
        return "/pages/board/notice/updateNotice"; // 수정됨
    }

    @RequestMapping("update")
    public String update(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String loginCheck = checkLogin(request, response);
        if (loginCheck != null) return loginCheck;

        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("login");
        Object user = session.getAttribute("m");
        String writerName = user != null ?
            (user instanceof Professor ? ((Professor) user).getProfessorName() : ((Student) user).getStudentName()) : "Unknown";
        System.out.println("update - Login: " + login + ", WriterName: " + writerName);

        String uploadPath = request.getServletContext().getRealPath("/upload/board");
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();
        int maxSize = 10 * 1024 * 1024; // 10MB
        MultipartRequest multi = new MultipartRequest(request, uploadPath, maxSize, "UTF-8");

        String noticeId = multi.getParameter("noticeId");
        String noticePassword = multi.getParameter("noticePassword");
        String noticeTitle = multi.getParameter("noticeTitle");
        String noticeContent = multi.getParameter("noticeContent");
        String originalFile = multi.getParameter("noticeFile");
        String newFile = multi.getFilesystemName("noticeFile");

        if (noticeId == null || noticeId.trim().isEmpty() || noticePassword == null || noticePassword.trim().isEmpty() ||
            noticeTitle == null || noticeTitle.trim().isEmpty()) {
            session.setAttribute("error", "필수 입력값이 누락되었습니다.");
            return "redirect:updateNotice?noticeId=" + noticeId;
        }

        Notice notice = dao.selectOne(noticeId);
        if (notice == null) {
            session.setAttribute("error", "게시물을 찾을 수 없습니다.");
            return "redirect:notice/getNotices";
        }

        if (!notice.getWriterId().equals(login)) {
            session.setAttribute("error", "자신의 게시물만 수정할 수 있습니다.");
            return "redirect:getNotices";
        }

        if (!notice.getNoticePassword().equals(noticePassword)) {
            session.setAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "redirect:updateNotice?noticeId=" + noticeId;
        }

        if (newFile == null || newFile.isEmpty()) {
            newFile = originalFile;
        } else {
            if (originalFile != null && !originalFile.isEmpty()) {
                File oldFile = new File(uploadPath, originalFile);
                if (oldFile.exists()) oldFile.delete();
            }
            File newFileObject = new File(uploadPath, newFile);
            newFile = System.currentTimeMillis() + "_" + newFile;
            newFileObject.renameTo(new File(uploadPath, newFile));
        }

        notice.setNoticeId(noticeId);
        notice.setWriterId(login);
        notice.setWriterName(writerName); // 업데이트 시도
        notice.setNoticePassword(noticePassword);
        notice.setNoticeTitle(noticeTitle);
        notice.setNoticeContent(noticeContent);
        notice.setNoticeFile(newFile);
        notice.setNoticeUpdatedAt(new Date());

        try {
            dao.update(notice);
            return "redirect:notice/getNotices";
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "게시물 수정 실패: " + e.getMessage());
            return "redirect:updateNotice?noticeId=" + noticeId;
        }
    }
}