package com.trip.tripwiki.service;

import java.util.List;
import com.trip.tripwiki.domain.Gallery;

public interface GalleryService {
	
	public List<Gallery> getBoardList(int page, int limit);
	
	public Gallery getDetail(int num);
	
	public int galleryModify(Gallery modifyboard);
	
	public int galleryDelete(int num);
	
	public void galleryInsert(Gallery board);
	
}
