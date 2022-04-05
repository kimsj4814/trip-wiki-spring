package com.trip.tripwiki.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.trip.tripwiki.dao.UserDAO;
import com.trip.tripwiki.domain.User;

import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDAO dao;
	
	@Value("${my.savefolder}")
	private String uploadFolder;
	
	@Override
	public User isId(String id) {
		return dao.idcheck(id);
	}//isId String end;

	@Override
	public int isId(String id, String password) {
		// TODO Auto-generated method stub
		User user = dao.idcheck(id);
		if(user == null) {
			return -1;
		}else {
			if(user.getUser_password().equals(password)) {
				return 1;
			}else {
				return 0;
			}
		}
	}//isID end

	@Override
	public int add(User user) {
		user.setUser_profile_original("default.png");
		File file = new File(uploadFolder + File.separator + 
				user.getUser_profile_original());
		return dao.add(user);
	}//addUser end

	@Override
	public String isNickname(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String chkNickname(String id) {
		// TODO Auto-generated method stub
		return "";	
	}
	
}
