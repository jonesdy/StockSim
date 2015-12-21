using StockSim.Data.Access.Interface;
using StockSim.Data.Transfer;
using StockSim.Models.Game;
using StockSim.Services.Interface;
using System;
using System.Collections.Generic;
using System.Linq;

namespace StockSim.Services
{
   public class GamesService : IGamesService
   {
      private readonly IGameDao _gameDao;

      public GamesService(IGameDao gameDao)
      {
         _gameDao = gameDao;
      }

      public IEnumerable<GameViewModel> GetPublicGames()
      {
         return _gameDao.SelectPublicGames().Select(x => new GameViewModel
         {
            Gid = x.Gid,
            Title = x.Title,
            Private = x.Private,
            PlayerCount = _gameDao.SelectPlayerCountFromGameById(x.Gid),
            StartingMoney = x.StartingMoney
         });
      }

      public IEnumerable<GameViewModel> GetGamesByUsername(string username)
      {
         return _gameDao.SelectGamesByUsername(username).Select(x => new GameViewModel
         {
            Gid = x.Gid,
            Title = x.Title,
            Private = x.Private,
            PlayerCount = _gameDao.SelectPlayerCountFromGameById(x.Gid),
            StartingMoney = x.StartingMoney
         });
      }

      public GameViewModel GetGameByGid(int gid)
      {
         var dto = _gameDao.SelectGameById(gid);
         return new GameViewModel
         {
            Gid = dto.Gid,
            Title = dto.Title,
            Private = dto.Private,
            PlayerCount = _gameDao.SelectPlayerCountFromGameById(dto.Gid),
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
            PlayerCount = _gameDao.SelectPlayerCountFromGameById(dto.Gid),
            StartingMoney = dto.StartingMoney
         };
      }
   }
}