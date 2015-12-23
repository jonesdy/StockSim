using StockSim.Data.Access.Interface;
using StockSim.Data.Transfer;
using System.Collections.Generic;
using System.Linq;

namespace StockSim.Data.Access
{
   public class GameDao : IGameDao
   {
      public GameDto InsertGame(GameDto game)
      {
         using (var db = new StockSimDbContext())
         {
            var ret = db.GameDtos.Add(game);

            db.SaveChanges();

            return ret.Entity;
         }
      }

      public GameDto SelectGameByGid(int gid)
      {
         using (var db = new StockSimDbContext())
         {
            return db.GameDtos.FirstOrDefault(x => x.Gid == gid);
         }
      }

      public IList<GameDto> SelectPublicGames()
      {
         using (var db = new StockSimDbContext())
         {
            return db.GameDtos.Where(x => !x.Private).ToList();
         }
      }

      public IList<GameDto> SelectGamesByUsername(string username)
      {
         using (var db = new StockSimDbContext())
         {
            return (from game in db.GameDtos
                    join player in db.PlayerDtos on game.Gid equals player.Gid
                    where player.Username == username
                    select new GameDto
                    {
                       Gid = game.Gid,
                       Title = game.Title,
                       StartingMoney = game.StartingMoney,
                       Private = game.Private,
                       StartTimestamp = game.StartTimestamp
                    }).ToList();
         }
      }

      public GameDto SelectGameByTitle(string title)
      {
         using(var db = new StockSimDbContext())
         {
            return db.GameDtos.FirstOrDefault(x => x.Title == title);
         }
      }
   }
}