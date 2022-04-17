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

	public ComBoardList getDetail(int num) {
		return sqlSession.selectOne("Community.detail", num);
	}

	public int boardReply(ComBoardList comBoard) {
		return sqlSession.insert("Community.reply_insert", comBoard);
	}

	public int boardReplyUpdate(ComBoardList comBoard) {
		return sqlSession.update("Community.reply_update", comBoard);
	}

	public ComBoardList isBoardWriter(Map<String, Object> map) {
		return sqlSession.selectOne("Community.boardWriter", map);
	}

	public int boardModify(ComBoardList modifyboard) {
		return sqlSession.update("Community.modify", modifyboard);
	}
	
	public int getReadCountUpdate(int num) {
		return sqlSession.update("Community.readCountUpdate", num);
	}

}
