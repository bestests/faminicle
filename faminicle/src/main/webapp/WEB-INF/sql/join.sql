CREATE TABLE FAM_MEMBER(
	MEM_NO 			INT 	NOT NULL 	PRIMARY KEY		  		AUTO_INCREMENT 
	,ID	 			VARCHAR(20)	 	NOT NULL 
	,PASS	 		VARCHAR(20)	 	NOT NULL 
	,E_MAIL		 	VARCHAR(100) 	NOT NULL  
	,MEM_PIC_PATH  VARCHAR(255)
	,NAME	 		VARCHAR(20)	 	NOT NULL
	,BIRTHDAY	 	VARCHAR(20)	 	NOT NULL 
	,TEL	 		INT		 		NOT NULL 	  
);

CREATE SEQUENCE SEQ_MEMBER_NO;

ALTER TABLE FAM_MEMBER ADD MEM_NO INT auto_increment primary key
ALTER TABLE FAM_MEMBER ADD login_pic_path varchar(100);

insert into fam_member(id,pass,e_Mail,name,birthday,tel)
		values("test","test","test@test","test","test","1234")




/*
	show full columns from 테이블명 ; //컬럼명 조회하기


	사용자 아이디
	사용자 비밀번호
	사용자 이메일
	사용자 이름
	사용자 생년월일
	사용자 연락처
*/



