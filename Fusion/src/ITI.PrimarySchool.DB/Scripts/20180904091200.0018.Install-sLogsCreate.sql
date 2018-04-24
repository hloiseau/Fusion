create proc iti.sLogsCreate
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

	if exists(select * from iti.tLogs d where d.LogId = @LogId and d.UsersId = @UsersId and [Time] = @Time and DevicesId = @DevicesId and [Action] like @Action)
	begin
		rollback;
		return 1;
	end;

    insert into iti.tLogs(UsersId, [Time], DevicesId, [Action]) values(@UsersId, @Time, @DevicesId, @Action);
	set @LogId = scope_identity();
	commit;
    return 0;
end;