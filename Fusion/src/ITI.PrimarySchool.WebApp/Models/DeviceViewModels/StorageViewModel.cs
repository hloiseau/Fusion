using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Fusion.WebApp.Models.DeviceViewModels
{
    public class StorageViewModel
    {
        public float TotalGiga { get; set; }

        public float FreeGiga { get; set; }

        public float UsedGiga { get; set; }
    }
}
