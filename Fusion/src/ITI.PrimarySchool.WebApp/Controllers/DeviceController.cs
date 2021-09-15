using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Fusion.WebApp.Models.AccountViewModels;
using Microsoft.AspNetCore.Mvc;
using Fusion.DAL;
using Fusion.WebApp.Models.DeviceViewModels;
using Microsoft.AspNetCore.SignalR;

namespace Fusion.WebApp.Controllers
{
    [Route("api/[controller]")]
    public class DeviceController : Controller
    {
        private readonly IHubContext<VueHub> _hubContext;
        DeviceGateway _deviceGateway;

        public DeviceController(DeviceGateway deviceGateway, IHubContext<VueHub> hubContext)
        {
            _deviceGateway = deviceGateway;
            _hubContext = hubContext;
        }

        [HttpGet]
        public async Task<IActionResult> GetContactList()
        {
            IEnumerable<DeviceData> result = await _deviceGateway.ListAll();
            return Ok(result);
        }

        [HttpPost("createdevice")]
        public async Task<IActionResult> CreateDevice([FromBody] string name)
        {
            await _deviceGateway.AddDevice(name);
            return Ok();
        }

        [HttpPost("storagesend")]
        public IActionResult RequestStorageDataAndroid()
        {
            string result = NotificationFactory.SendNotificationFromFirebaseCloud("Request accepted", "", "storage");
            return Ok(result);
        }

        [HttpPost("receivestoragedata")]
        public async Task<IActionResult> ReceiveStorageData([FromBody]StorageViewModel storageData)
        {
            await _hubContext.Clients.All.SendAsync("StorageReceive", storageData.UsedGiga, storageData.TotalGiga);
            return Ok();
        }

        [HttpPost("receivebatterydata")]
        public async Task<IActionResult> ReceiveBatteryData([FromBody]BatteryViewModel batteryData)
        {
            await _hubContext.Clients.All.SendAsync("BatteryReceive", batteryData.BatteryPct, batteryData.IsCharging);
            return Ok();
        }
    }
}
