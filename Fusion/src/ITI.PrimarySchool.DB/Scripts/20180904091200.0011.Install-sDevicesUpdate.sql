create proc iti.sDevicesUpdate
(
    @Name nvarchar(32),
    @Type  nvarchar(8),
	@Token nvarchar(250),
	@DevicesId int out
)
as
begin
	set transaction isolation level serializable;
	begin tran;

	if not exists(select * from iti.tDevices u where u.DevicesId = @DevicesId)
	begin
		rollback;
		return 1;
	end;

	if exists(select * from iti.tDevices u where u.DevicesId <> @DevicesId and u.[Name] = @Name and u.[Type] = @Type and u.Token = @Token)
	begin
		rollback;
		return 2;
	end;

    update iti.tDevices set [Name] = @Name, [Type] = @Type, Token = @Token where DevicesId = @DevicesId;
	commit;
    return 0;
end;