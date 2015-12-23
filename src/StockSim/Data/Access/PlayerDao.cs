using StockSim.Data.Access.Interface;
using StockSim.Data.Transfer;
using System.Linq;

namespace StockSim.Data.Access
{
   public class PlayerDao : IPlayerDao
   {
      public PlayerDto InsertPlayer(PlayerDto player)
      {
         using (var db = new StockSimDbContext())
         {
            var ret = db.PlayerDtos.Add(player);

            db.SaveChanges();

            return ret.Entity;
         }
      }

      public int SelectPlayerCountByGid(int gid)
      {
         using (var db = new StockSimDbContext())
         {
            return db.PlayerDtos.Count(x => x.Gid == gid);
         }
      }

      public bool IsUserInGame(int gid, string username)
      {
         using (var db = new StockSimDbContext())
         {
            var player = db.PlayerDtos.FirstOrDefault(x => x.Gid == gid && x.Username == username);
            return player != null;
         }
      }
   }
}