package com.trip.tripwiki.domain;

import java.sql.Date;

public class User {
	
	private String user_id;
	private String user_password;
	private String user_profile;
	private String user_profile_original;
//	private String user_nickname;
	private String user_email;
	private String user_mail_authkey;
	private int user_mail_auth;
	private Date user_reg_date;

	
	public String getUser_mail_authkey() {
		return user_mail_authkey;
	}
	public void setUser_mail_authkey(String user_mail_authkey) {
		this.user_mail_authkey = user_mail_authkey;
	}
	
	public int getUser_mail_auth() {
		return user_mail_auth;
	}
	public void setUser_mail_auth(int user_mail_auth) {
		this.user_mail_auth = user_mail_auth;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	public String getUser_profile() {
		return user_profile;
	}
	public void setUser_profile(String user_profile) {
		this.user_profile = user_profile;
	}
	public String getUser_profile_original() {
		return user_profile_original;
	}
	public void setUser_profile_original(String user_profile_original) {
		this.user_profile_original = user_profile_original;
	}
//	public String getUser_nickname() {
//		return user_nickname;
//	}
//	public void setUser_nickname(String user_nickname) {
//		this.user_nickname = user_nickname;
//	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public Date getUser_reg_date() {
		return user_reg_date;
	}
	public void setUser_reg_date(Date user_reg_date) {
		this.user_reg_date = user_reg_date;
	}
	
	
}
