using StockSim.Data.Transfer;
using System.Collections.Generic;

namespace StockSim.Data.Access.Interface
{
   public interface IStockQuoteDao
   {
      StockQuoteDto GetStockQuoteBySymbol(string symbol);
      IList<StockQuoteDto> GetStockQuotesBySymbols(IList<string> symbols);
   }
}