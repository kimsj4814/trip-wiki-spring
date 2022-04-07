package com.trip.tripwiki.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.tripwiki.dao.GalleryDAO;
import com.trip.tripwiki.domain.Gallery;

@Service
public class GalleryServiceImpl implements GalleryService {
	
	@Autowired
	private GalleryDAO dao;

	@Override
	public List<Gallery> getBoardList(int page, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Gallery getDetail(int num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int galleryModify(Gallery modifyboard) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int galleryDelete(int num) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void galleryInsert(Gallery gallery) {
		dao.galleryInsert(gallery);
		
	}

}
