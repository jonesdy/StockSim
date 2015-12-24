using StockSim.Data.Transfer;
using System.Collections.Generic;

namespace StockSim.Data.Access.Interface
{
   public interface IStockDao
   {
      StockDto InsertNewStock(StockDto stock);
      IList<StockDto> SelectStocksByPid(int pid);
      int UpdateStockCountBySid(int sid, int count);
      bool DeleteStockBySid(int sid);
      StockDto SelectStockByPidAndTickerSymbol(int pid, string tickerSymbol);
   }
}