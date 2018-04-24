using System.Collections;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace Fusion.WebApp.Models.AccountViewModels
{
    public class Contact
    {
        [Required]
        public string _name { get; set; }
        [Required]
        public string _number { get; set; }

    }
}