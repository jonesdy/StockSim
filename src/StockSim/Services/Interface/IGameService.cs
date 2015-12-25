using StockSim.Models.Game;
using System.Collections.Generic;

namespace StockSim.Services.Interface
{
   public interface IGameService
   {
      IEnumerable<GameViewModel> GetOfficialGames();
      IEnumerable<GameViewModel> GetPublicGames();
      IEnumerable<GameViewModel> GetGamesByUsername(string username);
      GameViewModel GetGameByGid(int gid);
      GameViewModel AddNewGame(NewGameViewModel newGame);
      GameViewModel GetGameByTitle(string title);
   }
}