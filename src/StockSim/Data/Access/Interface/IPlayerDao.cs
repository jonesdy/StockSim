using StockSim.Data.Transfer;

namespace StockSim.Data.Access.Interface
{
   public interface IPlayerDao
   {
      PlayerDto InsertPlayer(PlayerDto player);
      int SelectPlayerCountByGid(int gid);
      bool IsUserInGame(int gid, string username);
   }
}