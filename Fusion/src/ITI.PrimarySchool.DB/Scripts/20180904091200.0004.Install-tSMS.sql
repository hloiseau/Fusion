create table iti.tSMS
(
  idSMS INT NOT NULL,
  DevicesId INT NOT NULL,
  UsersId INT NOT NULL,
  Extern VARCHAR(45) NOT NULL,
  [Time] DATETIME NOT NULL,
  [Message] TEXT,
  direction BIT(1) NOT NULL,

  constraint PK_tUsersDevice primary key(DeviceId),
  constraint FK_tUsersDevice_tUsers foreign key(UsersId) references test.tUsers(UsersId),
  constraint FK_tUsersDevice_tDevices foreign key(DevicesId) references test.tUsers(DevicesId)
)