using System.Collections.Generic;
using System.IO;
using System.Threading.Tasks;
using Fusion.DAL;
using Fusion.WebApp.Models.AccountViewModels;
using Microsoft.AspNetCore.Http.Connections.Internal;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.SignalR;

namespace Fusion.WebApp.Controllers
{

    [Route("api/[controller]")]
    public class ContactController : Controller
    {
        readonly ContactGateway _contactGateway;
        private readonly IHubContext<VueHub> _hubContext;

        public ContactController(ContactGateway contactGateway, IHubContext<VueHub> hubContext)
        {
            _contactGateway = contactGateway;
            _hubContext = hubContext;
        }

        [HttpGet]
        public async Task<IActionResult> GetContactList()
        {
            IEnumerable<ContactData> result = await _contactGateway.ListAll();
            await _hubContext.Clients.All.SendAsync("send", "Test");
            return Ok(result);
        }

        [HttpGet("{id}", Name = "GetContact")]
        public async Task<IActionResult> GetContactById(int id)
        {
            Result<ContactData> result = await _contactGateway.FindById(id);
            return this.CreateResult(result);
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
