using StockSim.Data.Transfer;
using System.Collections.Generic;

namespace StockSim.Data.Access.Interface
{
   public interface IGameDao
   {
      GameDto InsertGame(GameDto game);
      GameDto SelectGameByGid(int gid);
      IList<GameDto> SelectPublicGames();
      IList<GameDto> SelectGamesByUsername(string username);
      GameDto SelectGameByTitle(string title);
      IList<GameDto> SelectOfficialGames();
   }
}