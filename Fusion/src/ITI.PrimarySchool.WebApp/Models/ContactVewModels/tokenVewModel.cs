using System.ComponentModel.DataAnnotations;

namespace Fusion.WebApp.Models.AccountViewModels
{
    public class TokenVewModel
    {
        [Required]
        [Display( Name = "Token" )]
        public string Token { get; set; }        
    }
}
