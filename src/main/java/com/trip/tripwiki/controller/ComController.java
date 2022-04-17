package com.trip.tripwiki.controller;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.trip.tripwiki.domain.ComBoardList;
import com.trip.tripwiki.service.ComCommentService;
import com.trip.tripwiki.service.ComService;

@Controller
@CrossOrigin(origins="http://localhost:8081")
public class ComController {
	
	private static final Logger logger = LoggerFactory.getLogger(ComController.class);
	
	@Autowired
	private ComService comService;
	
	@Autowired
	private ComCommentService comCommentService;
	
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
	
	@GetMapping(value="/communitys")
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
	
	@GetMapping(value= {"/communitys/{num}"})
	@ResponseBody
	public Map<String, Object> Detail(@PathVariable int num){
		ComBoardList comBoard = comService.getDetail(num);
		comService.setReadCountUpdate(num);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("board", comBoard);
		return map;
	}
	
	@ResponseBody
	@GetMapping("/boards/down")
	public byte[] CommunityFileDown(String filename,String original,
								HttpServletResponse response) throws Exception {
		
		String sFilePath = saveFolder + filename;
		logger.info(sFilePath);
		File file = new File(sFilePath);
		byte[] bytes = FileCopyUtils.copyToByteArray(file);
		
		String sEncoding = new String(original.getBytes("utf-8"), "ISO-8859-1");
		response.setHeader("Content-Disposition", "attachment;filename=" + sEncoding);
		response.setContentLength(bytes.length);
		return bytes;
	}
	
	@PostMapping("/boards/reply/new")
	@ResponseBody
	public String BoardReplyAction(ComBoardList comBoard) {
		int result = comService.boardReply(comBoard);
		if (result == 0) {
			return "fail";
		}else {
			return "success";
		}
	}
	
	@GetMapping("/replyView")
	public ModelAndView BoardReplyView(int num,
			ModelAndView mv,
			HttpServletRequest request
			) {
		ComBoardList board = comService.getDetail(num);
		
		// 글 내용 불러오기 실패한 경우
		if (board == null) {
			
			mv.setViewName("error/error");
			mv.addObject("url", request.getRequestURL());
			mv.addObject("message", "게시판 답변글 가져오기 실패입니다.");
			
		}else {
		
		mv.addObject("boarddata", board);
		mv.setViewName("board/board_reply");
		}
		return mv;
	}
	
	
	@PatchMapping("/boards")
	@ResponseBody
	public String BoardModifyAction(
			ComBoardList boarddata,
			@RequestParam(value ="chheck", defaultValue="", required=false) String check
			) throws Exception {
		boolean usercheck =
				comService.isBoardWriter(boarddata.getBOARD_NUM(), boarddata.getBOARD_PASS());
		String message="";
		// 비밀번호가 다른경우
		if (usercheck == false) {
			return "Nopass";
		}
		
		MultipartFile uploadfile = boarddata.getUploadfile();

		
		if (check != null && !check.equals("")) {// 기존 파일 그대로 사용하는 경우입니다.
			logger.info("기존파일 그대로 사용합니다.");
			boarddata.setBOARD_ORIGINAL(check);
		}else {
			
			//답변글의 경우 파일 첨부에 대한 기능이 없다
			// 만약 답변글을 수정할 경우
			//<input type="file" id="upfile" name="uploadfile" > 엘리먼트가 존재하지 않아
			//private Multipartfile uploadfile; 에서 uploadfile은 null 입니다.
			if (uploadfile!=null && !uploadfile.isEmpty()) {
				logger.info("파일 변경되었습니다.");
				
				String fileName = uploadfile.getOriginalFilename(); //원래 파일명
				boarddata.setBOARD_ORIGINAL(fileName);
				
				String fileDBName = fileDBName(fileName, saveFolder);
				//transferTo(File path) : 업로드한 파일의 매개변수의 경로에 저장합니다.
				uploadfile.transferTo(new File(saveFolder + fileDBName));
				//바뀐 파일명으로 저장
				boarddata.setBOARD_FILE(fileDBName);
				
			}else {	//파일 선택하지 않은 경우
				logger.info("선택한 파일이 없습니다.");
				// <input type="hidden" name="BOARD_FILE" value="${boarddata.BOARD_FILE}">
				//위 태그에 값이 있다면 ""로 값을 변경합니다.
				boarddata.setBOARD_FILE(""); //""로 초기화 합니다.
				boarddata.setBOARD_ORIGINAL(""); //""로 초기화
 			}
		}
		
		// DAO에서 수정 메서드 호출하여 수정합니다.
		int result = comService.boardModify(boarddata);
		
		//수정에 실패한 경우
		if (result == 0) {
			message="fail";
		}else {
			logger.info("게시판 수정 완료");
			message="success";
		}
		return message;
	}
	
}
