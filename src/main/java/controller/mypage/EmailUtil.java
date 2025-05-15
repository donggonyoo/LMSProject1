package controller.mypage;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {

	public static void sendIdEmail(String toEmail, String userName, String id) {
        String host = "smtp.gmail.com";
        String from = "ddkk8525@gmail.com"; // 실제 Gmail 주소
        String password = "muef nfld rbql bfez"; // Gmail 앱 비밀번호
        String tempNum="";
        for (int i = 0; i < 4; i++) {
        	 int num = new Random().nextInt(9)+1;
        	 tempNum += num;
		}
        
       

        // SMTP 서버 속성 설정
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.debug", "true"); // 디버깅 활성화

        // SSL/TLS 디버깅 (선택 사항)
        System.setProperty("javax.net.debug", "all");

        // 세션 생성
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("회원가입 ID 안내");
            message.setText(userName + "님, 회원가입이 완료되었습니다.\n\n발급된 ID: " + id + "\n\n로그인 페이지에서 사용하세요.\n ");
            

            Transport.send(message);
            System.out.println("이메일 전송 성공: " + toEmail);
           

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("이메일 전송 실패: " + e.getMessage());
        }
       
    }
	
	public static String sendNum(String toEmail, String userName, String id) {
        String host = "smtp.gmail.com";
        String from = "ddkk8525@gmail.com"; // 실제 Gmail 주소
        String password = "muef nfld rbql bfez"; // Gmail 앱 비밀번호
        String tempNum="";
        for (int i = 0; i < 4; i++) {
        	 int num = new Random().nextInt(9)+1;
        	 tempNum += num;
		}

        // SMTP 서버 속성 설정
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.debug", "true"); // 디버깅 활성화


        // 세션 생성
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("인증번호 안내");
            message.setText("인증번호 : "+tempNum);
            

            Transport.send(message);
            System.out.println("이메일 전송 성공: " + toEmail);
            return tempNum;
           

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("이메일 전송 실패: " + e.getMessage());
        }
        return null;
       
    }
}
