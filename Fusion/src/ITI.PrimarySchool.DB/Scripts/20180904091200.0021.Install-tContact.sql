create table iti.tContact
(
  ContactId INT NOT NULL,
  FirstName varchar(32),
  LastName varchar(32),
  Mail varchar(32),
  PhoneNumber VARCHAR(32),

  constraint PK_tContact primary key(ContactId)
)