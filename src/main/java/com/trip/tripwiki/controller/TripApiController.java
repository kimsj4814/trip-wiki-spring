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
public class TripApiController {
	
	private static final Logger logger = LoggerFactory.getLogger(TripApiController.class);
	
	@Value("${my.apikey}")
	private String API_KEY;
	
	@GetMapping("/apitest")
	public Map<String,Object> callApiWithXml(String keyword, int page) {
		StringBuffer result = new StringBuffer();
		String jsonPrintString = null;
		//keyword = "서울";
		String encodeResult = null;
		try {
			encodeResult = URLEncoder.encode(keyword,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String apiUrl="http://api.visitkorea.or.kr/openapi/"
				+ "service/rest/KorService/searchKeyword?"
				+ "serviceKey="+API_KEY
				+"&MobileApp=AppTest&MobileOS=ETC&"
				+ "pageNo="+page+"&numOfRows=8&listYN=Y&arrange=A&contentTypeId=12&keyword=" +encodeResult;
																										//+"&areaCode=1;"
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
		            logger.info(result.toString());
		           
		            JSONObject jsonObject = XML.toJSONObject(result.toString());
		            jsonPrintString = jsonObject.toString();
					
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				
				//0 1 2 3 4 5 6 7 8 9 0  1      25
				//"t o t a l C o u n t " : 1 4 1, "items"  [22-24]
				int start = jsonPrintString.indexOf("totalCount");//10   21
				int end = jsonPrintString.indexOf(",\"items");//25
				int listcount = Integer.parseInt(jsonPrintString.substring(start+12,end));
				int limit = 8;
				int maxpage = (listcount + limit - 1) / limit;

				if(page>maxpage) {
					page=maxpage;
				}
				
				int startpage = ((page - 1) / 10) * 10 + 1;
				int endpage = startpage + 10 - 1;

				if (endpage > maxpage)
					endpage = maxpage;
				
				Map<String, Object> map = new HashMap<String,Object>();
		        map.put("page", page);
		        map.put("maxpage", maxpage);
		        map.put("startpage", startpage);
		        map.put("endpage", endpage);
		        map.put("listcount", listcount);
		        map.put("boardlist", jsonPrintString);
		        map.put("limit", limit);
		        return map;
				
	}
	
	@GetMapping("/apiMain")
	public Map<String,Object> callApi_main() {
		StringBuffer result = new StringBuffer();
		String jsonPrintString = null;
		
		// 서울 여행지 6개 불러오기
		String apiUrl="http://api.visitkorea.or.kr/openapi/"
				+ "service/rest/KorService/searchKeyword?"
				+ "serviceKey="+API_KEY
				+"&MobileApp=AppTest&MobileOS=ETC&"
				+ "pageNo=1&numOfRows=6&listYN=Y&arrange=A&contentTypeId=12&keyword=%EC%84%9C%EC%9A%B8";
																							
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
		    logger.info(result.toString());
		           
		    JSONObject jsonObject = XML.toJSONObject(result.toString());
		    jsonPrintString = jsonObject.toString();
					
		} catch(Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("boardlist", jsonPrintString);
		return map;
	}
}
