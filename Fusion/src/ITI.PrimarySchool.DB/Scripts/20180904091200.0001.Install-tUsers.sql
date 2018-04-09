create table iti.tUsers
(
    UsersId int identity(0, 1),
    FirstName nvarchar(32) not null,
    LastName  nvarchar(32) not null,
	Mail nvarchar(32) not null,

    constraint PK_tUsers primary key(UsersId),
    constraint UK_tUsers_FirstName_LastName unique(FirstName, LastName),
    constraint CK_tUsers_FirstName check(FirstName <> N''),
    constraint CK_tUsers_LastName check(LastName <> N''),
	constraint CK_tUsers_Mail check(Mail <> N'')
);