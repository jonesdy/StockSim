using StockSim.Models.Stock;
using System.Collections.Generic;

namespace StockSim.Services.Interface
{
   public interface IStockService
   {
      bool BuyStock(int gid, string username, string tickerSymbol, int count);
      bool SellStock(int gid, string username, string tickerSymbol, int count);
      IList<StockViewModel> GetPlayerStocks(int gid, string username);
   }
}