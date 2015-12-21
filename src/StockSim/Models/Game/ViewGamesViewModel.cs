using System.Collections.Generic;

namespace StockSim.Models.Game
{
   public class ViewGamesViewModel
   {
      public IEnumerable<GameViewModel> PublicGames { get; set; }
      public IEnumerable<GameViewModel> UserGames { get; set; }
   }
}