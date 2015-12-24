using StockSim.Data.Access.Interface;
using StockSim.Data.Transfer;
using StockSim.Models.Stock;
using StockSim.Services.Interface;
using System;
using System.Collections.Generic;
using System.Linq;

namespace StockSim.Services
{
   public class StockService : IStockService
   {
      private readonly IPlayerDao _playerDao;
      private readonly IStockDao _stockDao;
      private readonly IStockQuoteDao _stockQuoteDao;
      private readonly ITransactionDao _transactionDao;

      public StockService(IPlayerDao playerDao, IStockDao stockDao, IStockQuoteDao stockQuoteDao, ITransactionDao transactionDao)
      {
         _playerDao = playerDao;
         _stockDao = stockDao;
         _stockQuoteDao = stockQuoteDao;
         _transactionDao = transactionDao;
      }

      public bool BuyStock(int gid, string username, string tickerSymbol, int count)
      {
         var player = _playerDao.SelectPlayerByGidAndUsername(gid, username);
         if (player == null)
         {
            throw new InvalidOperationException("User is not in this game!");
         }

         var quote = _stockQuoteDao.GetStockQuoteBySymbol(tickerSymbol);
         if(quote == null)
         {
            throw new InvalidOperationException("Unable to get stock quote!");
         }

         if (count <= 0)
         {
            throw new InvalidOperationException("Count must be greater than 0!");
         }

         var totalCost = quote.Cost * count;

         if(player.Balance < totalCost)
         {
            throw new InvalidOperationException("User does not have enough money!");
         }

         // Good to go
         var stock = _stockDao.SelectStockByPidAndTickerSymbol(player.Pid, tickerSymbol);
         if (stock == null)
         {
            stock = _stockDao.InsertNewStock(new StockDto
            {
               TickerSymbol = quote.Symbol,
               Pid = player.Pid,
               Count = count
            });
         }
         else
         {
            _stockDao.UpdateStockCountBySid(stock.Sid, stock.Count + count);
         }
         _playerDao.UpdateBalanceByPid(player.Pid, player.Balance - totalCost);

         _transactionDao.InsertTransaction(new TransactionDto
         {
            Sid = stock.Sid,
            Count = count,
            Price = quote.Cost,
            Timestamp = DateTime.UtcNow,
            Buy = true
         });
         return true;
      }

      public bool SellStock(int gid, string username, string tickerSymbol, int count)
      {
         var player = _playerDao.SelectPlayerByGidAndUsername(gid, username);
         if (player == null)
         {
            throw new InvalidOperationException("User is not in this game!");
         }

         var quote = _stockQuoteDao.GetStockQuoteBySymbol(tickerSymbol);
         if (quote == null)
         {
            throw new InvalidOperationException("Unable to get stock quote!");
         }

         if (count <= 0)
         {
            throw new InvalidOperationException("Count must be greater than 0!");
         }

         var stock = _stockDao.SelectStockByPidAndTickerSymbol(player.Pid, tickerSymbol);
         if(stock == null || stock.Count < count)
         {
            throw new InvalidOperationException("User does not have enough stocks to sell!");
         }

         // Good to go
         var totalCost = quote.Cost * count;
         _stockDao.UpdateStockCountBySid(stock.Sid, stock.Count - count);
         _playerDao.UpdateBalanceByPid(player.Pid, player.Balance + totalCost);
         
         // Don't delete the stock because we need it for transactions

         _transactionDao.InsertTransaction(new TransactionDto
         {
            Sid = stock.Sid,
            Count = count,
            Price = quote.Cost,
            Timestamp = DateTime.UtcNow,
            Buy = false
         });
         return true;
      }

      public IList<StockViewModel> GetPlayerStocks(int gid, string username)
      {
         var player = _playerDao.SelectPlayerByGidAndUsername(gid, username);
         if (player == null)
         {
            return new List<StockViewModel>();
         }

         var stocks = _stockDao.SelectStocksByPid(player.Pid);
         if(stocks == null || !stocks.Any())
         {
            return new List<StockViewModel>();
         }

         return stocks.Select(x => new StockViewModel
         {
            TickerSymbol = x.TickerSymbol,
            Count = x.Count
         }).Where(x => x.Count != 0).ToList();
      }
   }
}