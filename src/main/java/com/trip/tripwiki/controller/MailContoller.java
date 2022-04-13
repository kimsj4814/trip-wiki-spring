package com.trip.tripwiki.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParser;
import com.trip.tripwiki.domain.User;
import com.trip.tripwiki.service.MailService;
import com.trip.tripwiki.service.UserService;


@RestController
public class MailContoller {

	@Autowired
	private MailService mailService;
	
	
	@Autowired
	private UserService userService;
	
	//유저 메일authkey 생성 및 이메일 보내는 메서드
	@GetMapping("createMailAuth/{id}")
	public String createAuthkey(@PathVariable String id) {
		User user = userService.isId(id);
		int result = -1;
		try {
			result = mailService.register(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Integer.toString(result);
	}//createMailAuth end
	
	//유저 메인인증 받았다면 확인하는 메서드
	@PostMapping("createUserAuth/{mail}/{authKey}/{id}")
	public String createUserAuth(
			@PathVariable String mail,
			@PathVariable String authKey,
			@PathVariable String id) {
		int result = -1;
		try {
			result = mailService.memberAuth(mail,authKey,id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Integer.toString(result);
	}//createUserAuth() evue
	@PostMapping("emailChk")
	public Map<String, Object> emailChecker(
			@RequestBody User user) {
		return mailService.sendMailChk(user.getUser_id(), user.getUser_email());	
	}//emailChecker() end
	
	@PostMapping("keySender")
	public int madeKeySender(@RequestBody Map<String, Object> map) {
		int result = -1;
		String re = mailService.sendKeyPasswordToMail((String)map.get("email"),(String)map.get("message"),(String)map.get("id"));
		if(re != null) {
			result = 1;
		}
		return result;
	}//keySender
	@PostMapping("idFinder")
	public int idFinder(@RequestBody Map<String, Object> map) {
		
		return mailService.sendIdToMail((String)map.get("email"), (String)map.get("message"));
	}//idFinder
	@PostMapping("SelectToFinder/{secrectKey}/{id}")
	public Map<String, Object> userKeyFinder(
			@PathVariable String secrectKey,
			@PathVariable String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = -1;
		User user = mailService.userGetKey(secrectKey, id);
		if(user != null) {
			result =1;
		}
		map.put("result", result);
		map.put("id", user.getUser_id());
		return map;
	}
	
}
