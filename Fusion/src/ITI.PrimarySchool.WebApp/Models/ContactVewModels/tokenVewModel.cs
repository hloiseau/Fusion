using System.ComponentModel.DataAnnotations;

namespace Fusion.WebApp.Models.AccountViewModels
{
    public class TokenVewModel
    {
        [Required]
        public string Token { get; set; }        
    }
}
