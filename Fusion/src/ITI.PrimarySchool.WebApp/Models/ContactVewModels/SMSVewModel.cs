using System.Collections;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using Microsoft.AspNetCore.Mvc.ViewFeatures.Internal;

namespace Fusion.WebApp.Models.AccountViewModels
{
    public class SMSVewModel
    {
        public List<Sms> sms { get; set; }
    }
}
