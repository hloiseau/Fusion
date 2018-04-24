create view iti.vContact
as
    select
        ContactId = c.ContactId,
        FirstName = c.FirstName,
        LastName = c.LastName,
		Mail = c.Mail,
		PhoneNumber = c.PhoneNumber
	from iti.tContact c
    where c.ContactId <> 0;