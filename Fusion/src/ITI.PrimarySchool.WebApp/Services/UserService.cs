﻿using System.Threading.Tasks;
using Fusion.DAL;

namespace Fusion.WebApp.Services
{
    public class UserService
    {
        readonly UserGateway _userGateway;
        readonly PasswordHasher _passwordHasher;

        public UserService( UserGateway userGateway, PasswordHasher passwordHasher )
        {
            _userGateway = userGateway;
            _passwordHasher = passwordHasher;
        }

        public Task<Result<int>> CreatePasswordUser( string email, string password )
        {
            return _userGateway.CreatePasswordUser( email, _passwordHasher.HashPassword( password ) );
        }

        public async Task<UserData> FindUser( string email, string password )
        {
            UserData user = await _userGateway.FindByEmail( email );
            if( user != null)
            {
                return user;
            }

            return null;
        }
    }
}
