package com.trip.tripwiki.service;

import java.util.HashMap;
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
	public int getListCount() {
		return dao.getListCount();
	}
	
	@Override
	public List<Gallery> mainGallery() {
		return dao.mainGallery();
	}
	
	@Override
	public List<Gallery> getGalleryList(int page, int limit) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		map.put("start", startrow);
		map.put("end", endrow);
		return dao.getGalleryList(map);
	}

	@Override
	public Gallery getDetail(int num) {
		return dao.getDetail(num);
	}
	
	@Override
	public void galleryInsert(Gallery gallery) {
		dao.galleryInsert(gallery);
	}

	@Override
	public int galleryModify(Gallery modify) {
		return dao.galleryModify(modify);
	}

	@Override
	public int galleryDelete(int num) {
		int result = 0;
		Gallery gallery = dao.getDetail(num);
		if (gallery != null) {
			result = dao.galleryDelete(gallery);
		}
		return result;
	}

}
