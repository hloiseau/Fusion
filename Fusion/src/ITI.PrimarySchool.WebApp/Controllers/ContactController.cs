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

        
        [HttpPost]
        [AllowAnonymous]
        [ValidateAntiForgeryToken]
        public async Task<Result> token(string token1, string token2)
        {
            _contactViewModel.Token = token1;
            _contactViewModel.ConfirmToken = token2;
            return Result.Success();
        }
    }
}
