create proc iti.sContactCreate
(
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
		if exists(select * from iti.tContact c where c.FirstName like @FirstName and LastName like @LastName or c.FirstName like @FirstName and LastName is null)
		
	begin
		rollback;
		return 1;
	end;

    insert into iti.tContact(FirstName, LastName, Mail, PhoneNumber) values(@FirstName, @LastName, @Mail, @PhoneNumber);
	set @ContactId = scope_identity();
	commit;
    return 0;
end;