create table listtest(
	no int auto_increment primary key,
	content varchar(100) not null,
	file_path varchar(255) not null,
	reg_date date not null
);

insert into listtest(content, file_path, reg_date)
values('test20', '../images/20.jpg', '1999-01-20');