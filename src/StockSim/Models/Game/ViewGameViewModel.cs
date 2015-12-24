using StockSim.Models.Stock;
using System.Collections.Generic;

namespace StockSim.Models.Game
{
   public class ViewGameViewModel
   {
      public GameViewModel Game { get; set; }
      public IList<StockViewModel> PlayerStocks { get; set; }
      public bool UserCanJoin { get; set; }
      public decimal? Balance { get; set; }
   }
}