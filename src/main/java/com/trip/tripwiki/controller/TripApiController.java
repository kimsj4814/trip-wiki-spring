package com.trip.tripwiki.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import java.net.HttpURLConnection;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class TripApiController {

	@Value("${my.apikey}")
	private String API_KEY;
	@GetMapping("/apitest")
	public String callApiWithXml(String keyword) {
		StringBuffer result = new StringBuffer();
		String jsonPrintString = null;
		String apiUrl="http://api.visitkorea.or.kr/openapi/"
				+ "service/rest/KorService/searchKeyword?"
				+ "serviceKey="+API_KEY
				+"&MobileApp=AppTest&MobileOS=ETC&"
				+ "pageNo=1&numOfRows=10&listYN=Y&arrange=A&contentTypeId=12&keyword=%EA%B2%BD%EA%B8%B0";
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
