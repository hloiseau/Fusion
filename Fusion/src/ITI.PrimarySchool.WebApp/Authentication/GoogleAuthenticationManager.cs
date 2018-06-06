using System.Threading.Tasks;
using Fusion.DAL;
using Fusion.WebApp.Services;
using Microsoft.AspNetCore.Authentication.OAuth;

namespace Fusion.WebApp.Authentication
{
    public class GoogleAuthenticationManager : AuthenticationManager<GoogleUserInfo>
    {
        readonly UserGateway _userGateway;
        public static string GoogleIdgeneral { get; private set; } 
        public GoogleAuthenticationManager( UserService userService, UserGateway userGateway )
        {
            _userGateway = userGateway;
        }

        protected override async Task CreateOrUpdateUser( GoogleUserInfo userInfo )
        {
            if( userInfo.RefreshToken != null )
            {
                GoogleIdgeneral = userInfo.GoogleId;
                await _userGateway.CreateOrUpdateGoogleUser( userInfo.Email, userInfo.GoogleId, userInfo.RefreshToken );
            }
        }

        protected override Task<UserData> FindUser( GoogleUserInfo userInfo )
        {
            GoogleIdgeneral = userInfo.GoogleId;
            return _userGateway.FindByGoogleId( userInfo.GoogleId );
        }

        protected override Task<GoogleUserInfo> GetUserInfoFromContext( OAuthCreatingTicketContext ctx )
        {
            GoogleIdgeneral = ctx.GetGoogleId();
            return Task.FromResult( new GoogleUserInfo
            {
                RefreshToken = ctx.RefreshToken,
                Email = ctx.GetEmail(),
                GoogleId = ctx.GetGoogleId()
            } );
        }
    }

    public class GoogleUserInfo
    {
        public string RefreshToken { get; set; }

        public string Email { get; set; }

        public string GoogleId { get; set; }
    }
}
