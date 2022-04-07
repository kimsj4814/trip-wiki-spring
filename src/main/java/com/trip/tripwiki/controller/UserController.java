package com.trip.tripwiki.controller;



import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trip.tripwiki.domain.User;
import com.trip.tripwiki.service.UserService;

@RestController
public class UserController {
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	
	@PostMapping("/users")
	public int call(
			@RequestBody User user,HttpSession session) {

		int result = userService.isId(user.getUser_id(), user.getUser_password());
		if(result ==2) {
			return result;
		}
		if(result == 1) {
			session.setAttribute("id", user.getUser_id());
			session.setMaxInactiveInterval(3600);
		}
		return result;
	}//로그인 end
	
	@PostMapping("/users/add")
	public int addUsers(
			@RequestBody User user) {
		return userService.add(user);
	}// 회원가입 end
	
	@GetMapping("/users/idcheck")
	public boolean idcheck(
			@RequestParam(value="user_id",defaultValue = "",required = true) String id) {
		User users = userService.isId(id);
		boolean result = false;
		if(users == null) {
			logger.info("logger Map은 NULL이 아니다.");
			result =true;
		}else {
			result=false;
		}
		return result;
	}//id check end
	@GetMapping("/getSession")
	public String getSession(HttpSession session) {
		logger.info("[getSession]id" + (String) session.getAttribute("id"));
		return (String) session.getAttribute("id");
	}
	@PostMapping("/users/logout")
	public boolean logout(HttpSession session) {
		session.invalidate();
		return true;
	}
}
