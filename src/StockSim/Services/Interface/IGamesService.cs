using StockSim.Models.Game;
using System.Collections.Generic;

namespace StockSim.Services.Interface
{
   public interface IGamesService
   {
      IEnumerable<GameViewModel> GetPublicGames();
      IEnumerable<GameViewModel> GetGamesByUsername(string username);
      GameViewModel GetGameByGid(int gid);
   }
}