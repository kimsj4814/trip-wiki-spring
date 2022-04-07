package com.trip.tripwiki.task;




import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import com.trip.tripwiki.domain.MailVo;







@Component
public class SendMail {
	
	@Autowired
	private JavaMailSenderImpl mailSender;

	

	
	private static final Logger logger = LoggerFactory.getLogger(SendMail.class);
	
	public void sendMail(MailVo vo) {
		
		MimeMessagePreparator mp = new MimeMessagePreparator() {
			
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				helper.setFrom("blul56c@knou.co.kr");
				helper.setTo("wjrdjs005@naver.com");
				helper.setSubject("제목입니다.");		
				//1. 문자로만 전송하는 경우
				//두 번째 인자는 html을 사용하겠다는 뜻
				//helper.setText(vo.getContent(), true);
				
				//2. 이미지를 내장해서 보내는 경우
				//cid(content id)
				String content = "<img src='cid:Home'>" + vo.getContent();
				helper.setText("부제목입니다.", true);		
			}
		};
		mailSender.send(mp);	//메일 전송합니다.
		logger.info("메일 전송했습니다.");
	}
}

