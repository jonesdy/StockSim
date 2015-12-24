using System.ComponentModel.DataAnnotations;

namespace StockSim.Models.Game
{
   public class NewGameViewModel
   {
      [Required]
      public string Title { get; set; }

      [Required]
      [DataType(DataType.Currency)]
      [Display(Name = "Starting Money")]
      public decimal StartingMoney { get; set; }

      [Display(Name = "Private Game?")]
      public bool Private { get; set; }
   }
}