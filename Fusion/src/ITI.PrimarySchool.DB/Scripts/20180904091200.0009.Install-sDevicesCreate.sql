create proc iti.sUsersCreate
(
	@Name nvarchar(32) not null,
    @Type  nvarchar(8) not null,
	@Token text not null,
	@DevicesId int out
)
as
begin
	set transaction isolation level serializable;
	begin tran;

	if exists(select * from iti.tDevices d where d.[Name] = @Name and d.[Type] = @Type and d.Token = @Token)
	begin
		rollback;
		return 1;
	end;

    insert into iti.tDevices([Name], [Type], Token) values(@Name, @Type, @Token);
	set @DevicesId = scope_identity();
	commit;
    return 0;
end;