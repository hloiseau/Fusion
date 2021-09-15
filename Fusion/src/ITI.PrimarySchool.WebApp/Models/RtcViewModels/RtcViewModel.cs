using System.Collections;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace Fusion.WebApp.Models.RtcViewModels
{
    public class RtcViewModel
    {
      
        public SessionDescription Desc { get; set; }

        public IceCandidate IceCandidate { get; set; }

        public string Sdp { get; set; }

        public string Candidate { get; set; }

    } 
}