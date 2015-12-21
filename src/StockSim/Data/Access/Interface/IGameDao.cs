using StockSim.Data.Transfer;
using System.Collections.Generic;

namespace StockSim.Data.Access.Interface
{
   public interface IGameDao
   {
      GameDto InsertGame(GameDto game);
      GameDto SelectGameById(int id);
      IEnumerable<GameDto> SelectPublicGames();
      IEnumerable<GameDto> SelectGamesByUsername(string username);
      int SelectPlayerCountFromGameById(int id);
      GameDto SelectGameByTitle(string title);
   }
}