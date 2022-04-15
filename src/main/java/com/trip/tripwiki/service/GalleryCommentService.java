package com.trip.tripwiki.service;

import java.util.List;

import com.trip.tripwiki.domain.GalleryComment;

public interface GalleryCommentService {
	
	public int getListCount(int gallery_id);
	
	public List<GalleryComment> getCommentList(int gallery_id, int page);
	
	public int commentsInsert(GalleryComment c);
	
	public int commentsDelete(int num);
	
	public int commentsUpdate(GalleryComment co);

}
