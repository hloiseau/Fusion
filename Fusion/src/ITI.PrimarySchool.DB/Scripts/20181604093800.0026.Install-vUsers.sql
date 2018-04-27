create view iti.vUsers
as
    select UsersId = u.UsersId,
           Mail = u.Mail,
           GoogleRefreshToken = case when gl.RefreshToken is null then '' else gl.RefreshToken end,
           GoogleId = case when gl.GoogleId is null then '' else gl.GoogleId end
    from iti.tUsers u
        left outer join iti.tGoogleUsers gl on gl.UsersId = u.UsersId
    where u.UsersId <> 0;
