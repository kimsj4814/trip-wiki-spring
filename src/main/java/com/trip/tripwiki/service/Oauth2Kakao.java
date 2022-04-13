package com.trip.tripwiki.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trip.tripwiki.domain.AuthorizationKakao;
import lombok.RequiredArgsConstructor;

@Service
//롬복에서 제공하는 final 자동 생성자 기능
@RequiredArgsConstructor
public class Oauth2Kakao {
	//restTemplate 사용 Rest방식 API를 호출할때 유용한 스프링 내장객체.
	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;
	@Value("${my.kakao.restapi.key}")
	private String kakaoOuatch2Clinentid;
	@Value("${my.kakao.redirect.url}")
	private String frontendRedirectUrl;
	
	public AuthorizationKakao callTokenApi(String code) {
		String grantType = "authorization_code";
		
		//헤더 설정을 위해 HttpHeader 객체를 불러서 ContenType을 MeidaType객체를 이용하여
		//form-unlencoded 방식으로 만들어준다.
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("grant_type", grantType);
		params.add("client_id", kakaoOuatch2Clinentid);
		params.add("redirect_url", frontendRedirectUrl);
		params.add("code", code);
		
		HttpEntity<MultiValueMap<String, String>> request =
				new HttpEntity<>(params,headers);
		String url = "https://kauth.kakao.com/oauth/token";
		try {
			//GET 방식 요청으로 결과를 ResponseEntity로 반환 반환 타입을 스트링클래스로
			ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
			
			AuthorizationKakao authorization = objectMapper.readValue(
					response.getBody(), AuthorizationKakao.class);
			return authorization;
			
		}catch (RestClientException | JsonProcessingException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		

	}//end
	
	 public String callGetUserByAccessToken(String accessToken) {
	        HttpHeaders headers = new HttpHeaders();
	        headers.set("Authorization", "Bearer " + accessToken);
	        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

	        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

	        String url = "https://kapi.kakao.com/v2/user/me";
	        try {
	            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

	            // 값 리턴
	            return response.getBody();
	        }catch (RestClientException ex) {
	            ex.printStackTrace();
	            return null;
	        }
	    }
}
