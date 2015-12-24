using StockSim.Models.Transaction;
using System.Collections.Generic;

namespace StockSim.Services.Interface
{
   public interface ITransactionService
   {
      IList<TransactionViewModel> GetTransactionsForGameAndUser(int gid, string username);
   }
}