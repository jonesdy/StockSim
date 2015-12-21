using System.ComponentModel.DataAnnotations;

namespace StockSim.Models.Game
{
   public class NewGameViewModel
   {
      [Required]
      public string Title { get; set; }

      [Required]
      [DataType(DataType.Currency)]
      public decimal StartingMoney { get; set; }

      [Display(Name = "Private Game?")]
      public bool Private { get; set; }
   }
}