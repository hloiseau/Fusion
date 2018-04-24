create view iti.vUsersDevice
as
    select
        UserId = u.UsersId,
		UserFirstname = case when u.UsersId = 0 then N'' else u.Firstname end,
		UserLastName = case when u.UsersId = 0 then N'' else u.LastName end,
		UserMail = case when u.UsersId = 0 then N'' else u.Mail end,
		DeviceId = d.devicesId,
		DeviceName = d.[Name],
		[Type] = d.[Type]
	from iti.tUsers u
		left outer join iti.tUsersDevice ud on ud.UsersId = u.UsersId
		left outer join iti.tDevices d on d.DevicesId = ud.devicesId
    where ud.UsersDeviceId <> 0;