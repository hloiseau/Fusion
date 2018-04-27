create proc iti.sUserDeviceCreate
(
	@UsersId int,
    @DevicesId  int,
	@UsersDeviceId int out
)
as
begin
	set transaction isolation level serializable;
	begin tran;

	if exists(select * from iti.tUsersDevice d where d.UsersId = @UsersId and d.DevicesId = @DevicesId)
	begin
		rollback;
		return 1;
	end;

    insert into iti.tUsersDevice(UsersId, DevicesId) values(@UsersId, @DevicesId);
	set @UsersDeviceId = scope_identity();
	commit;
    return 0;
end;