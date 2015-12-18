using StockSim.Data.Transfer;
using System.Collections.Generic;

namespace StockSim.Models
{
   public class ViewGamesModel
   {
      public IEnumerable<GameDto> PublicGames { get; set; }
      public IEnumerable<GameDto> UserGames { get; set; }
   }
}