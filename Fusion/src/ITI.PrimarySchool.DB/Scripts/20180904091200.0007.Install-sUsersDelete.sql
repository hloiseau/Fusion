create proc iti.sUsersDelete
(
    @UsersId int
)
as
begin
	set transaction isolation level serializable;
	begin tran;

	if not exists(select * from iti.tUsers u where u.UsersId = UsersId)
	begin
		rollback;
		return 1;
	end;

    update iti.tUsersDevice set UsersId = 0 where UsersId = @UsersId;
	update iti.tSMS set UsersId = 0 where UsersId = @UsersId;
	update iti.tLogs set UsersId = 0 where UsersId = @UsersId;
    delete from iti.tUsers where UsersId = @UsersId;
	commit;
    return 0;
end;