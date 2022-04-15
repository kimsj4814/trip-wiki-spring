package com.trip.tripwiki.dao;


import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.trip.tripwiki.domain.ComBoardList;

@Repository
public class ComDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	public List<ComBoardList> getSearchList(Map<String, Object> map) {
		return sqlSession.selectList("Community.getSearchList", map);
	}

	public int getSearchListCount(Map<String, Object> map) {
		return sqlSession.selectOne("Community.searchCount", map);
	}

	public void insertBoard(ComBoardList comBoard) {
		sqlSession.insert("Community.insert", comBoard);
		
	}

}
