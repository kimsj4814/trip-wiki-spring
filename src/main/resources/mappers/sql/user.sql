drop table users cascade constraints;

create table users(
	user_id varchar2(30) primary key,
	user_password varchar2(100),
	user_profile varchar2(200),
	user_profile_original varchar2(200),
	user_nickname varchar2(20),
	user_email varchar2(80),
	user_authMailcode varchar2(100),
	user_reg_date Date
)
