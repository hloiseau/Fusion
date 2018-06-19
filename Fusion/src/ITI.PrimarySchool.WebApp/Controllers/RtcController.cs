using System.Collections.Generic;
using System.IO;
using System.Threading.Tasks;
using Fusion.DAL;
using Fusion.WebApp.Models.AccountViewModels;
using Fusion.WebApp.Models.RtcViewModels;
using Microsoft.AspNetCore.Http.Connections.Internal;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.SignalR;

namespace Fusion.WebApp.Controllers
{

    [Route("api/[controller]")]
    public class RtcController : Controller
    {
        private readonly IHubContext<VueHub> _hubContext;

        public RtcController(ContactGateway contactGateway, IHubContext<VueHub> hubContext)
        {
            _hubContext = hubContext;
        }

        [HttpPost("descriptionandroid")]
        public async Task DescriptionAndroid([FromBody] RtcViewModel model)
        {
            await _hubContext.Clients.All.SendAsync("Sdp", model.Desc);
        }

        [HttpPost("candidateandroid")]
        public async Task CandidateAndroid([FromBody] RtcViewModel model)
        {

            await _hubContext.Clients.All.SendAsync("Candidate", model.IceCandidate);
        }

        [HttpPost("descriptionvue")]
        public void DescriptionVue([FromBody] RtcViewModel model)
        {
            NotificationFactory.SendNotificationFromFirebaseCloud(null, model.Sdp, "sdp");
        }

        [HttpPost("candidatevue")]
        public void CandidateVue([FromBody] RtcViewModel model)
        {
            NotificationFactory.SendNotificationFromFirebaseCloud(null, model.Candidate, "candidate");
        }
    }
}

/*
 Request.Body.Seek(0, SeekOrigin.Begin);
 StreamReader sr = new StreamReader(Request.Body);
 string body = await sr.ReadToEndAsync();
 */
