drop table Community cascade constraints purge;
CREATE TABLE community(
	BOARD_NUM	 NUMBER,	--글번호
	BOARD_NAME   VARCHAR2(30),	--작성자
	BOARD_PASS	 VARCHAR2(30),	--비밀번호
	BOARD_SUBJECT	 VARCHAR2(300),	--제목
	BOARD_CONTENT	 VARCHAR2(4000),	--내용
	BOARD_FILE	 VARCHAR2(50),	--첨부될 파일명
	BOARD_ORIGINAL VARCHAR2(50),
	BOARD_RE_REF	 NUMBER,	--답변글 작성시 참조되는 글의 번호
	BOARD_RE_LEV	 NUMBER,	--답변 글의 깊이
	BOARD_RE_SEQ	 NUMBER,	--답변 글의 순서
	BOARD_READCOUNT	 NUMBER,	--글의 조회수
	BOARD_LOCATION VARCHAR2(30),
	BOARD_DATE DATE default sysdate,	--글의 작성 날짜
	PRIMARY KEY(BOARD_NUM)
	
);

select * from community