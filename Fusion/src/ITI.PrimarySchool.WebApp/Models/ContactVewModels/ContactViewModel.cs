using System.ComponentModel.DataAnnotations;

namespace Fusion.WebApp.Models.AccountViewModels
{
    public class ContactViewModel
    {
        [Required]
        [Display( Name = "Token" )]
        public string Token { get; set; }

        [Required]
        [Display( Name = "Confirm Token")]
        [Compare("Token", ErrorMessage = "The Token and confirmation Token do not match.")]
        public string ConfirmToken { get; set; }
    }
}
