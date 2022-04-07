package com.trip.tripwiki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trip.tripwiki.domain.User;
import com.trip.tripwiki.service.MailService;
import com.trip.tripwiki.service.UserService;

@RestController
public class MailContoller {

	@Autowired
	private MailService mailService;
	
	@Autowired
	private UserService userService;
	
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
	}//유저 메일authkey 생성 및 이메일 보내는 메서드
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
	}//유저 메인인증 받았다면 확인하는 메서드
}
