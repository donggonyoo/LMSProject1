package controller.board;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;

@WebServlet(urlPatterns = {"/post/uploadImage", "/notice/uploadImage"})
@MultipartConfig
public class UploadImageServlet extends MskimRequestMapping {
    private static final long serialVersionUID = 1L;
    private static final String UPLOAD_DIR = "upload/board";
    private static final String LOGIN_PAGE = "/LMSProject1/mypage/doLogin";

    @RequestMapping("uploadImage")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 로그인 체크
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("login");
        if (login == null) {
            session.setAttribute("error", "로그인하시오");
            response.sendRedirect(request.getContextPath() + LOGIN_PAGE);
            return;
        }

        // 업로드 경로 설정
        String uploadPath = request.getServletContext().getRealPath("/") + UPLOAD_DIR;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        int maxSize = 20 * 1024 * 1024; // 20MB
        MultipartRequest multi = new MultipartRequest(
            request,
            uploadPath,
            maxSize,
            "UTF-8",
            new DefaultFileRenamePolicy()
        );

        // 파일 처리
        String fileName = multi.getFilesystemName("file");
        if (fileName != null && !fileName.isEmpty()) {
            // 파일명에 타임스탬프 추가
            File originalFile = new File(uploadPath, fileName);
            String newFileName = System.currentTimeMillis() + "_" + fileName;
            File newFile = new File(uploadPath, newFileName);
            originalFile.renameTo(newFile);

            // 파일 URL 생성
            String fileUrl = request.getContextPath() + "/" + UPLOAD_DIR + "/" + newFileName;
            response.setContentType("text/plain; charset=UTF-8");
            response.getWriter().write(fileUrl);
        } else {
            response.setContentType("text/plain; charset=UTF-8");
            response.getWriter().write("파일 업로드 실패");
        }
    }
}
