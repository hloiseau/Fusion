create view iti.vAuthenticationProvider
as
    select usr.UsersId, usr.ProviderName
    from (select UsersId = u.UsersId,
              ProviderName = 'Fusion'
          from iti.tUsers u
          union all
          select UsersId = u.UsersId,
              ProviderName = 'Google'
          from iti.tGoogleUsers u) usr
    where usr.UsersId <> 0;