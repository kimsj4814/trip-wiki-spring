package com.trip.tripwiki.controller;

import java.io.File;
import java.util.Calendar;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	
	@RequestMapping(value="gallery/new", method=RequestMethod.POST)
	@ResponseBody
	public String add(Gallery gallery) throws Exception {
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
	
	private String fileDBName(String fileName, String saveFolder) {
		// 새로운 폴더 이름 : 오늘 년+월+일
	    Calendar c = Calendar.getInstance();
	    int year = c.get(Calendar.YEAR);
	    int month = c.get(Calendar.MONTH) + 1;
	    int date = c.get(Calendar.DATE);
	    
	    String homedir = saveFolder + File.separator + year + "-" + month + "-" + date;
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
	    String fileDBName = File.separator + year + "-" + month + "-" + date + File.separator + refileName;
	    logger.info("fileDBName = " + fileDBName);
	      
	    return fileDBName;
	}

}
