package controller.mypage;

public class AlgoTest {
	public static void main(String[] args) {
		
		for (int i = 0; i < 5; i++) {
			String tempPw = new MypageController().getTempPw();
			System.out.println(i+":"+tempPw);
		}
		
	}

}
