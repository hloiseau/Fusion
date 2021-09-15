using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Fusion.WebApp.Models.RtcViewModels
{
    public class IceCandidate
    {
        public string sdpMid { get; set; } 

        public int sdpMLineIndex { get; set; } 

        public string sdp { get; set; }
    }
}
