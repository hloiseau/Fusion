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
    public class SMSController : Controller
    {

        readonly SMSGateway _smsGateway;

        public SMSController( SMSGateway smsGateway)
        {
            _smsGateway = smsGateway;
        }

        [HttpPost("recivsms")]
        public async Task<IActionResult> ReciveSMSList([FromBody] SMSViewModel model)
        {
            Result result = null;
            for (int i = 0; i < model.smsLs.Count; i++)
            {
                result = await _smsGateway.AddSMS(1, model.smsLs[i].Extern, model.smsLs[i].Message, model.smsLs[i].direction);
            }
            return Ok(result);
        }

        [HttpPost("SendNewSMS")]
        public async Task<IActionResult> SendNewSMS(SMS model)
        {
            string result =  NotificationFactory.SendNotificationFromFirebaseCloud(model.Extern, model.Message);
            
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
