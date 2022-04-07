drop table users cascade constraints;

create table users(
	user_id varchar2(30) primary key,
	user_password varchar2(100),
	user_profile varchar2(200),
	user_profile_original varchar2(200),
	user_email varchar2(80),
	user_mail_authkey varchar2(100),
	user_mail_auth number(1),
	user_reg_date Date
)
