using System;

namespace StockSim.Models.Transaction
{
   public class TransactionViewModel
   {
      public string TickerSymbol { get; set; }
      public int Count { get; set; }
      public decimal Price { get; set; }
      public DateTime Timestamp { get; set; }
      public bool Buy { get; set; }
   }
}