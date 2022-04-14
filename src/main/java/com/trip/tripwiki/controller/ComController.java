package com.trip.tripwiki.controller;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.trip.tripwiki.domain.ComBoardList;
import com.trip.tripwiki.service.ComService;

@Controller
@CrossOrigin(origins="http://localhost:8081")
public class ComController {
	
	private static final Logger logger = LoggerFactory.getLogger(ComController.class);
	
	@Autowired
	private ComService comService;
	
	@Value("${my.savefolder}")
	private String saveFolder;
	
	@RequestMapping(value="/boards/new",method=RequestMethod.POST)
	@ResponseBody
	   //@RequestMapping(value="/add",method=RequestMethod.POST)
	   public String add(ComBoardList comBoard)
	         throws Exception {
	      
	      MultipartFile uploadfile = comBoard.getUploadfile();

	      if (uploadfile!=null && !uploadfile.isEmpty()) {
	         String fileName = uploadfile.getOriginalFilename();//원래 파일명
	         comBoard.setBOARD_ORIGINAL(fileName);// 원래 파일명 저장
	         

	         logger.info(saveFolder);
	         File file = new File(saveFolder);
	         if(!file.exists()) {
	        	 if(file.mkdir()) {
	        		 logger.info("폴더 생성");
	        	 }else {
	        		 logger.info("폴더 생성 실패");
	        	 }
	         }else {
	        	 logger.info("폴더 존재");
	         }
	         String fileDBName = fileDBName(fileName, saveFolder);
	         logger.info("fileDBName = " + fileDBName);
	         
	         //transferTo(File Path) : 업로드한 파일을 매개 변수의 경로에 저장합니다.
	         uploadfile.transferTo(new File(saveFolder + fileDBName));
	         //바뀐 파일명으로 저장
	         comBoard.setBOARD_FILE(fileDBName);
	      }
	      
	      comService.insertBoard(comBoard);//저장 메서드  호출
	      
	      return "success";

	   }
	
	private String fileDBName(String fileName, String saveFolder) {

	      Calendar c = Calendar.getInstance();
	      int year = c.get(Calendar.YEAR);
	      int month = c.get(Calendar.MONTH) + 1;
	      int date = c.get(Calendar.DATE); 

	      String homedir = saveFolder + File.separator + year + "-" + month + "-" + date;
	      logger.info(homedir);
	      File path1 = new File(homedir);
	      if (!(path1.exists())) {
	    	
	         path1.mkdir();
	      }

	      Random r = new Random();
	      int random = r.nextInt(100000000);

	      /**** 확장자 구하기 시작 ****/
	      int index = fileName.lastIndexOf(".");

	      logger.info("index = " + index);

	      String fileExtension = fileName.substring(index + 1);
	      logger.info("fileExtension = " + fileExtension);
	      /**** 확장자 구하기 끝 ***/

	      String refileName = "bbs" + year + month + date + random + "." + fileExtension;
	      logger.info("refileName = " + refileName);

	      String fileDBName = File.separator + year + "-" + month + "-" + date + File.separator + refileName;
	      logger.info("fileDBName = " + fileDBName);
	      return fileDBName;
	      
	   }
	
	@GetMapping(value="/boards")
	@ResponseBody
	public Map<String,Object> BoardList(
			@RequestParam(value="page", defaultValue = "1", required = false) int page,
			@RequestParam(value="limit", defaultValue = "3", required = false) int limit,
			@RequestParam(value="search_field", defaultValue = "-1") String index,
			@RequestParam(value="search_word", defaultValue = "") String search_word
			) {
		
		List<ComBoardList> list = null;
		int listcount = 0;
		
		listcount = comService.getSearchListCount(index, search_word);
		
		list = comService.getSearchList(index, search_word, page, limit);
		

		int maxpage = (listcount + limit - 1) /limit;
		
		int startpage = ((page - 1) / 10) *10 + 1;
		
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
		map.put("search_field", index);
		map.put("search_word", search_word);
		return map;
			
		
	}
	   

}
