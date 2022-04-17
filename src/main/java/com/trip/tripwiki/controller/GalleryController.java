package com.trip.tripwiki.controller;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.trip.tripwiki.domain.Gallery;
import com.trip.tripwiki.service.GalleryService;

@Controller
public class GalleryController {
	
	private Logger logger = LoggerFactory.getLogger(GalleryController.class);
	
	@Autowired
	private GalleryService galleryService;
	
	@Value("${my.savefolder}")
	private String saveFolder;
	
	// 갤러리 리스트 부분 가져오기
	@ResponseBody
	@GetMapping(value="/main/photos")
	public Map<String, Object> mainGallery() {
		List<Gallery> gallerylist = galleryService.mainGallery();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gallerylist", gallerylist);
		return map;
	}
	
	// 갤러리 전체 리스트
	@ResponseBody
	@GetMapping(value="/photos")
	public Map<String, Object> galleryListAjax(
			@RequestParam(value="page", defaultValue="1", required=false) int page,
			@RequestParam(value="limit", defaultValue="6", required=false) int limit) {
		
		int listcount = galleryService.getListCount(); // 총 리스트 수를 받아오기
		
		// 총 페이지 수
		int maxpage = (listcount + limit - 1) / limit;
		
		// 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21 ...)
		int startpage = ((page -1) / 10) * 10 + 1;
		
		// 현재 페이지에 보여줄 마지막 페이지 수 (10, 20, 30 ...)
		int endpage = startpage + 10 - 1;
		if (endpage > maxpage)
			endpage = maxpage;
		
		List<Gallery> gallerylist = galleryService.getGalleryList(page, limit); // 리스트 받아오기
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("maxpage", maxpage);
		map.put("startpage", startpage);
		map.put("endpage", endpage);
		map.put("listcount", listcount);
		map.put("gallerylist", gallerylist);
		map.put("limit", limit);
		return map;
	}
	
	// 포토갤러리 추가
	@ResponseBody
	@RequestMapping(value="/gallery/new", method=RequestMethod.POST)
	public String galleryAdd(Gallery gallery) throws Exception {
		MultipartFile uploadfile = gallery.getUploadfile();
		
		if (uploadfile != null && !uploadfile.isEmpty()) {
			String fileName = uploadfile.getOriginalFilename(); // 원래 파일명
			//board.setBOARD_ORIGINAL(fileName); // 원래 파일명 저장
			
			// c:/upload 생성
			logger.info(saveFolder);
			File file = new File(saveFolder);
			if (!file.exists()) {
				if (file.mkdir()) {
					logger.info("폴더 생성");
				} else {
					logger.info("폴더 생성 실패");
				}
			} else {
				logger.info("폴더 존재");
			}
			
			String fileDBName = fileDBName(fileName, saveFolder);
			logger.info("fileDBName = " + fileDBName);
			
			// transferTo(File path): 업로드한 파일을 매개변수 경로에 저장함.
			uploadfile.transferTo(new File(saveFolder + fileDBName));
			
			// 바뀐 파일명으로 저장
			gallery.setPhoto(fileDBName);
		}
		galleryService.galleryInsert(gallery); // 저장 메서드 호출
		return "success";	
	}
	
	// 포토갤러리 수정
	@PatchMapping("/gallery/update")
	@ResponseBody
	public String galleryModifyAction(Gallery gallerydata, 
			@RequestParam(value="check", defaultValue="", required=false) String check) throws Exception {
		String message = "";
		MultipartFile uploadfile = gallerydata.getUploadfile();
		
		if (check != null && !check.equals("")) { // 기존 파일을 그대로 사용하는 경우
			logger.info("기존 파일을 그대로 사용합니다." + check);
			gallerydata.setPhoto(check);
		} else {
			if (uploadfile != null && !uploadfile.isEmpty()) { // 파일을 변경한 경우
				logger.info("파일이 변경되었습니다.");
				
				String fileName = uploadfile.getOriginalFilename(); // 원래 파일명
				
				String fileDBName = fileDBName(fileName, saveFolder);
				
				// transferTo(File path)는 업로드한 파일을 매개변수의 경로에 저장함.
				uploadfile.transferTo(new File(saveFolder + fileDBName));
				
				// 바뀐 파일명으로 저장
				gallerydata.setPhoto(fileDBName);
			} else {
				logger.info("선택한 파일이 없습니다.");
				gallerydata.setPhoto(check);
			}
		}
		
		// DAO에서 수정 메서드를 호출하여 수정
		int result = galleryService.galleryModify(gallerydata);
		
		if (result == 0) { // 수정에 실패한 경우
			message = "fail";
		} else { // 수정에 성공한 경우
			logger.info("포토갤러리 수정 완료");
			message = "success";
		}
		
		return message;
	} // BoardModifyAction end
	
	// 포토갤러리 이미지 파일명 변경
	private String fileDBName(String fileName, String saveFolder) {
		// 새로운 폴더 이름 : 오늘 년+월+일
	    Calendar c = Calendar.getInstance();
	    int year = c.get(Calendar.YEAR);
	    int month = c.get(Calendar.MONTH) + 1;
	    int date = c.get(Calendar.DATE);
	    
	    String homedir = saveFolder + "/" + year + "-" + month + "-" + date;
	    logger.info(homedir);
	    
	    // c:/upload/년도-달-일 폴더
	    File path1 = new File(homedir);
	    if (!(path1.exists())) {  // homedir 경로의 폴더가 존재하는지 확인
	    	path1.mkdir(); // 새폴더를 생성
	    }

	    // 난수를 구하기
	    Random r = new Random();
	    int random = r.nextInt(100000000);

	    // 확장자 구하기
	    int index = fileName.lastIndexOf(".");
	    logger.info("index = " + index);
	    String fileExtension = fileName.substring(index + 1);
	    logger.info("fileExtension = " + fileExtension);

	    // 새로운 파일명
	    String refileName = "bbs" + year + month + date + random + "." + fileExtension;
	    logger.info("refileName = " + refileName);

	    // 오라클 DB에 저장될 파일명
	    String fileDBName = "/"+ year + "-" + month + "-" + date + "/" + refileName;
	    logger.info("fileDBName = " + fileDBName);
	      
	    return fileDBName;
	}
	
	// 포토갤러리 상세
	@GetMapping(value={"/gallery/{num}"})
	@ResponseBody
	public Map<String, Object> galleryDetail(@PathVariable int num) {
		Gallery gallery = galleryService.getDetail(num);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gallery", gallery);
		return map;
	}
	
	// 갤러리 사진 출력
	@GetMapping(value={"/gallery/display"})
	@ResponseBody
	public byte[] display(String filename, HttpServletResponse response) throws Exception {
		logger.info(filename);
		String sFilePath = saveFolder + filename;
		logger.info(sFilePath.toString());
		File file = new File(sFilePath);
		byte[] bytes = FileCopyUtils.copyToByteArray(file);
		return bytes;
	}
	
	@DeleteMapping(value={"/gallery/{num}"})
	@ResponseBody
	public String galleryDeleteAction(@PathVariable int num) {
		
		int result = galleryService.galleryDelete(num);
		
		// 삭제 처리 실패한 경우
		if (result == 0) {
			return "-1";
		}
		
		// 삭제 처리 성공한 경우, 글 목록 보기 요청 전송하기
		logger.info("게시판 삭제 성공");
		
		return "1";
	}
	
	// 사용자가 작성한 포토갤러리
	@ResponseBody
	@GetMapping(value="/mygallery")
	public Map<String, Object> myGallery(
			@RequestParam String user_id,
			@RequestParam(value="page", defaultValue="1", required=false) int page,
			@RequestParam(value="limit", defaultValue="10", required=false) int limit) {
		
		int listcount = 0;
		listcount = galleryService.myListCount(user_id); // 총 리스트 수를 받아오기
			
		// 총 페이지 수
		int maxpage = (listcount + limit - 1) / limit;
			
		// 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21 ...)
		int startpage = ((page - 1) / 10) * 10 + 1;
		
		// 현재 페이지에 보여줄 마지막 페이지 수 (10, 20, 30 ...)
		int endpage = startpage + 10 - 1;
		if (endpage > maxpage)
			endpage = maxpage;
			
		List<Gallery> gallerylist = galleryService.myGallery(user_id, page, limit); // 리스트 받아오기
			
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("maxpage", maxpage);
		map.put("startpage", startpage);
		map.put("endpage", endpage);
		map.put("listcount", listcount);
		map.put("gallerylist", gallerylist);
		map.put("limit", limit);
		return map;
	}
	
	
	
}
