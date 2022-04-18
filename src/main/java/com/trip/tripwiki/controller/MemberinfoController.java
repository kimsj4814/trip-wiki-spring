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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.trip.tripwiki.domain.ComBoardList;
import com.trip.tripwiki.domain.Gallery;
import com.trip.tripwiki.domain.User;
import com.trip.tripwiki.service.ComService;
import com.trip.tripwiki.service.GalleryService;
import com.trip.tripwiki.service.MemberinfoService;
import com.trip.tripwiki.service.UpdateService;


@RestController
public class MemberinfoController {
	private static final Logger logger = LoggerFactory.getLogger(MemberinfoController.class);
	//회원의 개인 정보
	@Autowired
	private MemberinfoService memberinfoservice;
	
	@Autowired
	private UpdateService updateservice;

	
		@RequestMapping(value = "/members/{id}", method = RequestMethod.GET)
		public User member_info(@PathVariable String id) {
			logger.info("id=" + id);
			return memberinfoservice.member_info(id);
		}
		
		//회원 관리 리스트
		@RequestMapping(value = "/users", method = RequestMethod.GET)
		public Map<String,Object> memberList(
				@RequestParam(value = "page", defaultValue = "1", required = false) int page,
				@RequestParam(value = "limit", defaultValue = "3", required = false) int limit,
				@RequestParam(value = "search_field", defaultValue = "") String index,
				@RequestParam(value = "search_word", defaultValue = "") String search_word) {
			
			List<User> list = null;
			int listcount = 0;
			
			list = updateservice.getSearchList(index, search_word, page, limit);
			listcount = updateservice.getSearchListCount(index, search_word); //총 리스트 수를 받아옴
			
			// 총 페이지 수
			int maxpage = (listcount + limit - 1) / limit;
			
			//현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21 등...)
			int startpage = ((page - 1) / 10) * 10 + 1;
			
			//현재 페이지에 보여줄 마지막 페이지 수(10, 20 , 30등...)
			int endpage = startpage +10 - 1;
			
			if (endpage > maxpage)
				endpage = maxpage;
			Map<String, Object>  map= new HashMap<String,Object>();
			map.put("page", page);
			map.put("maxpage", maxpage);
			map.put("startpage", startpage);
			map.put("endpage", endpage);
			map.put("listcount", listcount);
			map.put("memberlist", list);
			map.put("search_field", index);
			map.put("search_word", search_word);
			return map;
		}
		
		// 수정처리
		@PutMapping(value = "/users")
		public int updateProcess(@RequestBody User users) {
			return updateservice.update(users);
		}
		
		//삭제
		@DeleteMapping(value = "/users/{id}")
		public int member_delete(@PathVariable String id) {
			logger.info("id=" + id);
			return updateservice.delete(id);
		}
		
		@GetMapping(value = "/mycomm")
		public Map<String,Object> mycomm
		(	
				@RequestParam(value="id", required = true) String id,
				@RequestParam(value="page", defaultValue = "1", required = false) int page,
		@RequestParam(value="limit", defaultValue = "7", required = false) int limit
		) {
			logger.info("[myComm init] init myComm Controler");
	List<ComBoardList> list = null;
	int listcount = 0;
	
	listcount = memberinfoservice.getBoardcount(id);

	logger.info("user get board count listcount = " + listcount);

	int maxpage = (listcount + limit - 1) /limit;
	
	int startpage = ((page - 1) / 10) *10 + 1;
	
	list = memberinfoservice.getBoard(id, page, limit);
	int endpage = startpage + 10 -1;
	
	if (endpage > maxpage)
		endpage = maxpage;
	
		
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("page", page);
	map.put("maxpage", maxpage);
	map.put("startpage", startpage);
	map.put("endpage", endpage);
	map.put("listcount", listcount);
	map.put("boardlist", list);
	return map;
		
	
} 
		
		
}
