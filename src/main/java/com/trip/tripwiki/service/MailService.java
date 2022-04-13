package com.trip.tripwiki.service;



import java.util.Map;

import com.trip.tripwiki.domain.User;

public interface MailService {

	public int register(User user) throws Exception;
	public int memberAuth(String mail,String authKey,String id) throws Exception;
	public Map<String, Object> sendMailChk(String id,String email);
	public String sendKeyPasswordToMail(String email,String message,String id);
	public int sendIdToMail(String email, String message);
	public User userGetKey(String secrectKey, String input_id);
}
