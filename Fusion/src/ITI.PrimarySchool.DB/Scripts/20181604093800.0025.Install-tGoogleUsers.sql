create table iti.tGoogleUsers
(
    UsersId       int,
    GoogleId     varchar(32) not null,
    RefreshToken varchar(64) not null,

    constraint PK_tGoogleUsers primary key(UsersId),
    constraint FK_tGoogleUsers_UserId foreign key(UsersId) references iti.tUsers(UsersId),
    constraint UK_tGoogleUsers_GoogleId unique(GoogleId)
);

insert into iti.tGoogleUsers(UsersId, GoogleId, RefreshToken) values(0, 0, '');