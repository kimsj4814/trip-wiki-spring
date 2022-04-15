package com.trip.tripwiki.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.tripwiki.dao.GalleryCommentDAO;
import com.trip.tripwiki.domain.GalleryComment;


@Service
public class GalleryCommentServiceImpl implements GalleryCommentService{
	
	@Autowired
	private GalleryCommentDAO dao;

	@Override
	public int getListCount(int board_num) {
		return dao.getListCount(board_num);
	}

	@Override
	public List<GalleryComment> getCommentList(int gallery_id, int page) {
		int startrow = 1;
		int endrow = page * 5;
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("gallery_id", gallery_id);
		map.put("start", startrow);
		map.put("end", endrow);
		return dao.getCommentList(map);
	}

	@Override
	public int commentsInsert(GalleryComment c) {
		return dao.commentsInsert(c);
	}

	@Override
	public int commentsUpdate(GalleryComment co) {
		return dao.commentsUpdate(co);
	}

	@Override
	public int commentsDelete(int num) {
		return dao.commentsDelete(num);
	}
	
}
