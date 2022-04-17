package com.trip.tripwiki.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.trip.tripwiki.domain.ComComment;

@Repository
public class ComCommentDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	public int getListcount(int board_num) {
		return sqlSession.selectOne("CommunityComments.count", board_num);
	}

	public List<ComComment> getCommentList(Map<String, Integer> map) {
		return sqlSession.selectList("CommunityComments.getList", map);
	}

	public int commentsInsert(ComComment c) {
		return sqlSession.insert("CommunityComments.insert", c);
	}

	public int commentsDelete(int num) {
		return sqlSession.delete("CommunityComments.delete", num);
	}

	public int commentsUpdate(ComComment co) {
		return sqlSession.update("CommunityComments.update", co);
	}

}
