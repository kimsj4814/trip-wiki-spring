package com.trip.tripwiki.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.trip.tripwiki.domain.Gallery;


@Repository
public class GalleryDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public void galleryInsert(Gallery gallery) {
		sqlSession.insert("Gallery.insert", gallery);
	}
}
