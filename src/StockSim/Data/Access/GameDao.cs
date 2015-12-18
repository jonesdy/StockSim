using StockSim.Data.Access.Interface;
using StockSim.Data.Transfer;
using System.Collections.Generic;
using System.Linq;

namespace StockSim.Data.Access
{
   public class GameDao : IGameDao
   {
      public void InsertGame(GameDto game)
      {
         using (var db = new StockSimDbContext())
         {
            db.GameDtos.Add(game);

            db.SaveChanges();
         }
      }

      public GameDto SelectGameById(int id)
      {
         using (var db = new StockSimDbContext())
         {
            return db.GameDtos.Where(x => x.Gid == id).FirstOrDefault();
         }
      }

      public IEnumerable<GameDto> SelectPublicGames()
      {
         using (var db = new StockSimDbContext())
         {
            return db.GameDtos.Where(x => !x.Private).ToList();
         }
      }

      public IEnumerable<GameDto> SelectGamesByUsername(string username)
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

      public int SelectPlayerCountFromGameById(int id)
      {
         using (var db = new StockSimDbContext())
         {
            return db.PlayerDtos.Count(x => x.Gid == id);
         }
      }
   }
}