using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace StockSim.Models.Stock
{
   public class BuySellStockViewModel
   {
      public int Gid { get; set; }
      public string GameTitle { get; set; }
      public decimal Balance { get; set; }
      public IList<StockViewModel> PlayerStocks { get; set; }

      [Required]
      [Display(Name = "Ticker Symbol")]
      public string TickerSymbol { get; set; }

      [Required]
      public int Count { get; set; }
   }
}