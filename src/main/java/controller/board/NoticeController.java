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

import domain.Notice;
import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import model.dao.board.NoticeDao;

@WebServlet(urlPatterns = {"/notice/*"}, initParams = {@WebInitParam(name="view", value="/dist/pages/board/")})
@MultipartConfig
public class NoticeController extends MskimRequestMapping {
    private NoticeDao dao = new NoticeDao();
    
    @RequestMapping("getNotices")
    public String getNotices(HttpServletRequest request, HttpServletResponse response) {
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
        String column = request.getParameter("column");
        String find = request.getParameter("find");
        
        System.out.println("Parameters - column: " + column + ", find: " + find);
        if (column == null || column.trim().isEmpty() || find == null || find.trim().isEmpty()) {
            column = null;
            find = null;
        }
        
        int boardcount = dao.boardCount(column, find);
        List<Notice> list = dao.list(pageNum, limit, column, find);
        if (list == null) {
            list = new ArrayList<>();
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
        
        return "notice/getNotices";
    }
    
    @RequestMapping("createNotice")
    public String createNotice(HttpServletRequest request, HttpServletResponse response) {
        return "notice/createNotice";
    }
    
    @RequestMapping("write")
    public String write(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("write method called");
        Part filePart = null;
        String noticeFile = null;
        try {
            filePart = request.getPart("notice_file");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = filePart.getSubmittedFileName();
                String uploadPath = request.getServletContext().getRealPath("/upload/board");
                System.out.println("Upload path: " + uploadPath);
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdirs();
                noticeFile = System.currentTimeMillis() + "_" + fileName;
                filePart.write(new File(uploadPath, noticeFile).getPath());
            }
        } catch (Exception e) {
            System.err.println("File upload error: " + e.getMessage());
            request.setAttribute("error", "파일 업로드 실패: " + e.getMessage());
            return "notice/createNotice";
        }
        
        String writerId = request.getParameter("writer_id");
        String pass = request.getParameter("pass");
        String noticeTitle = request.getParameter("notice_title");
        String noticeContent = request.getParameter("notice_content");
        
        System.out.println("writer_id: " + writerId);
        System.out.println("pass: " + pass);
        System.out.println("notice_title: " + noticeTitle);
        System.out.println("notice_content: " + noticeContent);
        System.out.println("notice_file: " + noticeFile);
        
        if (writerId == null || writerId.trim().isEmpty() ||
                pass == null || pass.trim().isEmpty() ||
                noticeTitle == null || noticeTitle.trim().isEmpty()) {
            request.setAttribute("error", "글쓴이, 비밀번호, 제목은 필수입니다.");
            return "notice/createNotice";
        }
        
        String newNoticeId = generateNewNoticeId();
        Notice notice = new Notice();
        notice.setNoticeId(newNoticeId);
        notice.setWriterId(writerId);
        notice.setNoticePassword(pass);
        notice.setNoticeTitle(noticeTitle);
        notice.setNoticeContent(noticeContent);
        notice.setNoticeFile(noticeFile);
        notice.setNoticeCreatedAt(new Date());
        notice.setNoticeUpdatedAt(new Date());
        notice.setNoticeReadCount(0);
        
        try {
            dao.insert(notice);
            return "redirect:/LMSProject1/notice/getNotices";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "게시물 등록 실패: " + e.getMessage());
            return "notice/createNotice";
        }
    }
    
    @RequestMapping("getNoticeDetail")
    public String getNoticeDetail(HttpServletRequest request, HttpServletResponse response) {
        String noticeId = request.getParameter("notice_id");
        String readcnt = request.getParameter("readcnt");
        System.out.println("getNoticeDetail called with notice_id: " + noticeId + ", readcnt: " + readcnt);

        if (noticeId == null || noticeId.trim().isEmpty()) {
            request.setAttribute("error", "게시물 ID가 필요합니다.");
            return "notice/getNotices";
        }
        
        try {
            Notice notice = dao.selectOne(noticeId);
            if (notice == null) {
                request.setAttribute("error", "게시물을 찾을 수 없습니다.");
                return "notice/getNotices";
            }
            
            if (readcnt == null || !readcnt.trim().equalsIgnoreCase("f")) {
                dao.incrementReadCount(noticeId);
                notice = dao.selectOne(noticeId); // 조회수 반영된 최신 데이터 가져오기
            }
            
            request.setAttribute("notice", notice);
            return "notice/getNoticeDetail";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "게시물 조회 실패: " + e.getMessage());
            return "notice/getNotices";
        }
    }
    
    @RequestMapping("deleteNotice")
    public String deleteNotice(HttpServletRequest request, HttpServletResponse response) {
        String noticeId = request.getParameter("noticeId");
        System.out.println("deleteNotice called with noticeId: " + noticeId);

        if (noticeId == null || noticeId.trim().isEmpty()) {
            request.setAttribute("error", "게시물 ID가 필요합니다.");
            return "notice/getNotices";
        }

        try {
            Notice notice = dao.selectOne(noticeId);
            if (notice == null) {
                request.setAttribute("error", "삭제하려는 게시물이 존재하지 않습니다.");
                return "notice/getNotices";
            }
            request.setAttribute("notice", notice);
            return "notice/deleteNotice";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "게시물 조회 실패: " + e.getMessage());
            return "notice/getNotices";
        }
    }
    
    @RequestMapping("delete")
    public String delete(HttpServletRequest request, HttpServletResponse response) {
        String noticeId = request.getParameter("noticeId");
        String pass = request.getParameter("pass");
        System.out.println("delete called with noticeId: " + noticeId + ", pass: " + pass);

        if (noticeId == null || noticeId.trim().isEmpty() || 
            pass == null || pass.trim().isEmpty()) {
            request.setAttribute("error", "게시물 ID와 비밀번호는 필수입니다.");
            return "notice/deleteNotice?noticeId=" + (noticeId != null ? noticeId : "");
        }

        try {
            Notice notice = dao.selectOne(noticeId);
            if (notice == null) {
                request.setAttribute("error", "삭제하려는 게시물이 존재하지 않습니다.");
                return "notice/deleteNotice?noticeId=" + noticeId;
            }
            
            if (!notice.getNoticePassword().equals(pass)) {
                request.setAttribute("error", "비밀번호가 일치하지 않습니다.");
                return "notice/deleteNotice?noticeId=" + noticeId;
            }

            dao.delete(noticeId);
            return "redirect:/LMSProject1/notice/getNotices";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "게시물 삭제 실패: " + e.getMessage());
            return "notice/deleteNotice?noticeId=" + noticeId;
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
            String numberPart = maxNoticeId.substring(1); // "N001" -> "001"
            int number = Integer.parseInt(numberPart);
            number++;
            String newId = "N" + String.format("%03d", number); // "N002"
            System.out.println("Generated notice_id: " + newId);
            return newId;
        } catch (Exception e) {
            System.err.println("Notice_id 생성 실패: " + e.getMessage());
            return "N001";
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
    
    @RequestMapping("updateNotice")
    public String updateNotice(HttpServletRequest request, HttpServletResponse response) {
        String noticeId = request.getParameter("noticeId");
        if (noticeId == null || noticeId.trim().isEmpty()) {
            request.setAttribute("error", "게시물 ID가 필요합니다.");
            return "notice/getNotices";
        }
        Notice notice = dao.selectOne(noticeId);
        if (notice == null) {
            request.setAttribute("error", "게시물을 찾을 수 없습니다.");
            return "notice/getNotices";
        }
        request.setAttribute("notice", notice);  
        return "notice/updateNotice";
    }
    
    @RequestMapping("update")
    public String update(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String noticeId = request.getParameter("noticeId");
        String noticePassword = request.getParameter("noticePassword");
        String writerId = request.getParameter("writerId");
        String noticeTitle = request.getParameter("noticeTitle");
        String noticeContent = request.getParameter("noticeContent");
        String originalFile = request.getParameter("noticeFile");
        Part filePart = request.getPart("noticeFile");
        
        if (noticeId == null || noticeId.trim().isEmpty() || noticePassword == null || noticePassword.trim().isEmpty() ||
            writerId == null || writerId.trim().isEmpty() || noticeTitle == null || noticeTitle.trim().isEmpty()) {
            request.setAttribute("error", "필수 입력값이 누락되었습니다.");
            request.setAttribute("notice", dao.selectOne(noticeId));
            return "notice/updateNotice";
        }
        
        Notice notice = dao.selectOne(noticeId);
        
        if (notice == null) {
            request.setAttribute("error", "게시물을 찾을 수 없습니다.");
            return "notice/getNotices";
        }
        
        if (!notice.getNoticePassword().equals(noticePassword)) {
            request.setAttribute("error", "비밀번호가 일치하지 않습니다.");
            request.setAttribute("notice", notice);
            return "notice/updateNotice";
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
        
        notice.setNoticeId(noticeId);
        notice.setNoticePassword(noticePassword);
        notice.setWriterId(writerId); 
        notice.setNoticeTitle(noticeTitle);
        notice.setNoticeContent(noticeContent);
        notice.setNoticeFile(newFile);
        notice.setNoticeUpdatedAt(new Date());
        
        try {
            dao.update(notice);
            return "redirect:/LMSProject1/notice/getNotices";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "게시물 수정 실패: " + e.getMessage());
            request.setAttribute("notice", notice);
            return "notice/updateNotice"; // "post/updatePost" → "notice/updateNotice" 수정
        }
    }
}