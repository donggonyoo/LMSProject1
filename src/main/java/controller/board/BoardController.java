//package controller.board;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Date;
//import java.util.List;
//
//import javax.servlet.annotation.WebInitParam;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.oreilly.servlet.MultipartRequest;
//
//import domain.Board;
//import gdu.mskim.MSLogin;
//import gdu.mskim.MskimRequestMapping;
//import gdu.mskim.RequestMapping;
//import model.comment.CommentDao;
//import model.dao.board.BoardDao;
//
//@WebServlet(urlPatterns = {"/board/*"},
//			initParams = {@WebInitParam(name="view", value="/view/")})
//public class BoardController extends MskimRequestMapping{
//	
//	private BoardDao dao = new BoardDao();
//	private CommentDao commDao = new CommentDao();
//	
//	@RequestMapping("write")
//	@MSLogin("noticeCheck")
//	public String write(HttpServletRequest request,HttpServletResponse response) {
//		
//		String path = request.getServletContext().getRealPath("/") + "/upload/board/";
//		File f = new File(path);
//		
//		// mkdir() : 한단계 폴더만 생성
//		// mkdirs() : 여러단계 폴더만 생성
//		if (!f.exists()) f.mkdirs();// 폴더 생성
//		
//		int size = 10*1024*1024;// 10M. 업로드파일 최대크기.
//		MultipartRequest multi = null;
//		String msg = "";
//		String url = "";
//		
//		try {
//			multi = new MultipartRequest(request, path, size, "utf-8");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		Board board = new Board();
//		board.setWriter(multi.getParameter("writer"));
//		board.setPass(multi.getParameter("pass"));
//		board.setTitle(multi.getParameter("title"));
//		board.setContent(multi.getParameter("content"));
//		board.setFile1(multi.getFilesystemName("file1"));//
//		
//		String boardid = (String) request.getSession().getAttribute("boardid");
//		if(boardid == null) {
//			boardid = "1";
//		}
//		
//		// 게시판 종류(boardid)
//		// 1: 공지사항. 
//		// 2: 자유게시판
//		board.setBoardid(boardid);
//		
//		if(board.getFile1() == null) {
//			board.setFile1("");// 업로드 파일이 없는 경우
//		}
//		
//		int num = dao.maxnum(); // 등록된 게시글의 최대 num값.
//		board.setNum(++num);// 게시글 키값. 게시글 번호
//		board.setGrp(num);// 그룹번호. 원글인 경우 그룹번호와 게시글번호가 같다.
//		
//		if(dao.insert(board)) { // 게시글 등록 성공.
//			return "redirect:list?boardid=" + boardid;
//		} else { // 등록 실패
//			msg = "게시물 등록 실패";
//			url = "writeForm";
//		}
//		
//		request.setAttribute("msg", msg);
//		request.setAttribute("url", url);
//		return "alert";
//	}
//	/**
//	 * 어노테이션 등록 함수.
//	 * @return 
//	 */
//	public String noticeCheck(HttpServletRequest request,HttpServletResponse response) {
//		
//		String boardid = (String)request.getSession().getAttribute("boardid");
//		
//		if (boardid == null || boardid.trim().equals("")) {
//			boardid = "1";
//		}
//		
//		String login = (String) request.getSession().getAttribute("login");
//		if (boardid.equals("1")) {
//			if (login == null || !login.equals("admin")) {
//				System.out.println("if문 탔는지?:Y");
//				request.setAttribute("msg", "공지사항은 관리는 관리자만 가능합니다.");
//				request.setAttribute("url", request.getContextPath() + "/board/list?boardid=" + boardid);
//				System.out.println("alert=========");
//				return"alert";
//			}
//		}
//		System.out.println("noticeCheck검증통과");
//		return null;
//	}
//		
//	
//	@RequestMapping("writeForm")
//	@MSLogin("noticeCheck")
//	public String writeForm(HttpServletRequest request,HttpServletResponse response) {
//		return "board/writeForm";
//	}
//	
//	/*
//	 * 1. 한페이지당 10건의 게시물 출력
//	 * 		pageNum 파라미터값 => 없는 경우 1로 설정.
//	 * 		boardid 파라미터값 => 있는 경우 session에 boardid값을 등록
//	 * 2. 최근 등록된 게시물이 가장 위쪽에 출력
//	 * 3. db에서 해당페이지에 출력될 내용만 조회하여 화면에 출력
//	 */
//	@RequestMapping("list")
//	public String list(HttpServletRequest request,HttpServletResponse response) {
//		
//		int pageNum = 1;
//
//		try {
//			pageNum = Integer.parseInt(request.getParameter("pageNum"));
//		} catch (NumberFormatException e) {}
//		
//		String boardid = request.getParameter("boardid");
//		
//		if(boardid == null || boardid.trim().equals("")) {
//			boardid = "1";
//		}
//		request.getSession().setAttribute("boardid", boardid);
//		
//		/*
//		 * 검색관련 파라미터 추가하기 : column, find
//		 */
//		String column = request.getParameter("column");
//		String find = request.getParameter("find");
//		/*
//		 * column, find 값은 두개가 동시에 존재해야함. 하나만 파라미터값으로 존재하면 
//		 * 두개의 파라미터가 없는 것으로 간주.
//		 */
//		if (column == null || column.trim().equals("") || 
//				find == null || find.trim().equals("")) {
//			
//			column = null;
//			find = null;
//		}
//		
//		int limit = 10;// 페이지당 출력되는 게시물의 건수
//		int boardcount = dao.boardCount(boardid, column, find);// 등록된 게시물 건수
//		// pageNum에 해당하는 게시물목록을 최대 10개를 db에서 가져오기
//		List<Board> list = dao.list(boardid,pageNum,limit, column, find);
//		
//		
//		/*
//		 * maxpage : 필요한 페이지의 갯수
//		 * 		게시물건수        maxpage
//		 * 			3				1
//	  			3.0/10 => 0.3 + 0.95 => (int)(1.25) => 1
//		 * 			10				1
//		 * 		10.0/10 => 1.0 + 0.95 => (int)1.95) =>1
//		 * 			11				2
//		 * 		11.0/10 => 1.1 + 0.95 => (int)(2.05) => 2
//		 */
//		int maxpage = (int) ((double)boardcount/limit + 0.95);
//		
//		/*	startpage : 시작페이지
//		 *  
//		 *  pageNum:현재페이지           페이지의 시작번호
//		 *  	1 							1
//		 *  	1/10.0 => 0.1+0.9=> (int)(1.0 - 1) * 1 0=> 0 + 1 => 1
//		 *  	10							1
//		 *  	11							11
//		 *  	11/10.0 => 1.1+0.9 => (int)(2.0 - 1) * 10 => 10 + 1 => 11
//		 *  	502							501
//		 *  	502/10.0 => 50.2 + 0.9 => (int)(51.1 - 1) * 10 => 500 + 1 => 501
//		 */
//		int startpage = ((int)(pageNum/10.0 + 0.9) - 1) * 10 + 1;
//		
//		// endpage : 화면에 출력한 마지막 페이지번호. 한 화면에 10개의 페이지번호 출력.
//		int endpage = startpage + 9;
//		
//		// ex: 총페이지수 3개일시 3페이지까지만 출력해야지 10까지 출력하지 않는다.
//		if(endpage > maxpage) {
//			endpage = maxpage;
//		}
//		
//		String boardName = "공지사항";
//		if(boardid.equals("2")) {
//			boardName = "자유게시판";
//		}
//		
//		int boardNum = boardcount - (pageNum - 1) * limit;
//		
//		request.setAttribute("boardName", boardName);
//		request.setAttribute("boardcount", boardcount);
//		request.setAttribute("boardid", boardid);
//		request.setAttribute("pageNum", pageNum);
//		request.setAttribute("list", list);
//		request.setAttribute("startpage", startpage);
//		request.setAttribute("endpage", endpage);
//		request.setAttribute("maxpage", maxpage);
//		request.setAttribute("boardNum", boardNum);
//		request.setAttribute("today", new Date());
//		request.setAttribute("column", column);
//		request.setAttribute("find", find);
//		return "board/list";
//		
//	}
//	
//	/*
//	 * 1. num 파라미터 저장
//	 * 2. num의 게시물을 db에서 조회
//	 * 		Board b = BoardDao.selectOne(num)
//	 * 3. 게시물의 조회수를 증가시키기
//	 * 		boardDao.readcntAdd(num)
//	 * 3. 조회된 게시물 화면에 전달
//	 */
//	@RequestMapping("info")
//	public String info(HttpServletRequest request,HttpServletResponse response) {
//		
//		int num = Integer.parseInt(request.getParameter("num"));
//		String readCnt = request.getParameter("readcnt");
//		
//		Board b = dao.selectOne(num);
//		// readCnt "f"인경우 조회수 증가안함
//		if (readCnt == null || !readCnt.trim().equals("f")) {
//			dao.readcntAdd(num);
//		}
//		
//		String boardid = b.getBoardid();
//		String boardName = "공지사항";
//		
//		if(boardid.equals("2")) {
//			boardName = "자유게시판";
//		}
//		// 댓글 목록 조회
//		List<Comment> commList = commDao.list(num);
//		
//		request.setAttribute("b", b);
//		request.setAttribute("boardName", boardName);
//		request.setAttribute("commList", commList);// 댓글 목록 view로 전달
//		
//		return "board/info";
//	}
//	
//	@RequestMapping("replyForm")
//	public String replyForm(HttpServletRequest request,HttpServletResponse response) {
//		
//		int num = Integer.parseInt(request.getParameter("num"));
//		Board b = dao.selectOne(num);
//		request.setAttribute("board", b);
//		
//		return "board/replyForm";
//	}
//	
//	/*
//	 * 1. 파라미터값을 Board 객체에 저장
//	 * 	원글정보 : num, grp, grplevel, grpstep, boardid
//	 * 	답글정보 : writer, pass, title, content => 입력한 내용
//	 * 
//	 * 2. 같은 grp에 속하는 게시물들의 grpstep 값을 1 증가시키기
//	 * 	void BoardDao.grpStepAdd(grp, grpstep)
//	 * 
//	 * 3. Board에 저장된 답글 정보를 db에 추가하기
//	 * 	num : maxnum + 1
//	 * 	grp : 원글과 동일
//	 * 	grplevel : 원글의 grplevel + 1
//	 * 	grpstep : 원글의 grpstep + 1
//	 * 	boardid : 원글과 동일
//	 * 
//	 * 4. 성공시 list로 이동
//	 *    실패시 replyForm 이동 
//	 */
//	@RequestMapping("reply")
//	public String reply(HttpServletRequest request,HttpServletResponse response) {
//		
//		int num = Integer.parseInt(request.getParameter("num"));
//		int grp = Integer.parseInt(request.getParameter("grp")) ;
//		int grplevel = Integer.parseInt(request.getParameter("grplevel"));
//		int grpstep = Integer.parseInt(request.getParameter("grpstep"));
//		String boardid = request.getParameter("boardid");
//		String writer = request.getParameter("writer");
//		String pass = request.getParameter("pass");
//		String title = request.getParameter("title");
//		String content = request.getParameter("content");
//		int maxNum = dao.maxnum();
//		
//		Board board = new Board();
//		board.setNum(maxNum+1);
//		board.setWriter(writer);
//		board.setPass(pass);
//		board.setTitle(title);
//		board.setContent(content);
//		board.setFile1("");
//		board.setGrp(grp);
//		board.setGrplevel(grplevel+1);
//		board.setGrpstep(grpstep+1);
//		board.setBoardid(boardid);
//		
//		
//		dao.grpStepAdd(grp, grpstep);
//		boolean result = dao.insert(board);
//		
//		if(result) {
//			return "redirect:list?boardid=" + boardid;
//		} else {
//			request.setAttribute("msg", "답변 등록 실패");
//			request.setAttribute("url", "replyForm?num=" + num);
//			
//			return "alert";
//		}
//	}
//	
//	@RequestMapping("updateForm")
//	@MSLogin("noticeCheck")
//	public String updateForm(HttpServletRequest request,HttpServletResponse response) {
//		int num = Integer.parseInt(request.getParameter("num"));
//		Board b = dao.selectOne(num);
//		request.setAttribute("b", b);
//		
//		return "board/updateForm";
//	}
//	
//	/*
//	 * 1. 파라미터 정보를 Board객체에 저장 => request 객체 사용 불가
//	 * 2. 비밀번호 검증 : 비밀번호 오류시, 메세지 출력 후 updateForm 이동
//	 * 3. 게시물 수정. boolean BoardDao.update(Board객체)
//	 * 	- 첨부파일이 없는 경우 file2의 내용을 다시 저장.
//	 * 4. 수정성공 : info 페이지로 이동
//	 * 	  수정실패 : 실패메세지 출력후 updateForm 이동  	
//	 */
//	@RequestMapping("update")
//	@MSLogin("noticeCheck")
//	public String update(HttpServletRequest request,HttpServletResponse response) {
//		String path=request.getServletContext().getRealPath("/") + "upload/board/";
//		File f = new File(path);
//		if(!f.exists()) f.mkdirs();
//		MultipartRequest multi = null;
//		
//		try {
//			multi = new MultipartRequest(request,path,10*1024*1024,"utf-8");
//		} catch (IOException e){
//			e.printStackTrace();
//		}
//		
//		int num = Integer.parseInt(multi.getParameter("num"));
//		String file1 = multi.getFilesystemName("file1");		
//		String file2 = multi.getParameter("file2");
//		String writer = multi.getParameter("writer");
//		String pass = multi.getParameter("pass");
//		String title = multi.getParameter("title");
//		String content = multi.getParameter("content");
//		
//		Board board = dao.selectOne(num);
//		
//		if (!pass.equals(board.getPass())) {
//			request.setAttribute("msg", "비밀번호 오류");
//			request.setAttribute("url", "updateForm?num=" + num);
//			return "alert";
//		} else {
//			if (file1 == null || file1.equals("")) {
//				board.setFile1(file2);
//			} else {
//				board.setFile1(file1);
//			}
//			
//			board.setNum(num);
//			board.setWriter(writer);
//			board.setTitle(title);
//			board.setContent(content);
//			
//			if (dao.update(board)) {
//				return "redirect:board/info?num=" + num;
//			} else {
//				return "board/updateForm?num=" + num;
//			}	
//		}
//	}
//	
//	/*
//	 * 1. num, pass 파라미터를 변수에저장
//	 * 2. 비밀번호 검증
//	 * 		틀린경우 : 메세지 출력, deleteForm 이동
//	 * 3. 답변글이 존재하는원글인경우 삭제 불가
//	 * 	  답변글 삭제후 삭제가능 메세지 출력. list페이지로 이동
//	 * 
//	 * 4. 게시물 삭제
//	 * 	boolean BoardDao.delete(num)
//	 * 	삭제 성공시 list로 이동
//	 * 	삭제 실패시 삭제실패 메세지 출력후. deleteForm 이동
//	 * 
//	 * 		
//	 */
//	@RequestMapping("delete")
//	@MSLogin("noticeCheck")
//	public String delete(HttpServletRequest request,HttpServletResponse response) {
//		System.out.println("deleteURL 호출==========");
//		int num = Integer.parseInt(request.getParameter("num"));
//		String pass = request.getParameter("pass");
//		Board board = dao.selectOne(num);
//		String boardId = board.getBoardid();
//		
//		if (!pass.equals(board.getPass())) {
//			request.setAttribute("msg", "비밀번호 불일치");
//			request.setAttribute("url", "list?boardid=" + boardId);
//			return "alert";
//		} else {
//			if (dao.checkReply(num)) {
//				request.setAttribute("msg", "답글이 존재하는 원글은 삭제불가. 답글 삭제처리후 삭제가능");
//				request.setAttribute("url", "list?boardid=" + boardId);
//				return "alert";
//			} else {
//				if (dao.delete(num)) {
//					return "redirect:list?boardid=" + boardId;
//				} else {
//					request.setAttribute("msg", "삭제 실패");
//					request.setAttribute("url", "deleteForm?num=" + num);
//					return "alert";
//				}
//			}
//		}
//	}
//	
//	@RequestMapping("comment")
//	public String comment(HttpServletRequest request,HttpServletResponse response) {
//		Comment comm = new Comment();
//		comm.setNum(Integer.parseInt(request.getParameter("num")));
//		comm.setWriter(request.getParameter("writer"));
//		comm.setContent(request.getParameter("content"));
//		
//		int seq = commDao.maxSeq(comm.getNum());
//		comm.setSeq(++seq);
//		
//		if (commDao.insert(comm)) {
//			return "redirect:info?num="+comm.getNum()+"&readcnt=f";
//		}
//		
//		request.setAttribute("msg", "다글 등록시 오류 발생");
//		request.setAttribute("url", "info?num="+comm.getNum()+"&readcnt=f");
//		
//		return "alert";
//	}
//	
//	/*  TODO
//	 	현재 입력시 비밀번호 입력을 안하기때문에 삭제시 검증이 없음.
//	 	-> 비밀번호 입력, 로그인 정보로 판단필요.
//	 */
//	@RequestMapping("commdel")
//	public String commdel(HttpServletRequest request,HttpServletResponse response) {
//		
//		int num = Integer.parseInt(request.getParameter("num"));
//		int seq = Integer.parseInt(request.getParameter("seq"));
//		String url = "info?num="+ num +"&readcnt=f";
//		
//		if (commDao.delete(num, seq)) {
//			return "redirect:" + url;
//		}
//		
//		request.setAttribute("msg", "답글 삭제시 오류발생");
//		request.setAttribute("url", url);
//		return "alert";
//	}
//	
//	
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
