create proc iti.sLogsDelete
(
    @LogId int
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

    delete from iti.tLogs where LogId = @LogId;
	commit;
    return 0;
end;