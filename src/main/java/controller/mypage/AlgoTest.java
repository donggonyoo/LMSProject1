package controller.mypage;

import java.util.Arrays;
import java.util.List;

public class AlgoTest {
	public static void main(String[] args) {
		
		for (int i = 0; i < 5; i++) {
			String tempPw = new MypageController().getTempPw();
			System.out.println(i+":"+tempPw);
		}
		
		List<String> list = Arrays.asList("%","@","#","^","&","*","!");
		
		for (String string : list) {
			System.out.println(string.toUpperCase());
		}
		
		
		
	}

}
