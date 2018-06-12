using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.SignalR;

namespace Fusion.WebApp
{
    public class VueHub : Hub
    {
        public async Task Send()
        {
            if (Clients != null) await Clients.All.SendAsync("Send", "loltest");
        }

        public async Task SmsReceived()
        {
            await Clients.All.SendAsync("Test", "name", "message");
        }
        public override async Task OnConnectedAsync()
        {
            await Groups.AddToGroupAsync(Context.ConnectionId, "SignalR Users");
            await base.OnConnectedAsync();
        }

        public override async Task OnDisconnectedAsync(Exception exception)
        {
            await Groups.RemoveFromGroupAsync(Context.ConnectionId, "SignalR Users");
            await base.OnDisconnectedAsync(exception);
        }
    }
}
