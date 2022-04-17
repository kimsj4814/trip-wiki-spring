package com.trip.tripwiki.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.trip.tripwiki.domain.ComComment;
import com.trip.tripwiki.service.ComCommentService;


//@Controller
@CrossOrigin(origins = "http://localhost:8080")
@Controller
public class ComCommentController {
	
	@Autowired
	private ComCommentService comCommentService;
	
	/* @ResponseBody와 jackson을 이용하여 JSON 사용하기 */
	@ResponseBody
	@GetMapping(value = "/comcomments")
	public Map<String,Object> CommentList(@RequestParam int board_num,@RequestParam int page) {
		List<ComComment> list = comCommentService.getCommentList(board_num, page);
		int listcount = comCommentService.getListCount(board_num);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("list",list);
		map.put("listcount",listcount);
		return map;
	}
	
	
	@ResponseBody
	@PostMapping(value = "/comcomments/new")
	public int ComCommentAdd(@RequestBody ComComment co) {
	     return comCommentService.commentsInsert(co); 
	   }
	
	@ResponseBody
	@PatchMapping(value = "/comcopmments")
	public int ComCommentUpdate(@RequestBody ComComment co) {
	     return comCommentService.commentsUpdate(co); 
	   }
	
	@ResponseBody
	@DeleteMapping(value = "/comcomments/{num}")
	public int ComCommentDelete(@PathVariable int num) {
	     return comCommentService.commentsDelete(num); 
	   }
	
}


