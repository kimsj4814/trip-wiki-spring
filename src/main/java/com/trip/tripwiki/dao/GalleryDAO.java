package com.trip.tripwiki.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.trip.tripwiki.domain.Gallery;


@Repository
public class GalleryDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public int getListCount() {
		return sqlSession.selectOne("Gallery.count");
	}
	
	public List<Gallery> mainGallery() {
		return sqlSession.selectList("Gallery.main");
	}
	
	public List<Gallery> getGalleryList(HashMap<String, Integer> map) {
		return sqlSession.selectList("Gallery.list", map);
	}
	
	public void galleryInsert(Gallery gallery) {
		sqlSession.insert("Gallery.insert", gallery);
	}
	
	public int galleryModify(Gallery modify) {
		return sqlSession.update("Gallery.modify", modify);
	}

	public Gallery getDetail(int num) {
		return sqlSession.selectOne("Gallery.detail", num);
	}

	public int galleryDelete(Gallery gallery) {
		return sqlSession.delete("Gallery.delete", gallery);
	}


}
