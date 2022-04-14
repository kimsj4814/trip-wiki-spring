package com.trip.tripwiki.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.net.HttpURLConnection;

import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class DetailApiController {
	
	private static final Logger logger = LoggerFactory.getLogger(DetailApiController.class);
	
	@Value("${my.apikey}")
	private String API_KEY;

	@GetMapping("/apitest2")
	public String callApiWithXml(int contentId) {
		StringBuffer result = new StringBuffer();
		String jsonPrintString = null;
		
		String encodeResult = null;
		try {
			String appName = URLEncoder.encode("한국관광공사", "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String apiUrl="http://api.visitkorea.or.kr/openapi/"
				+ "service/rest/KorService/detailCommon?"
				+ "serviceKey="+API_KEY
				+ "&contentId="+contentId
				+ "&defaultYN=Y&MobileOS=ETC&MobileApp=AppTest&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y";
		
				try {
					URL url = new URL(apiUrl);
					HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
					urlConnection.setRequestMethod("GET");
					
					BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
		            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
		            String returnLine;
		            while((returnLine = bufferedReader.readLine()) != null) {
		                result.append(returnLine);
		            }
		            
		
		            JSONObject jsonObject = XML.toJSONObject(result.toString());
		            jsonPrintString = jsonObject.toString();
					
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				
				return jsonPrintString;
				
	}
}
