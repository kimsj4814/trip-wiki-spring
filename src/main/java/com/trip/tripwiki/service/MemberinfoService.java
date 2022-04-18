package com.trip.tripwiki.service;

import java.util.List;

import com.trip.tripwiki.domain.ComBoardList;
import com.trip.tripwiki.domain.User;

public interface MemberinfoService {

	User member_info(String id);

	List<ComBoardList> getBoard(String id ,int page, int limit);

	int getBoardcount(String id); 
}
