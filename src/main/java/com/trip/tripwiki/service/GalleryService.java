package com.trip.tripwiki.service;

import java.util.List;
import com.trip.tripwiki.domain.Gallery;

public interface GalleryService {
	
	public int getListCount();

	public List<Gallery> mainGallery();
	
	public List<Gallery> getGalleryList(int page, int limit);
	
	public void galleryInsert(Gallery board);
	
	public Gallery getDetail(int num);
	
	public int galleryModify(Gallery modifyboard);
	
	public int galleryDelete(int num);

	public List<Gallery> myGallery(String user_id, int page, int limit);

	public int myListCount(String user_id);

}
