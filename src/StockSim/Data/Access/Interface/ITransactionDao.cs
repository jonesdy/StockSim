using StockSim.Data.Transfer;
using System.Collections.Generic;

namespace StockSim.Data.Access.Interface
{
   public interface ITransactionDao
   {
      TransactionDto InsertTransaction(TransactionDto transaction);
      IList<TransactionDto> SelectTransactionsBySid(int sid);
   }
}