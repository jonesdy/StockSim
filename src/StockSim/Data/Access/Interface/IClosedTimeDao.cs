using StockSim.Data.Transfer;
using System.Collections.Generic;

namespace StockSim.Data.Access.Interface
{
   public interface IClosedTimeDao
   {
      IList<ClosedTimeDto> SelectClosedTimes();
   }
}