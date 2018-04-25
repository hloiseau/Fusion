using System;
using System.Collections.Generic;
using System.IO;
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

        [HttpGet]
        public async Task<IActionResult> GetContactList()
        {
            IEnumerable<ContactData> result = await _contactGateway.ListAll();
            return Ok(result);
        }

        [HttpPost("sync")]
        public async Task<IActionResult> ReciveContactList([FromBody] ContactVewModel model)
        {
            Request.Body.Seek(0, SeekOrigin.Begin);
            StreamReader sr = new StreamReader(Request.Body);
            string body = await sr.ReadToEndAsync();
            Result result = null;
            for (int i = 0; i < model.Contacts.Count; i++)
            {
                result = await _contactGateway.ReciveContactList(model.Contacts[i].Name, null, model.Contacts[i].Number);
            }
            return Ok(result);
        }

    }
}

/*
 Request.Body.Seek(0, SeekOrigin.Begin);
 StreamReader sr = new StreamReader(Request.Body);
 string body = await sr.ReadToEndAsync();
 */
