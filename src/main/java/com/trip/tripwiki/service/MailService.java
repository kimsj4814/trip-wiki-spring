package com.trip.tripwiki.service;



import com.trip.tripwiki.domain.User;

public interface MailService {

	public int register(User user) throws Exception;
	public int memberAuth(String mail,String authKey,String id) throws Exception;
}
