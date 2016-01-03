using StockSim.Data.Access.Interface;
using StockSim.Data.Transfer;
using System.Collections.Generic;
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

      public bool DeletePlayerByGidAndUsername(int gid, string username)
      {
         using (var db = new StockSimDbContext())
         {
            var player = db.PlayerDtos.FirstOrDefault(x => x.Gid == gid && x.Username == username);
            if(player != null)
            {
               db.PlayerDtos.Remove(player);
               db.SaveChanges();
               return true;
            }
         }
         return false;
      }

      public PlayerDto SelectPlayerByGidAndUsername(int gid, string username)
      {
         using (var db = new StockSimDbContext())
         {
            return db.PlayerDtos.FirstOrDefault(x => x.Gid == gid && x.Username == username);
         }
      }

      public decimal UpdateBalanceByPid(int pid, decimal balance)
      {
         using (var db = new StockSimDbContext())
         {
            var existing = db.PlayerDtos.First(x => x.Pid == pid);
            existing.Balance = balance;
            db.SaveChanges();
            return db.PlayerDtos.First(x => x.Pid == pid).Balance;
         }
      }

      public IList<PlayerDto> SelectPlayersByGid(int gid)
      {
         using (var db = new StockSimDbContext())
         {
            return db.PlayerDtos.Where(x => x.Gid == gid).ToList();
         }
      }
   }
}