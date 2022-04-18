package com.trip.tripwiki.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.tripwiki.controller.MemberinfoController;
import com.trip.tripwiki.dao.UpdateDAO;
import com.trip.tripwiki.domain.User;
import com.trip.tripwiki.service.UpdateService;

@Service
public class UpdateServiceImpl implements UpdateService {
	private static final Logger logger = LoggerFactory.getLogger(UpdateServiceImpl.class);
	@Autowired
	private UpdateDAO dao;
	
	@Override
	public int delete(String id) {
		return dao.delete(id);
		
	}

	@Override
	public int update(User u) {
		return dao.update(u);
	}

	@Override
	public List<User> getSearchList(String index, String search_word, int page, int limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(!index.equals("")) {
			String[] search_field = index.split("");
			logger.info("search_field.length:" +search_field.length);
			for(int i=0;i<search_field.length;i++) {
				logger.info(search_field[i]);
			}
			map.put("search_field", search_field);
			map.put("search_word", "%" + search_word + "%");
		}
		int startrow=(page-1)*limit+1;
		int endrow=startrow+limit-1;
		map.put("start", startrow);
		map.put("end", endrow);
		return dao.getSearchList(map);
	}

	@Override
	public int getSearchListCount(String index, String search_word) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(!index.equals("")) {
			String[] search_field = index.split("");
			logger.info("search_field.length:"+search_field.length);
			for(int i=0;i<search_field.length;i++) {
				logger.info(search_field[i]);
			}
			map.put("search_field", search_field);
			map.put("search_word","%" + search_word + "%");
		}
		return dao.getSearchListCount(map);
	}

}
