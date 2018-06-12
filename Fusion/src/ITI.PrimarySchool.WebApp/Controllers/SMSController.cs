using Fusion.DAL;
using Fusion.WebApp.Models.AccountViewModels;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Data.SqlTypes;
using System.IO;
using System.Threading.Tasks;
using Microsoft.AspNetCore.SignalR;

namespace Fusion.WebApp.Controllers
{

    [Route("api/[controller]")]
    public class SmsController : Controller
    {

        readonly SMSGateway _smsGateway;
        private readonly IHubContext<VueHub> _hubContext;

        public SmsController( SMSGateway smsGateway, IHubContext<VueHub> hubContext)
        {
            _smsGateway = smsGateway;
            _hubContext = hubContext;
        }

        [HttpPost("receivesms")]
        public async Task<IActionResult> ReceiveSMSList([FromBody] SMSViewModel model)
        {
            Request.Body.Seek(0, SeekOrigin.Begin);
            StreamReader sr = new StreamReader(Request.Body);
            string body = await sr.ReadToEndAsync();
            bool isSent = false;
            Result result = null;
            if (model.sms.Count == 1)
            {
                if (model.sms[0].Type == "1") isSent = true;
                else isSent = false;
                result = await _smsGateway.AddSMS(0, model.sms[0].Address, DateTimeOffset.FromUnixTimeMilliseconds(long.Parse(model.sms[0].Date)).DateTime, model.sms[0].Body, isSent);
                await _hubContext.Clients.All.SendAsync("Test", model.sms[0].Address, model.sms[0].Body); 
            }
            else
            {
                for (int i = 0; i < model.sms.Count; i++)
                {
                    if (model.sms[i].Type == "1") isSent = true;
                    else isSent = false;
                    result = await _smsGateway.AddSMS(0, model.sms[i].Address, DateTimeOffset.FromUnixTimeMilliseconds(long.Parse(model.sms[i].Date)).DateTime, model.sms[i].Body, isSent);
                }
            }
           
            return Ok(result);
        }

        [HttpPost("sendnewsms")]
        public async Task<IActionResult> SendNewSMS([FromBody] Sms model)
        {
            string result =  NotificationFactory.SendNotificationFromFirebaseCloud(model.Address, model.Body);

            bool isSent = false;
            if (model.Type == "1") isSent = true;
            else isSent = false;
            await _smsGateway.AddSMS(0, model.Address, DateTimeOffset.FromUnixTimeMilliseconds(long.Parse(model.Date)).DateTime, model.Body, isSent);

            return Ok(result);
        }

        [HttpGet("{number}", Name = "GetSMSByContact")]
        public async Task<IActionResult> DisplaySMSByNumber(string number)
        {
            IEnumerable<SMSData> result = await _smsGateway.FindByNumber(number.Replace(" ",string.Empty));
            return Ok(result);
        }

        [HttpGet]
        public async Task<IActionResult> GetSMSList()
        {
            IEnumerable<SMSData> result = await _smsGateway.ListAll();
            return Ok(result);
        }

    }
}
