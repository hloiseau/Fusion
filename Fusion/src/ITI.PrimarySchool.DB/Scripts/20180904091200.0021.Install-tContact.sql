create table iti.tContact
(
  ContactId int identity(0, 1),
  DeviceId int not null,
  FirstName varchar(32),
  LastName varchar(32),
  Mail varchar(32),
  PhoneNumber VARCHAR(32),

	constraint PK_tContact primary key(ContactId),
	constraint FK_tContact_tDevices foreign key(DeviceId) references iti.tDevices(DevicesId)
)

insert into iti.tContact(DeviceId, FirstName, LastName, Mail, PhoneNumber) values(0, 'Firstname0', 'Lastname0', 'Mailtest@meil.fr', '0123456789');