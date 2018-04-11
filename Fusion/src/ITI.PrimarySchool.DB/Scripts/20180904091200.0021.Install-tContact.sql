create table iti.tContactId
(
  ContactId INT NOT NULL,
  FirstName varchar(32),
  LastName varchar(32),
  Mail varchar(32) NOT NULL,
  PhoneNumber VARCHAR(32) NOT NULL,

  constraint PK_tUsersDevice primary key(ContactId)
)