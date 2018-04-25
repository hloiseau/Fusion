using System.Collections;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace Fusion.WebApp.Models.AccountViewModels
{
    public class Sms
    {
      
        public string Address { get; set; } // number of the sender

        public string Date { get; set; }

        public string Body { get; set; }

        public string Type { get; set; } // message inbox "1" sent "2"
    } 
}