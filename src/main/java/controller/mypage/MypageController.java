package controller.mypage;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import domain.Professor;
import domain.Student;
import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import model.dao.mypage.DeptDao;
import model.dao.mypage.ProStuDao;
import model.dao.mypage.ProfessorDao;
import model.dao.mypage.StudentDao;
//http://localhost:8080/LMSProject1/dist/pages/mypage/registerUserChk
@WebServlet(urlPatterns = {"/mypage/*"},
initParams = {@WebInitParam(name="view", value="/dist/pages/")}
)
public class MypageController  extends MskimRequestMapping{
	
	
	
	public String IdChk(String a) { //아이디를 만들어줌 교수는 pxxxx , 학생은 sxxxx
		String num = null;
		if(a.equals("pro")) {
			num = createProfessorId();	
		}
		else if(a.equals("stu")) {
			num = createStudentId();
		}
		
		return num;
	
	}
	
	//교수의아이디를 자동생성하는 메서드(p000)
	private String createProfessorId() {
		int[] num = {0,1,2,3,4,5,6,7,8,9}; 
		
		String sNum="";
		for(int i=0;i<3;i++) {
			//0 ~ (num.length-1)의 랜덤한숫자반환
			int ranNum = new Random().nextInt(num.length);
			sNum+=num[ranNum]; //랜덤한 3개의숫자
		}
		ProfessorDao dao = new ProfessorDao();
		
		while(true) { 
			if(dao.idchk("p"+sNum)) { //true(id가존재하지않을 시 )면 루프탈출
				break;
			}
			else {
				int iNum = Integer.parseInt(sNum);//sNum을 Integer로형변환 
				iNum +=1; // 1 증가
				sNum = String.valueOf(iNum); // sNum으로 다시넣기
			}
		}
		//p0000 형식
		return "p"+sNum;
		
	}
	//학생의아이디를 자동생성하는 메서드(s00000)
	private String createStudentId() {
		int[] num = {0,1,2,3,4,5,6,7,8,9};
		String sNum="";
		
		for(int i=0;i<5;i++) {
			//0 ~ (num.length-1)의 랜덤한숫자반환
			int ranNum = new Random().nextInt(num.length);
			sNum+=num[ranNum]; //랜덤한 5개의숫자
		}
		StudentDao memberDao = new StudentDao();
		
		while(true) { 
			if(memberDao.idchk("s"+sNum)) { //true(id가존재하지않을 시 )면 루프탈출
				break;
			}
			else {
				int iNum = Integer.parseInt(sNum);//sNum을 Integer로형변환 
				iNum +=1; // 1 증가
				sNum = String.valueOf(iNum); // sNum으로 다시넣기
			}
		}
		
		return "s"+sNum;
	}
	
	
	
	@RequestMapping("registerUserChk")
	public String  registerUser(HttpServletRequest request , HttpServletResponse response) throws ParseException {
		
		String name  = request.getParameter("name");
		String date = request.getParameter("birth");
		String pass = request.getParameter("password");
		String hashpw = BCrypt.hashpw(pass, BCrypt.gensalt());//hashPassword : 암호화 (복호화는불가능)
		String position = request.getParameter("position");
		String img = request.getParameter("picture");
		String major = request.getParameter("major");
		String email = request.getParameter("email");
		String tel = request.getParameter("tel");
		String id = IdChk(position);//직급에따른 아이디부여해주는 메서드
		System.out.println("name:"+name);
		System.out.println("date : "+date);
		System.out.println("pass : "+pass);
		System.out.println("hashpw : "+hashpw);
		System.out.println("id : "+id);
		System.out.println("major : "+major);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    Date birthDate = sdf.parse(date); // "YYYY-MM-dd" 형식의 문자열을 Date로 파싱
	    
	    DeptDao deptDao = new DeptDao();//DeptDao를이용해 name을 넣어 id를 꺼내 저장
	    String deptId = deptDao.selectId(major);
	    
	    String msg = name+"님 회원가입성공 id = "+id;
	    String url = "doLogin";
	    
	  //객체에 값 넣어주는과정 (교수 , 학생 따로)
	    //직급 = 교수일경우
		if(id.contains("p")) {
			Professor pro = new Professor();
			pro.setProfessorId(id);
			pro.setProfessorImg(img);
			pro.setProfessorName(name);
			pro.setProfessorBirthday(birthDate);
			pro.setProfessorEmail(email);
			//pro.setProfessorPassword(hashpw);
			pro.setProfessorPassword(pass);
			pro.setProfessorMajor(deptId);
			pro.setProfessorPhone(tel);
			ProfessorDao pDao = new ProfessorDao();
			if(!pDao.insert(pro)) {
				msg = "회원가입실패";
				url = "registerUser";
			}	
		}
		
		//학생일경우
		else {
			Student stu = new Student();
		    stu.setStudentId(id);
		    stu.setStudentNum(id.substring(1));
		    stu.setDeptId(deptId);
		    stu.setStudentName(name);
		    stu.setStudentBirthday(birthDate);
		    stu.setStudentEmail(email);
		    stu.setStudentImg(img);
		    //stu.setStudentPassword(hashpw);
		    stu.setStudentPassword(pass);
		    stu.setStudentPhone(deptId);
		    stu.setStudentPhone(tel);
		    stu.setStudentStatus("재학");
		    
		    StudentDao sDao = new StudentDao();
		    if(!sDao.insert(stu)) {
		    	msg = "회원가입실패";
				url = "registerUser";
		    }  
		}
		
		
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return "alert";
	}
	
	

	//사진업로드관련
	@RequestMapping("picture")
	public String picture(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String path = request.getServletContext().getRealPath("")+"/dist/assets/picture/";
		//기준 디렉토리 의 실제 경로
		//D:\java\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\LMSProject1\picture
		String fname = null;
		File f = new File(path);//업로드되는 폴더정보
		if(!f.exists()){
			f.mkdirs(); //없으면 폴더생성
		}
		//request : 이미지데이터저장
		//path : 업로드되는 폴더정보
		//10*1024*1024 : 최대업로드크기(10M)
		//new DefaultFileRenamePolicy() : 중복파일명존재시 이름변경해

		MultipartRequest multi = new MultipartRequest(
					request,path,10*1024*1024,"UTF-8",new DefaultFileRenamePolicy());
		fname = multi.getFilesystemName("picture");//사진명
		request.setAttribute("fname", fname);
		return "mypage/picture";
	}
	
	@RequestMapping("doLogin") //로그인상태로접근불가능
	public String doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		String login = (String)session.getAttribute("login");
		if(login==null) {
			return "mypage/doLogin";
		}
		else {
			request.setAttribute("msg", "로그아웃을하세요");
			request.setAttribute("url","index");
			return "alert";
		}
		
	}
	
	@RequestMapping("login")
	public String login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(); 
		//session정보를 얻음(session영역 속성 등록을위해)

		String id = request.getParameter("id");
		String pass = request.getParameter("password");

		if(id==null || id.trim()=="" || pass==null || pass.trim()=="") {
			request.setAttribute("msg", "아이디or비번확인");
			request.setAttribute("url","doLogin");
			return "alert";
		}
		//student or professor의 id,pw,name을가지고있는 map
		Map<String,String> map = new ProStuDao().login(id); 
			
			if(map==null){
				request.setAttribute("msg", "아이디 확인하세요");
				request.setAttribute("url","doLogin");
			}

			else{
				String dbId="";
				String dbPw="";
				String dbName="";
				Set<Entry<String,String>> entrySet = map.entrySet();
				for (Entry<String, String> entry : entrySet) {
					if(entry.getKey().contains("id")) {
						dbId = entry.getValue();
					}
					else if(entry.getKey().contains("password")) {
						dbPw = entry.getValue();
					}
					else {
						dbName = entry.getValue();
					}
				} //dbId,dbPw,dbName 꺼내기종료
					
				
				if(pass.equals(dbPw)
					//BCrypt.checkpw(pass, pro.getProfessorPassword())
						){//로그인성공
					session.setAttribute("login", dbId);
					if(dbId.contains("s")) {
						StudentDao dao = new StudentDao();
						Student student = dao.selectOne(dbId);
						session.setAttribute("m", student);
					}
					else {
						ProfessorDao dao = new ProfessorDao();
						Professor professor = dao.selectOne(dbId);
						session.setAttribute("m", professor);
					}
					request.setAttribute("msg", dbName+"님이 로그인 하셨습니다");
					request.setAttribute("url","index");

				}
				else{
					request.setAttribute("msg", "비번을 확인하세요");
					request.setAttribute("url","doLogin");
				}
			}
			return "alert";
		
	}
	
	
	
	
	@RequestMapping("index") //왜이렇게해야지만 index로가는거지??????
	public String main(HttpServletRequest request , HttpServletResponse response) {

		String login = (String)request.getSession().getAttribute("login");
		if(login==null || login.trim().equals("")) {
			request.setAttribute("msg", "로그인하세요");
			request.setAttribute("url","doLogin");
			return "alert";
		}
		return "index"; //forward 됨
		//redirect시 다른 request영역이므로 속성이 넘어가지않음

	}
	
	@RequestMapping("logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		return "redirect:doLogin"; //redirect하도록 설정(속성초기화)
	}
	
	@RequestMapping("findIdProcess")
	public String findIdProcess(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		String email = request.getParameter("email");

		ProStuDao proStuDao = new ProStuDao();
		String id = proStuDao.findId(name, email);
		if(id==null) {
			request.setAttribute("msg", "입력된정보가없어요");			
			return "idSearch";
		}
		else {
			request.setAttribute("msg", "id : "+id); //추후에는 이메일로보내는거까지??
			request.setAttribute("id", id);
			return "idSearch";
		}
		
	}
	
	@RequestMapping("findPwProcess")
	public String findPwProcess(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String email = request.getParameter("email");
		String pw = new ProStuDao().findPw(id,email);
		if(pw==null) {
			request.setAttribute("msg", "입력된정보가없어요");			
			return "pwSearch";
		}
		else {
			request.setAttribute("msg", "비밀번호는"+pw+"입니다");
			request.setAttribute("id", id);
			request.setAttribute("pw", pw);
			//return "mypage/pwUpdate";
			return "mypage/alertPw";
		}
	}
	
	//pwUpdate에서 넘어오는곳(비밀번호처리만담당)
	@RequestMapping("pw")
	public String pw(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String cPw = request.getParameter("cPw");
		System.out.println(id);
		System.out.println(pw);
		System.out.println(cPw);
		request.setAttribute("msg", "비밀번호변경완료");
		return "mypage/close";
	}
	
	
}
