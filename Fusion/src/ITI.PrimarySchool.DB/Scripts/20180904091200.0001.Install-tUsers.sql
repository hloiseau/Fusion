create table iti.tUsers
(
    UsersId int identity(0, 1),
    FirstName nvarchar(32),
    LastName  nvarchar(32),
	Mail nvarchar(45),

    constraint PK_tUsers primary key(UsersId),
    constraint UK_tUsers_FirstName_LastName unique(FirstName, LastName),
    constraint CK_tUsers_FirstName check(FirstName <> N''),
    constraint CK_tUsers_LastName check(LastName <> N''),
	constraint CK_tUsers_Mail check(Mail <> N'')
);

insert into iti.tUsers(FirstName,                                LastName,										Mail)
                  values(left(convert(nvarchar(36), newid()), 32), left(convert(nvarchar(36), newid()), 32), left(convert(nvarchar(49), newid()), 45));