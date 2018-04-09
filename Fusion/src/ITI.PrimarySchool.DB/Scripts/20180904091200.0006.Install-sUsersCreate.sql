create proc iti.sUsersCreate
(
	@FirstName nvarchar(32),
    @LastName  nvarchar(32),
	@Mail nvarchar(32),
	@UsersId int out
)
as
begin
	set transaction isolation level serializable;
	begin tran;

	if exists(select * from iti.tUser u where u.FirstName = @FirstName and u.LastName = @LastName and u.Mail = @Mail)
	begin
		rollback;
		return 1;
	end;

    insert into iti.tUsers(FirstName, LastName, Mail) values(@FirstName, @LastName, @Mail);
	set @UsersId = scope_identity();
	commit;
    return 0;
end;