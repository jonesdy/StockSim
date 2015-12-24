using System.Collections.Generic;

namespace StockSim.Models.Transaction
{
   public class ViewTransactionsViewModel
   {
      public IList<TransactionViewModel> Transactions { get; set; }
      public int Gid { get; set; }
      public decimal StartingMoney { get; set; }
      public decimal Balance { get; set; }
      public string GameTitle { get; set; }
   }
}