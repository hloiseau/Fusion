create proc iti.sContactUpdate
(
	@DeviceId int,
    @FirstName nvarchar(32),
    @LastName  nvarchar(32),
	@Mail nvarchar(32),
	@PhoneNumber nvarchar(32),
	@ContactId int out
)
as
begin
	set transaction isolation level serializable;
	begin tran;

	if not exists(select * from iti.tContact c where c.ContactId = @ContactId)
	begin
		rollback;
		return 1;
	end;

	if exists(select * from iti.tContact c where c.DeviceId <> @DeviceId and c.ContactId <> @ContactId and c.FirstName = @FirstName and c.LastName = @LastName and c.Mail = @Mail and PhoneNumber = @PhoneNumber)
	begin
		rollback;
		return 2;
	end;

    update iti.tContact set DeviceId = @DeviceId, FirstName = @FirstName, LastName = @LastName, Mail = @Mail, PhoneNumber = @PhoneNumber where ContactId = @ContactId;
	commit;
    return 0;
end;