create proc iti.sSMSUpdate
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

	if not exists(select * from iti.tSMS u where u.SMSId = @SMSId)
	begin
		rollback;
		return 1;
	end;

	if exists(select * from iti.tSMS u where u.SMSId <> @SMSId and u.DevicesId = @DevicesId and u.UsersId = @UsersId and u.Extern = @Extern and u.[Message] like @Message and u.direction = @direction)
	begin
		rollback;
		return 2;
	end;

    update iti.tSMS set [Message] = @Message, @direction = direction where SMSId = @SMSId;
	commit;
    return 0;
end;