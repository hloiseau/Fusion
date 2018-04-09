create table iti.tLogs
(
  LogId int not null,
  UsersId int not null,
  [Time] datetime not null,
  DevicesId int not null,
  [Action] text,

  constraint PK_tUsersDevice primary key(LogId),
  constraint FK_tUsersDevice_tUsers foreign key(UsersId) references test.tUsers(UsersId)
)