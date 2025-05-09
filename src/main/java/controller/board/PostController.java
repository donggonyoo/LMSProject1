package controller.board;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import model.dao.board.PostDao;

@WebServlet(urlPatterns = {"/post/*"}, initParams = {@WebInitParam(name = "view",value = "/dist/pages/board/")})
public class PostController extends MskimRequestMapping{
	private PostDao dao = new PostDao();
	
	@RequestMapping("getPost")
	public String getPost(HttpServletRequest request,HttpServletResponse response) {
		int pageNum = 1;
		try {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}catch (NumberFormatException e) {
		
		}
		return null;
	}
	
}
