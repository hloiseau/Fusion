using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Fusion.WebApp.Models.DeviceViewModels
{
    public class BatteryViewModel
    {
        public float BatteryPct { get; set; }

        public bool IsCharging { get; set; }

    }
}
