create proc iti.sLogsUpdate
(
    @UsersId int,
	@Time datetime,
	@DevicesId int,
	@Action text,
	@LogId int out
)
as
begin
	set transaction isolation level serializable;
	begin tran;

	if not exists(select * from iti.tLogs u where u.LogId = @LogId)
	begin
		rollback;
		return 1;
	end;

	if exists(select * from iti.tLogs u where u.LogId <> @LogId and u.UsersId = @UsersId and [Time] = @Time and u.DevicesId = @DevicesId and [Action] like @Action)
	begin
		rollback;
		return 2;
	end;

    update iti.tLogs set UsersId = @UsersId, [Time] = @Time, DevicesId = @DevicesId, [Action] = @Action where LogId = @LogId;
	commit;
    return 0;
end;