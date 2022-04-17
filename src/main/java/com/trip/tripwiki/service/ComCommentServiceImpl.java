package com.trip.tripwiki.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.tripwiki.dao.ComCommentDAO;
import com.trip.tripwiki.domain.ComComment;

@Service
public class ComCommentServiceImpl implements ComCommentService{

	@Autowired
	private ComCommentDAO dao;

	@Override
	public int getListCount(int board_num) {
		return dao.getListcount(board_num);
	}
	


	@Override
	public List<ComComment> getCommentList(int board_num, int page) {
		int startrow=1;
		int endrow=page*3;
	
		
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("board_num", board_num);
		map.put("start", startrow);
		map.put("end", endrow);
		return dao.getCommentList(map);
	}

	@Override
	public int commentsInsert(ComComment c) {
		return dao.commentsInsert(c);
	}

	@Override
	public int commentsDelete(int num) {
		return dao.commentsDelete(num);
	}

	@Override
	public int commentsUpdate(ComComment co) {
		return dao.commentsUpdate(co);
	}
	
	
}
