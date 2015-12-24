using StockSim.Data.Transfer;
using System.Collections.Generic;
using System.Linq;

namespace StockSim.Data.Access
{
   public class ClosedTimeDao
   {
      public IList<ClosedTimeDto> SelectClosedTimes()
      {
         using (var db = new StockSimDbContext())
         {
            return db.ClosedTimeDtos.ToList();
         }
      }
   }
}