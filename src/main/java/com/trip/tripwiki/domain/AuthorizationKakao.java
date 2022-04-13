package com.trip.tripwiki.domain;

import lombok.Getter;


//Getter만 사용하는 것을 ValueObject 
//값을 표현만 하는것을 Vo
//값까지 전달가능한것이 DTO(data transfer Object)
@Getter
public class AuthorizationKakao {
	private String access_token;
	private String token_type;
	private String refresh_token;
	private String expires_in;
	private String scope;
	private String refresh_token_expires_in;
}
