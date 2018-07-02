using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Fusion.WebApp.Models.AccountViewModels;
using Microsoft.AspNetCore.Mvc;
using Fusion.DAL;

namespace Fusion.WebApp.Controllers
{
    [Route("api/[controller]")]
    public class DeviceController : Controller
    {
        DeviceGateway _deviceGateway;

        public DeviceController(DeviceGateway deviceGateway)
        {
            _deviceGateway = deviceGateway;
        }

        [HttpPost("createdevice")]
        public async Task<IActionResult> CreateDevice([FromBody] string name)
        {
            await _deviceGateway.AddDevice(name);
            return Ok();
        }
    }
}
