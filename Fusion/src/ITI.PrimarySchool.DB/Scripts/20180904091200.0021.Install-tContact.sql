create table iti.tContact
(
  ContactId int identity(0, 1),
  FirstName varchar(32),
  LastName varchar(32),
  Mail varchar(32) NOT NULL,
  PhoneNumber VARCHAR(32) NOT NULL,

  constraint PK_tContact primary key(ContactId)
)