using StockSim.Data.Transfer;
using System.Collections.Generic;

namespace StockSim.Data.Access.Interface
{
   public interface IPlayerDao
   {
      PlayerDto InsertPlayer(PlayerDto player);
      int SelectPlayerCountByGid(int gid);
      bool IsUserInGame(int gid, string username);
      bool DeletePlayerByGidAndUsername(int gid, string username);
      PlayerDto SelectPlayerByGidAndUsername(int gid, string username);
      decimal UpdateBalanceByPid(int pid, decimal balance);
      IList<PlayerDto> SelectPlayersByGid(int gid);
   }
}