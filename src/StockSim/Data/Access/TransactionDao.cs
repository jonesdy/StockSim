using StockSim.Data.Access.Interface;
using StockSim.Data.Transfer;
using System.Collections.Generic;
using System.Linq;

namespace StockSim.Data.Access
{
   public class TransactionDao : ITransactionDao
   {
      public TransactionDto InsertTransaction(TransactionDto transaction)
      {
         using (var db = new StockSimDbContext())
         {
            var newTransaction = db.TransactionDtos.Add(transaction);
            db.SaveChanges();
            return newTransaction.Entity;
         }
      }

      public IList<TransactionDto> SelectTransactionsBySid(int sid)
      {
         using (var db = new StockSimDbContext())
         {
            return db.TransactionDtos.Where(x => x.Sid == sid).ToList();
         }
      }
   }
}