create proc iti.sContactDelete
(
    @ContactId int
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

    delete from iti.tContact where ContactId = @ContactId;
	commit;
    return 0;
end;