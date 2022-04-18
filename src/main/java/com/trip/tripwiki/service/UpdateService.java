package com.trip.tripwiki.service;

import java.util.List;

import com.trip.tripwiki.domain.User;

public interface UpdateService {

	public int delete(String id);
	
	public int update(User m);
	
	public List<User> getSearchList(String index, String search_word, 
			                          int page, int limit);
	
	public int getSearchListCount(String index, String search_word);


}
