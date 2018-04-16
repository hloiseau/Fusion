create procedure iti.sGoogleUsersCreateOrUpdate
(
    @Mail        nvarchar(64),
    @GoogleId     varchar(32),
    @RefreshToken varchar(64)
)
as
begin
	set transaction isolation level serializable;
	begin tran;

	if exists(select * from iti.tGoogleUsers u where u.GoogleId = @GoogleId)
	begin
		update iti.tGoogleUsers set RefreshToken = @RefreshToken where GoogleId = @GoogleId;
		commit;
		return 0;
	end;

    declare @usersId int;
	select @usersId = u.UsersId from iti.tUsers u where u.Mail = @Mail;

	if @usersId is null
	begin
		insert into iti.tUsers(Mail) values(@Mail);
		set @usersId = scope_identity();
	end;
    
    insert into iti.tGoogleUsers(UsersId,  GoogleId,  RefreshToken)
                         values(@usersId, @GoogleId, @RefreshToken);
	commit;
    return 0;
end;