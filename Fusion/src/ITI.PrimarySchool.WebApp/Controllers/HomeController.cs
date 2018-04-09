using System.Collections.Generic;
using System.Linq;
using System.Security.Claims;
using System.Threading.Tasks;
using Fusion.DAL;
using Fusion.WebApp.Authentication;
using Fusion.WebApp.Services;
using Microsoft.AspNetCore.Mvc;

namespace Fusion.WebApp.Controllers
{
    public class HomeController : Controller
    {
        readonly TokenService _tokenService;
        readonly UserGateway _userGateway;

        public HomeController( TokenService tokenService, UserGateway userGateway )
        {
            _tokenService = tokenService;
            _userGateway = userGateway;
        }

        // GET: /<controller>/
        public async Task<IActionResult> Index()
        {
            ClaimsIdentity identity = User.Identities.SingleOrDefault( i => i.AuthenticationType == CookieAuthentication.AuthenticationType );
            if( identity != null )
            {
                string userId = identity.FindFirst( ClaimTypes.NameIdentifier ).Value;
                string email = identity.FindFirst( ClaimTypes.Email ).Value;
                Token token = _tokenService.GenerateToken( userId, email );
                IEnumerable<string> providers = await _userGateway.GetAuthenticationProviders( userId );
                ViewData[ "Token" ] = token;
                ViewData[ "Email" ] = email;
                ViewData[ "Providers" ] = providers;
            }
            else
            {
                ViewData[ "Token" ] = null;
                ViewData[ "Email" ] = null;
                ViewData[ "Providers" ] = null;
            }

            ViewData[ "NoLayout" ] = true;
            return View();
        }
    }
}
