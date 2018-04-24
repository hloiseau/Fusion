create proc iti.sUserDeviceDelete
(
    @UsersDeviceId int
)
as
begin
	set transaction isolation level serializable;
	begin tran;

	if not exists(select * from iti.tUsersDevice u where u.UsersDeviceId = @UsersDeviceId)
	begin
		rollback;
		return 1;
	end;

    delete from iti.tUsersDevice where UsersDeviceId = @UsersDeviceId;
	commit;
    return 0;
end;