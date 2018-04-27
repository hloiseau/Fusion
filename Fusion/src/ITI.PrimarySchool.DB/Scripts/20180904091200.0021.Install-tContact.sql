create table iti.tContact
(
  ContactId int identity(0, 1),
  FirstName varchar(32),
  LastName varchar(32),
  Mail varchar(32),
  PhoneNumber VARCHAR(32),

  constraint PK_tContact primary key(ContactId)
)

insert into iti.tContact(FirstName, LastName, Mail, PhoneNumber) values('Firstname0', 'Lastname0', 'Mailtest@meil.fr', '0123456789');