create proc iti.sSMSDelete
(
    @SMSId int
)
as
begin
	set transaction isolation level serializable;
	begin tran;

	if not exists(select * from iti.tSMS u where u.SMSId = @SMSId)
	begin
		rollback;
		return 1;
	end;

    delete from iti.tSMS where SMSId = @SMSId;
	commit;
    return 0;
end;