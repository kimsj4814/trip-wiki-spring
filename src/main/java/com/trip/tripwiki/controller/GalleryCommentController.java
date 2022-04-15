package com.trip.tripwiki.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trip.tripwiki.domain.GalleryComment;
import com.trip.tripwiki.service.GalleryCommentService;



@Controller
public class GalleryCommentController {
	private Logger logger = LoggerFactory.getLogger(GalleryCommentController.class);
	
	@Autowired
	private GalleryCommentService commentService;
	
	@ResponseBody
	@GetMapping(value="/g_comments")
	public Map<String, Object> CommentList(@RequestParam int gallery_id, @RequestParam int page) {
		List<GalleryComment> list = commentService.getCommentList(gallery_id, page);
		int listcount = commentService.getListCount(gallery_id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("listcount", listcount);
		return map;
	}
	
	@ResponseBody
	@PostMapping(value="/g_comments/new")
	public int CommentAdd(@RequestBody GalleryComment co) {
		return commentService.commentsInsert(co);
	}
	
	@ResponseBody
	@DeleteMapping(value="/g_comments/{num}")
	public int CommentDelete(@PathVariable int num) {
		return commentService.commentsDelete(num);
	}
	
	@ResponseBody
	@PatchMapping(value="/g_comments")
	public int CommentUpdate(@RequestBody GalleryComment co) {
		return commentService.commentsUpdate(co);
	}

}
