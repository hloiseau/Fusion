create view iti.vSMS
as
	select
        SMSId = s.SMSId,
		[Time] = s.[Time],
		[Message] = s.[Message],
		direction = s.direction,
		DevicesId = d.devicesId,
		DeviceName = d.[Name],
		DeviceType = d.[Type],
		UsersId = u.UsersId,
		UserFirstname = case when u.UsersId = 0 then N'' else u.Firstname end,
		UserLastName = case when u.UsersId = 0 then N'' else u.LastName end,
		Extern = s.Extern,
		ContactId = c.ContactId
	from iti.tSMS s
		inner join iti.tUsers u on u.UsersId = s.UsersId
		inner join iti.tDevices d on d.DevicesId = s.DevicesId
		left outer join iti.tContact c on c.PhoneNumber = s.Extern
    where s.SMSId <> 0;