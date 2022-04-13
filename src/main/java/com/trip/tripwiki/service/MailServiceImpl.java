package com.trip.tripwiki.service;

import java.util.HashMap;
import java.util.List;
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


	@Override
	public Map<String, Object> sendMailChk(String id,String email) {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = dao.idcheck(id);
		//result = -1 이라면 아이디체크도 실패했을 때,
		int result = -1;
		if(user != null) {
			if(email.equals(user.getUser_email())) {
				//result가 2이면 체크가 될 때,
				result =2;
				logger.info("[info 성공 ] result 값은" + result);
			}else {
				//result가 1이면 id만 체크 되었을때
				result = 1;
			}
		}
		map.put("result", result);
		map.put("message", "비밀번호");
		return map;
	}

	@Override
	public String sendKeyPasswordToMail(String email, String message, String id) {
		String key = null;
		try {
			key = new TempKey().getKey(20, false);
			int result = dao.createAuthkey(id, key);
			MailUtils sendMail = new MailUtils(mailSender);
			sendMail.setSubject("[여행위키 "+message+" 인증 키 입니다.]");
			sendMail.setText("<h4> 인증 키는 "+key+" 입니다.</h4>"
					+ "<br/> 사이트로 돌아가 인증 키를 인증해 주세요.");
			sendMail.setFrom(my_email, "[대표: 여행]");
			sendMail.setTo(email);
			sendMail.send();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		return key;
	}
	@Override
	public int sendIdToMail(String email, String message) {
		//기본 result = -1
		int result = -1;
		try {
			List<User> list = dao.MailToEmailGroup(email);
			String[] temp_list =null;
			if(list != null) temp_list = new String[list.size()]; 
			else temp_list = new String[0];
			
			if(list.size() > 1) {
				for(int i = 0; i < list.size(); i++) {
					logger.info(list.get(i).getUser_id());
					temp_list[i] = list.get(i).getUser_id();
				}
			}
			String id_list=null;
			if(list.size() > 1) {
				logger.info("[info] Join이 반복되는 횟수를 확인");
				id_list= String.join(",", temp_list);
			}else {
				id_list = list.get(0).getUser_id();
			}
			
			MailUtils sendMail = new MailUtils(mailSender);
			sendMail.setSubject("[여행위키 에 가입하신 아이디 리스트입니다.]");
			sendMail.setText("<h4> 아이디 리스트 입니다.</h4>"
			+"<h3>해당 메일에 해당하는 아이디는 "+id_list+ "입니다.</h3>");
			sendMail.setTo(email);
			if(list != null) {
				result = 1;
				sendMail.send();		
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}


	@Override
	public User userGetKey(String secrectKey, String input_id) {
		return  dao.KeyselectorForUsers(secrectKey,input_id);
	}
	

}
