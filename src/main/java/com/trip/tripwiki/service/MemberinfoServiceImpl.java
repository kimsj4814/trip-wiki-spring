package com.trip.tripwiki.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.tripwiki.dao.MyWriteDAO;
import com.trip.tripwiki.dao.UserDAO;
import com.trip.tripwiki.domain.ComBoardList;
import com.trip.tripwiki.domain.User;

@Service
public class MemberinfoServiceImpl implements MemberinfoService {
	
	@Autowired
	private UserDAO dao;
	
	@Autowired
	private MyWriteDAO dao1;
	
	@Override
	public User member_info(String id) {
		
		return dao.idcheck(id);
	}

	@Override
	public int getBoardcount(String id) {
		// TODO Auto-generated method stub
		return dao1.getSearchListCount(id);
	}

	@Override
	public List<ComBoardList> getBoard(String id , int page, int limit) {
		int startrow=(page-1)*limit+1;
		int endrow=startrow+limit-1;
		Map<String, Object>  map= new HashMap<String,Object>();
		map.put("board_name", id);
		map.put("start", startrow);
		map.put("end", endrow);
		return dao1.getSearchList(map);
	}
}
