create proc iti.sUserDeviceUpdate
(
    @UsersId int,
    @DevicesId  int,
	@UsersDeviceId int out
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

	if exists(select * from iti.tUsersDevice u where u.UsersDeviceId <> @UsersDeviceId and u.UsersId = @UsersId and u.DevicesId = @DevicesId)
	begin
		rollback;
		return 2;
	end;

    update iti.tUsersDevice set UsersId = @UsersId, DevicesId = @DevicesId where UsersDeviceId = @UsersDeviceId;
	commit;
    return 0;
end;