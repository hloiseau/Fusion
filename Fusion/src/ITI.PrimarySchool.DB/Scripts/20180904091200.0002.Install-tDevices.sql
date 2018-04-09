create table iti.tDevices
(
    DevicesId int identity(0, 1),
    [Name] nvarchar(32) not null,
    [Type]  nvarchar(8) not null,
	[Token] text not null,

    constraint PK_tDevices primary key(DevicesId),
    constraint UK_tDevices_Name_Type unique(Name, Type),
    constraint CK_tDevices_Name check(Name <> N''),
    constraint CK_tDevices_Type check([Type] in ('Mobile', 'Computer', 'Tablet', 'Laptop'))
);