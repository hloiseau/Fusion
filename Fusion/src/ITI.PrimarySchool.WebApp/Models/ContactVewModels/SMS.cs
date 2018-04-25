using System.Collections;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace Fusion.WebApp.Models.AccountViewModels
{
    public class SMS
    {
        [Required]
        public string UsersId { get; set; } // user of app
        [Required]
        public string Extern { get; set; } // number of the guy
        [Required]
        public string Time { get; set; }

        public string Message { get; set; }
        [Required]
        public bool direction { get; set; } // message for userId ou extern VALUE 1 j'ai envoyer 2 j'ai recus
    } 
}