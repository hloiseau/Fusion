using System.Collections;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace Fusion.WebApp.Models.AccountViewModels
{
    public class Contact
    {
        [Required]
        public string Name { get; set; }
        [Required]
        public string Number { get; set; }

    }
}