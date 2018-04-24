using System.Collections;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using Microsoft.AspNetCore.Mvc.ViewFeatures.Internal;

namespace Fusion.WebApp.Models.AccountViewModels
{
    public class ContactVewModel
    {
        public List<Contact> ContactLs { get; set; } 
    }
}
