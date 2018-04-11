create table iti.tLogs
(
  LogId int not null,
  UsersId int not null,
  [Time] datetime not null,
  DevicesId int not null,
  [Action] text,

  constraint PK_tLogs primary key(LogId),
  constraint FK_tUsers foreign key(UsersId) references iti.tUsers(UsersId)
)