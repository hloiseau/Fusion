using System.Collections.Generic;
using System.Security.Claims;
using System.Threading.Tasks;
using Fusion.DAL;
using Fusion.WebApp.Authentication;
using Fusion.WebApp.Services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace Fusion.WebApp.Controllers
{
    [Route( "api/[controller]" )]
    [Authorize( AuthenticationSchemes = JwtBearerAuthentication.AuthenticationScheme )]
    public class GitHubController : Controller
    {
        readonly GitHubService _gitHubService;

        public GitHubController( GitHubService gitHubService )
        {
            _gitHubService = gitHubService;
        }

        [HttpGet( "Following" )]
        public async Task<IActionResult> GetFollowedStudents()
        {
            int userId = int.Parse( User.FindFirst( ClaimTypes.NameIdentifier ).Value );
            Result<IEnumerable<FollowedStudentData>> result = await _gitHubService.GetFollowedStudents( userId );
            return this.CreateResult( result );
        }
    }
}
