using StockSim.Data.Transfer;

namespace StockSim.Data.Access.Interface
{
   public interface IStockQuoteDao
   {
      StockQuoteDto GetStockQuoteBySymbol(string symbol);
   }
}