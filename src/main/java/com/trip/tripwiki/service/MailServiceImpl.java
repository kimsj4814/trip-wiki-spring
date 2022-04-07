package com.trip.tripwiki.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trip.tripwiki.dao.UserDAO;
import com.trip.tripwiki.domain.User;
import com.trip.tripwiki.task.TempKey;

@Service
public class MailServiceImpl implements MailService {

	private static Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
	
	@Autowired
	private JavaMailSender mailSender;

	
	@Autowired
	private UserDAO dao;
	
	@Value("${spring.mail.username}")
	private String my_email;
	
	@Transactional
	@Override
	public int register(User user) throws Exception {
		String key = new TempKey().getKey(50,false);
		logger.info("authkey is = " +key);
		int result = dao.createAuthkey(user.getUser_id(), key);
		User sendUserData = dao.idcheck(user.getUser_id());
		MailUtils sendMail = new MailUtils(mailSender);
		sendMail.setSubject("[여행위키 사용자 인증 메일입니다.]");
		sendMail.setText("<h1>메일인증</h1>"
				+ "<br />여행위키에 회원가입해주셔서 감사합니다."
				+ "<br />아래 [이메일 인증 확인]을 눌러주세요."
				+ "<a href='http://localhost:8081/trip/mailAuth/"
				+ sendUserData.getUser_mail_authkey()
				+ "/"+sendUserData.getUser_email()+
				"/"+sendUserData.getUser_id() 
				+ "'>이메일 인증</a>");
		sendMail.setFrom(my_email, "[대표: 여행]");
		sendMail.setTo(user.getUser_email());
		sendMail.send();
		
		return result;
		
	}

	@Override
	public int memberAuth(String email,String authKey,String id) throws Exception {
		// TODO Auto-generated method stub
		return dao.userAuth(email, authKey,id);
	}

}
