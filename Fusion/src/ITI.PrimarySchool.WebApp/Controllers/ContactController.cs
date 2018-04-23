using System;
using System.Collections.Generic;
using System.Security.Claims;
using System.Threading.Tasks;
using Fusion.DAL;
using Fusion.WebApp.Authentication;
using Fusion.WebApp.Models.AccountViewModels;
using Fusion.WebApp.Services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authentication;

namespace Fusion.WebApp.Controllers
{

    [Route( "api/[controller]" )]
    public class ContactController : Controller
    {

        readonly ContactGateway _contactGateway;
        
        public ContactController(ContactGateway contactGateway)
        {
            _contactGateway = contactGateway;
        }

        
        [HttpPost]
        public async Task<Result> Token([FromBody] TokenVewModel model)
        {
            await _contactGateway.AddConctact(model.Token);
            return Result.Success();
        }
    }
}
