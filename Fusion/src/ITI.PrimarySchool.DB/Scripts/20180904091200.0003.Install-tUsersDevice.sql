create table iti.tUsersDevice
(
    UsersDeviceId int identity(0, 1),
    UserId int not null,
    DeviceId  int not null,

    constraint PK_tUsersDevice primary key(DeviceId),
	constraint FK_tUsersDevice_tUsers foreign key(UserId) references test.tUsers(UsersId),
	constraint FK_tUsersDevice_tDevices foreign key(DeviceId) references test.tDevices(DevicesId)
);