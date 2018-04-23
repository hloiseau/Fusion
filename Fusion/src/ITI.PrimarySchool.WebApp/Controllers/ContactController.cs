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
    
    [Route("api/[controller]")]
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
            await _contactGateway.AddDevice(model.Token);
            return Result.Success();
        }

        [HttpPost("/sync/contact")]
        public async Task<IActionResult> ReciveContactList([FromBody] ContactVewModel model)
        {
            Result result = null;
            for (int i = 0; i <= model.ContactLs.Count; i++)
            {
                result = await _contactGateway.ReciveContactList(model.ContactLs[i].Name, model.ContactLs[i].Mail, model.ContactLs[i].Number);
            }
            return Ok(result);
        }

        [HttpGet]
        public async Task<IActionResult> GetContactList()
        {
            IEnumerable<ContactData> result = await _contactGateway.ListAll();
            return Ok(result);
        }
    }
}
