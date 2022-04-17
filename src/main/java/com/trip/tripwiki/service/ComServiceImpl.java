package com.trip.tripwiki.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.tripwiki.dao.ComDAO;
import com.trip.tripwiki.domain.ComBoardList;

@Service
public class ComServiceImpl implements ComService{
	
	private static final Logger logger = LoggerFactory.getLogger(ComServiceImpl.class);

	@Autowired
	private ComDAO dao;
	
	@Override
	public int getSearchListCount(String index, String search_word) {

		Map<String, Object> map = new HashMap<String, Object>();
		if(!index.equals("")) {
			String[] search_field = index.split("");
			logger.info("search_field.legnth:"+search_field.length);
			for(int i=0;i<search_field.length;i++) {
				logger.info(search_field[i]);
			}
			map.put("search_field", search_field);
			map.put("search_word", "%"+search_word+"%");
		}
		return dao.getSearchListCount(map);
	}

	@Override
	public List<ComBoardList> getSearchList(String index, String search_word, int page, int limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(!index.equals("")) {
			String[] search_field = index.split("");
			logger.info("search_field.legnth:"+search_field.length);
			for(int i=0;i<search_field.length;i++) {
				logger.info(search_field[i]);
			}
			map.put("search_field", search_field);
			map.put("search_word", "%"+search_word+"%");
		}
		int startrow=(page-1)*limit+1;
		int endrow=startrow+limit-1;
		map.put("start", startrow);
		map.put("end", endrow);
		return dao.getSearchList(map);
		
	}

	@Override
	public void insertBoard(ComBoardList comBoard) {
		dao.insertBoard(comBoard);
	}

	@Override
	public ComBoardList getDetail(int num) {
		return dao.getDetail(num);
	}

	@Override
	public int boardReply(ComBoardList comBoard) {
		comboardReplyUpdate(comBoard);
		comBoard.setBOARD_RE_LEV(comBoard.getBOARD_RE_LEV()+1);
		comBoard.setBOARD_RE_SEQ(comBoard.getBOARD_RE_SEQ()+1);
		return dao.boardReply(comBoard);
	}

	private int comboardReplyUpdate(ComBoardList comBoard) {
		return dao.boardReplyUpdate(comBoard);
		
	}

	@Override
	public boolean isBoardWriter(int num, String pass) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("num", num);
		map.put("pass", pass);
		ComBoardList result = dao.isBoardWriter(map);
		if(result==null)
			return false;
		else
			return true;
	}
	

	@Override
	public int boardModify(ComBoardList modifyboard) {
		return dao.boardModify(modifyboard);
	}

	@Override
	public int setReadCountUpdate(int num) {
		return dao.getReadCountUpdate(num);
	}



}
