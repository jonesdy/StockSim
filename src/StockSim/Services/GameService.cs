using StockSim.Data.Access.Interface;
using StockSim.Data.Transfer;
using StockSim.Models.Game;
using StockSim.Services.Interface;
using System;
using System.Collections.Generic;
using System.Linq;

namespace StockSim.Services
{
   public class GameService : IGameService
   {
      private readonly IGameDao _gameDao;
      private readonly IPlayerDao _playerDao;
      private readonly IStockQuoteDao _stockQuoteDao;
      private readonly IStockDao _stockDao;

      public GameService(IGameDao gameDao, IPlayerDao playerDao, IStockQuoteDao stockQuoteDao, IStockDao stockDao)
      {
         _gameDao = gameDao;
         _playerDao = playerDao;
         _stockQuoteDao = stockQuoteDao;
         _stockDao = stockDao;
      }

      public IEnumerable<GameViewModel> GetOfficialGames()
      {
         return _gameDao.SelectOfficialGames().Select(x => new GameViewModel
         {
            Gid = x.Gid,
            Title = x.Title,
            Private = x.Private,
            PlayerCount = _playerDao.SelectPlayerCountByGid(x.Gid),
            StartingMoney = x.StartingMoney,
            Official = x.Official
         });
      }

      public IEnumerable<GameViewModel> GetPublicGames()
      {
         return _gameDao.SelectPublicGames().Select(x => new GameViewModel
         {
            Gid = x.Gid,
            Title = x.Title,
            Private = x.Private,
            PlayerCount = _playerDao.SelectPlayerCountByGid(x.Gid),
            StartingMoney = x.StartingMoney,
            Official = x.Official
         });
      }

      public IEnumerable<GameViewModel> GetGamesByUsername(string username)
      {
         return _gameDao.SelectGamesByUsername(username).Select(x => new GameViewModel
         {
            Gid = x.Gid,
            Title = x.Title,
            Private = x.Private,
            PlayerCount = _playerDao.SelectPlayerCountByGid(x.Gid),
            StartingMoney = x.StartingMoney,
            Official = x.Official
         });
      }

      public GameViewModel GetGameByGid(int gid)
      {
         var dto = _gameDao.SelectGameByGid(gid);
         return new GameViewModel
         {
            Gid = dto.Gid,
            Title = dto.Title,
            Private = dto.Private,
            PlayerCount = _playerDao.SelectPlayerCountByGid(dto.Gid),
            StartingMoney = dto.StartingMoney
         };
      }

      public GameViewModel AddNewGame(NewGameViewModel newGame)
      {
         var existing = GetGameByTitle(newGame.Title);
         if(existing != null)
         {
            throw new InvalidOperationException($"Game with title {newGame.Title} already exists!");
         }
         var game = _gameDao.InsertGame(new GameDto
         {
            Title = newGame.Title,
            StartingMoney = newGame.StartingMoney,
            Private = newGame.Private,
            StartTimestamp = DateTime.UtcNow
         });

         return GetGameByGid(game.Gid);
      }

      public GameViewModel GetGameByTitle(string title)
      {
         var dto = _gameDao.SelectGameByTitle(title);
         if(dto == null)
         {
            return null;
         }
         return new GameViewModel
         {
            Gid = dto.Gid,
            Title = dto.Title,
            Private = dto.Private,
            PlayerCount = _playerDao.SelectPlayerCountByGid(dto.Gid),
            StartingMoney = dto.StartingMoney
         };
      }

      public IList<LeaderboardViewModel> GetLeaderboardsByGid(int gid)
      {
         // Get all the players
         var players = _playerDao.SelectPlayersByGid(gid);

         // Get all the possible stocks
         var stocks = new List<StockDto>();
         foreach(var player in players)
         {
            stocks.AddRange(_stockDao.SelectStocksByPid(player.Pid));
         }
         var symbols = stocks.Select(x => x.TickerSymbol).Distinct().ToList();

         // Get those quotes
         var quotes = _stockQuoteDao.GetStockQuotesBySymbols(symbols);

         // Mash stuff together to get estimated worth
         return players.Select(player => new LeaderboardViewModel
         {
            Username = player.Username,
            EstimatedWorth = stocks.Where(stock => stock.Pid == player.Pid)
               .Select(stock => quotes.First(quote => quote.Symbol == stock.TickerSymbol).Cost * stock.Count).Sum()
               + player.Balance
         }).ToList();
      }
   }
}