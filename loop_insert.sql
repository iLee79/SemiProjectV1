CREATE procedure loop_insert(IN cnt int)
begin
	declare i int default 1;
	while (i<=cnt) do
		insert into board(title,userid,contents) values('우우훗','a','우우훗');
		insert into board(title,userid,contents) values('푸허헐','b','푸허헐');
		insert into board(title,userid,contents) values('가나다','b','라마바');
		set i = i + 1;
	end while;
end;

delete from board;

call loop_insert(11004);