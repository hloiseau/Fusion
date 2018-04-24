create proc iti.sDevicesDelete
(
    @DevicesId int
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

    update iti.tUsersDevice set DevicesId = 0 where DevicesId = @DevicesId;
	update iti.tSMS set DevicesId = 0 where DevicesId = @DevicesId;
    delete from iti.tDevices where DevicesId = @DevicesId;
	commit;
    return 0;
end;