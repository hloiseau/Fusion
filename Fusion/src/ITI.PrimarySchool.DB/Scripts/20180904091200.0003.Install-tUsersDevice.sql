create table iti.tUsersDevice
(
    UsersDeviceId int identity(0, 1),
    UsersId int not null,
    DevicesId  int not null,

    constraint PK_tUsersDevice primary key(UsersDeviceId),
	constraint FK_tUsersDevice_tUsers foreign key(UsersId) references iti.tUsers(UsersId),
	constraint FK_tUsersDevice_tDevices foreign key(DevicesId) references iti.tDevices(DevicesId)
);

insert into iti.tUsersDevice(UsersId, DevicesId) values(0, 0);