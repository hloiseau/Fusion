using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Diagnostics;
using System.Threading.Tasks;
using Dapper;

namespace Fusion.DAL
{
    public class UserGateway
    {
        readonly string _connectionString;

        public UserGateway( string connectionString )
        {
            _connectionString = connectionString;
        }

        public async Task<UserData> FindById( int userId )
        {
            using( SqlConnection con = new SqlConnection( _connectionString ) )
            {
                return await con.QueryFirstOrDefaultAsync<UserData>(
                    "select u.UsersId, u.Mail, u.GoogleRefreshToken, u.GoogleId from iti.vUsers u where u.UsersId = @UsersId",
                    new { UsersId = userId } );
            }
        }

        public async Task<Result<UserData>> FindGitHubUser( int userId )
        {
            using( SqlConnection con = new SqlConnection( _connectionString ) )
            {
                UserData user = await con.QueryFirstOrDefaultAsync<UserData>(
                    @"select u.UsersId,
                             u.Mail,
                             u.GoogleRefreshToken,
                             u.GoogleId
                      from iti.vUsers u
                      where u.UsersId = @UsersId;",
                    new { UsersId = userId } );

                if( user == null ) return Result.Failure<UserData>( Status.BadRequest, "Unknown user." );

                return Result.Success( user );
            }
        }

        public async Task<UserData> FindByEmail( string email )
        {
            using( SqlConnection con = new SqlConnection( _connectionString ) )
            {
                return await con.QueryFirstOrDefaultAsync<UserData>(
                    "select u.UsersId, u.Mail, u.GoogleRefreshToken, u.GoogleId from iti.vUsers u where u.Mail = @Email",
                    new { Email = email } );
            }
        }

        public async Task<UserData> FindByGoogleId( string googleId )
        {
            using( SqlConnection con = new SqlConnection( _connectionString ) )
            {
                return await con.QueryFirstOrDefaultAsync<UserData>(
                    "select u.UsersId, u.Mail, u.GoogleRefreshToken, u.GoogleId from iti.vUsers u where u.GoogleId = @GoogleId",
                    new { GoogleId = googleId } );
            }
        }

        public async Task<Result<int>> CreatePasswordUser( string email, byte[] password )
        {
            using( SqlConnection con = new SqlConnection( _connectionString ) )
            {
                var p = new DynamicParameters();
                p.Add( "@Mail", email );
                p.Add( "@Password", password );
                p.Add( "@UsersId", dbType: DbType.Int32, direction: ParameterDirection.Output );
                p.Add( "@Status", dbType: DbType.Int32, direction: ParameterDirection.ReturnValue );
                await con.ExecuteAsync( "iti.sPasswordUserCreate", p, commandType: CommandType.StoredProcedure );

                int status = p.Get<int>( "@Status" );
                if( status == 1 ) return Result.Failure<int>( Status.BadRequest, "An account with this email already exists." );

                Debug.Assert( status == 0 );
                return Result.Success( p.Get<int>( "@UsersId" ) );
            }
        }

        public async Task CreateOrUpdateGithubUser( string email, int githubId, string accessToken )
        {
            using( SqlConnection con = new SqlConnection( _connectionString ) )
            {
                await con.ExecuteAsync(
                    "iti.sGithubUserCreateOrUpdate",
                    new { Mail = email, GithubId = githubId, AccessToken = accessToken },
                    commandType: CommandType.StoredProcedure );
            }
        }

        public async Task CreateOrUpdateGoogleUser( string email, string googleId, string refreshToken )
        {
            using( SqlConnection con = new SqlConnection( _connectionString ) )
            {
                await con.ExecuteAsync(
                    "iti.sGoogleUsersCreateOrUpdate",
                    new { Mail = email, GoogleId = googleId, RefreshToken = refreshToken },
                    commandType: CommandType.StoredProcedure );
            }
        }

        public async Task<IEnumerable<string>> GetAuthenticationProviders( string userId )
        {
            using( SqlConnection con = new SqlConnection( _connectionString ) )
            {
                return await con.QueryAsync<string>(
                    "select p.ProviderName from iti.vAuthenticationProvider p where p.UsersId = @UsersId",
                    new { UsersId = userId } );
            }
        }

        public async Task Delete( int userId )
        {
            using( SqlConnection con = new SqlConnection( _connectionString ) )
            {
                await con.ExecuteAsync( "iti.sUserDelete", new { UsersId = userId }, commandType: CommandType.StoredProcedure );
            }
        }

        public async Task UpdateEmail( int userId, string email )
        {
            using( SqlConnection con = new SqlConnection( _connectionString ) )
            {
                await con.ExecuteAsync(
                    "iti.sUserUpdate",
                    new { UsersId = userId, Mail = email },
                    commandType: CommandType.StoredProcedure );
            }
        }

        public async Task UpdatePassword( int userId, byte[] password )
        {
            using( SqlConnection con = new SqlConnection( _connectionString ) )
            {
                await con.ExecuteAsync(
                    "iti.sPasswordUserUpdate",
                    new { UsersId = userId, Password = password },
                    commandType: CommandType.StoredProcedure );
            }
        }
    }
}
