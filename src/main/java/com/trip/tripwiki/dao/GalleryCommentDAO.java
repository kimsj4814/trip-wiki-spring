package com.trip.tripwiki.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.trip.tripwiki.domain.GalleryComment;

@Repository
public class GalleryCommentDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public int getListCount(int gallery_id) {
		return sqlSession.selectOne("G_Comments.count", gallery_id);
	}
	
	public List<GalleryComment> getCommentList(Map<String, Integer> map) {
		return sqlSession.selectList("G_Comments.getList", map);
	}
	
	public int commentsInsert(GalleryComment c) {
		return sqlSession.insert("G_Comments.insert", c);
	}
	
	public int commentsUpdate(GalleryComment co) {
		return sqlSession.update("G_Comments.update", co);
	}
	
	public int commentsDelete(int num) {
		return sqlSession.delete("G_Comments.delete", num);
	}

}
