using StockSim.Data.Access.Interface;
using StockSim.Data.Transfer;
using System.Collections.Generic;
using System.Linq;

namespace StockSim.Data.Access
{
   public class StockDao : IStockDao
   {
      public StockDto InsertNewStock(StockDto stock)
      {
         using(var db = new StockSimDbContext())
         {
            var newStock = db.StockDtos.Add(stock).Entity;
            db.SaveChanges();
            return newStock;
         }
      }

      public IList<StockDto> SelectStocksByPid(int pid)
      {
         using (var db = new StockSimDbContext())
         {
            return db.StockDtos.Where(x => x.Pid == pid).ToList();
         }
      }

      public int UpdateStockCountBySid(int sid, int count)
      {
         using (var db = new StockSimDbContext())
         {
            var existing = db.StockDtos.First(x => x.Sid == sid);
            existing.Count = count;
            db.SaveChanges();
            return db.StockDtos.First(x => x.Sid == sid).Count;
         }
      }

      public bool DeleteStockBySid(int sid)
      {
         using (var db = new StockSimDbContext())
         {
            var stock = db.StockDtos.FirstOrDefault(x => x.Sid == sid);
            if(stock != null)
            {
               db.StockDtos.Remove(stock);
               db.SaveChanges();
               return true;
            }
         }
         return false;
      }

      public StockDto SelectStockByPidAndTickerSymbol(int pid, string tickerSymbol)
      {
         using (var db = new StockSimDbContext())
         {
            return db.StockDtos.FirstOrDefault(x => x.Pid == pid && x.TickerSymbol == tickerSymbol);
         }
      }
   }
}