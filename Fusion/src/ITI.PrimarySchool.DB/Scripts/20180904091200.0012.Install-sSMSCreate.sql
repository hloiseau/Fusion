create proc iti.sSMSCreate
(
	@DevicesId int,
    @UsersId  int,
	@Extern VARCHAR(45),
	@Time DATETIME,
	@Message TEXT,
	@direction BIT,
	@SMSId int out

)
as
begin
	set transaction isolation level serializable;
	begin tran;

	if exists(select * from iti.tSMS u where u.DevicesId = @DevicesId and u.UsersId = @UsersId and u.Extern = @Extern and u.[Message] like @Message and u.direction = @direction)
	begin
		rollback;
		return 1;
	end;

    insert into iti.tSMS(DevicesId, UsersId, Extern, [Time], [Message], direction) values(@DevicesId, @UsersId, @Extern, @Time, @Message, @direction);
	set @SMSId = scope_identity();
	commit;
    return 0;
end;