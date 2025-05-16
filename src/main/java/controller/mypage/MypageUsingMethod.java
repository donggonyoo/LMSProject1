package controller.mypage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import model.dao.mypage.ProfessorDao;
import model.dao.mypage.StudentDao;

public class MypageUsingMethod {

	public static String IdChk(String a) { 
		String num = null;
		if(a.equals("pro")) {
			num = createProfessorId();	
		}
		else if(a.equals("stu")) {
			num = createStudentId();
		}

		return num;
	}
	//임시비밀번호를 만드는 알고리즘(비밀번호찾기 시에만 발급이 될것임)
		public static String getTempPw() {
			List<String> list = Arrays.asList
					("a" ,"b" ,"c" ,"d" ," e" ,"f" ,"g" ,"h" ,"i" ,"j" ,"k" ,"l" ,"m" ,"n" ,"o" ,"p","q","r","s","t" );
					
					List<String> list2 = new ArrayList<>();
					for (String string : list) {
						list2.add(string.toUpperCase());
					}	
					
					
					List<Object> combineList = new ArrayList<>();
					combineList.addAll(list);
					combineList.addAll(list2);
					for (int i = 0; i < 15; i++) { //랜덤한0~9 숫자 10개집어넣기
						 combineList.add(new Random().nextInt(10)); 
					}
					Collections.shuffle(combineList);
					String tempNum = "";
					for (int i = 0; i < 6; i++) {
						int num = new Random().nextInt(combineList.size());
						tempNum += combineList.get(num);
					}
					System.out.println(tempNum);
					return tempNum;
				}
		
	
	//교수의아이디를 자동생성하는 메서드(p000)
	private static String createProfessorId() {
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
	private static String createStudentId() {
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
}
