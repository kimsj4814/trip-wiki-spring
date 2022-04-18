package com.trip.tripwiki.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.trip.tripwiki.domain.ComBoardList;
import com.trip.tripwiki.domain.Gallery;

@Repository
public class MyWriteDAO {

	@Autowired
	private SqlSessionTemplate sqlSession;

	public List<ComBoardList> getSearchList(Map<String, Object> map) {
		return sqlSession.selectList("Mycommunity.myboardlist", map);
	}

	public int getSearchListCount(String board_name) {
		return sqlSession.selectOne("Mycommunity.searchCount", board_name);
	}
	
	public int getListCount() {
		return sqlSession.selectOne("Gallery.count");
	}
	
	public List<Gallery> getGalleryList(HashMap<String, Integer> map) {
		return sqlSession.selectList("Gallery.list", map);
	}

	public List<ComBoardList> getBoard(String id) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("BOARD_NAME", id);
		return sqlSession.selectList("Mycommunity", map);
	}
}
