create proc iti.sUsersUpdate
(
    @UsersId int,
    @FirstName nvarchar(32),
    @LastName  nvarchar(32),
	@Mail  nvarchar(45)
)
as
begin
	set transaction isolation level serializable;
	begin tran;

	if not exists(select * from iti.tUsers u where u.UsersId = @UsersId)
	begin
		rollback;
		return 1;
	end;

	if exists(select * from iti.tUsers u where u.UsersId <> @UsersId and u.FirstName = @FirstName and u.LastName = @LastName and u.Mail = @Mail)
	begin
		rollback;
		return 2;
	end;

    update iti.tUsers set FirstName = @FirstName, LastName = @LastName, Mail = @Mail where UsersId = @UsersId;
	commit;
    return 0;
end;