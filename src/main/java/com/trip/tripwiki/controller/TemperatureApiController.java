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
public class TemperatureApiController {
	
	private static final Logger logger = LoggerFactory.getLogger(TemperatureApiController.class);
	
	@Value("${my.apikey}")
	private String API_KEY;

	@GetMapping("/apitest3")
	public String callApiWithXml(String regId, String tmFc) {
		StringBuffer result = new StringBuffer();
		String jsonPrintString = null;
		
		String encodeResult = null;
		
		String apiUrl="http://apis.data.go.kr/1360000/MidFcstInfoService/getMidTa"
				+ "?serviceKey="+API_KEY
				+ "&numOfRows=10&pageNo=1"
				+ "&regId="+regId
				+ "&tmFc="+tmFc;
		
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
