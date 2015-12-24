using StockSim.Data.Access.Interface;
using StockSim.Models.Transaction;
using StockSim.Services.Interface;
using System;
using System.Collections.Generic;
using System.Linq;

namespace StockSim.Services
{
   public class TransactionService : ITransactionService
   {
      private readonly ITransactionDao _transactionDao;
      private readonly IPlayerDao _playerDao;
      private readonly IStockDao _stockDao;

      public TransactionService(ITransactionDao transactionDao, IPlayerDao playerDao, IStockDao stockDao)
      {
         _transactionDao = transactionDao;
         _playerDao = playerDao;
         _stockDao = stockDao;
      }

      public IList<TransactionViewModel> GetTransactionsForGameAndUser(int gid, string username)
      {
         var player = _playerDao.SelectPlayerByGidAndUsername(gid, username);
         if(player == null)
         {
            throw new InvalidOperationException("User is not in this game!");
         }
         var stocks = _stockDao.SelectStocksByPid(player.Pid);
         var transactions = new List<TransactionViewModel>();
         foreach(var stock in stocks)
         {
            transactions.AddRange(_transactionDao.SelectTransactionsBySid(stock.Sid).Select(x => new TransactionViewModel
            {
               TickerSymbol = stock.TickerSymbol,
               Count = x.Count,
               Price = x.Price,
               Timestamp = x.Timestamp,
               Buy = x.Buy
            }));
         }
         return transactions;
      }
   }
}