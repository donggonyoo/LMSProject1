package controller.mypage;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import model.dao.mypage.StudentDao;
//http://localhost:8080/LMSProject1/dist/pages/mypage/registerUserChk
@WebServlet(urlPatterns = {"/mypage/*"},
initParams = {@WebInitParam(name="view", value="/dist/pages/")}
)
public class MypageController  extends MskimRequestMapping{
	
	
	
	public String IdChk(String a) { //아이디를 만들어줌 교수는 pxxxx , 학생은 sxxxx
		int[] num = {1,2,3,4,5,6,7,8,9,10};
		String sNum="s";
		
		for(int i=0;i<5;i++) {
			//0 ~ (num.length-1)의 랜덤한숫자반환
			int ranNum = new Random().nextInt(num.length);
			sNum+=num[ranNum]; //랜덤한 5개의숫자
		}
		StudentDao memberDao = new StudentDao();
		
		while(true) { 
			if(memberDao.idchk(sNum)) { //true(id가존재하지않을 시 )면 루프탈출
				break;
			}
			else {
				int iNum = Integer.parseInt(sNum);//sNum을 Integer로형변환 
				iNum +=1; // 1 증가
				sNum = String.valueOf(iNum); // sNum으로 다시넣기
			}
		}
		return sNum;
	}
	
	
	
	@RequestMapping("registerUserChk")
	public String  registerUser(HttpServletRequest request , HttpServletResponse response) {
		
		String name  = request.getParameter("name");
		String birth = request.getParameter("birth");
		String pass = request.getParameter("password");
		String hashpw = BCrypt.hashpw(pass, BCrypt.gensalt());//hashPassword : 암호화 (복호화는불가능)
		String position = request.getParameter("position");
		String picture = request.getParameter("picture");
		String idChk = IdChk(position);
		
		System.err.println("pictureName : "+picture);
		System.out.println("id : "+idChk);
		System.err.println("학번 : "+idChk.substring(1)); //앞에영어를 제외한것이 학번
		System.out.println("pass : "+pass);
		System.out.println("hashpass : "+hashpw);
		System.out.println("birthday : "+birth);
		System.out.println("name : "+ name);
		return "mypage/doLogin";
	}
	
	
	
	@RequestMapping("picture")
	public String picture(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String path = request.getServletContext().getRealPath("")+"/picture/";
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
}
