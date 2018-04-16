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


    public class ContactController : Controller
    {

        readonly ContactGateway _contactGateway;
        private ContactViewModel _contactViewModel;
        
        public ContactController(ContactGateway contactGateway)
        {
            _contactGateway = contactGateway;
        }

        
        [HttpPost("android/newtoken/{token}", Name = "GetAllInfo")]
        [ValidateAntiForgeryToken]
        public async Task<Result> token(string token)
        {
            _contactGateway.SaveToken() = token;
            return Result.Success();
        }
    }
}
