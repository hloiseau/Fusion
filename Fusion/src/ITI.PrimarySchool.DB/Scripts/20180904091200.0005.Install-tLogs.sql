create table iti.tLogs
(
  LogId int identity(0, 1),
  UsersId int not null,
  [Time] datetime not null,
  DevicesId int not null,
  [Action] text,

  constraint PK_tLogs primary key(LogId),
  constraint FK_tUsers foreign key(UsersId) references iti.tUsers(UsersId)
)

insert into iti.tLogs(UsersId, [Time], DevicesId, [Action]) values(0, '01:00', 0, 'Send a message test to see');