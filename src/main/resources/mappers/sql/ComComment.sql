drop table comcomments cascade constraints;

create table comcomments(
num number primary key,
id varchar2(30) references users(user_id),
content varchar2(200),
reg_date date,
board_num number references community(BOARD_NUM)
on delete cascade
);

drop sequence comcom_seq;
create sequence comcom_seq;

select * from comments