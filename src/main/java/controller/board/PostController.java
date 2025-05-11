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

@WebServlet(urlPatterns = {"/post/*"}, initParams = {@WebInitParam(name = "view",value = "/dist/pages/board/")})
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

        System.out.println("Controller - pageNum: " + pageNum + ", column: " + column + ", find: " + find);

        int boardcount = dao.boardCount(column, find);
        List<Post> list = dao.list(pageNum, limit, column, find);
        if (list == null) {
            System.out.println("Controller - list is null, initializing to empty list");
            list = new ArrayList<>();
        }
        System.out.println("Controller - boardcount: " + boardcount + ", list size: " + list.size());

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
    
   
}