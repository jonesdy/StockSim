using Microsoft.AspNet.Mvc;
using Microsoft.Extensions.Logging;
using StockSim.Models.Stock;
using StockSim.Services.Interface;
using System;

namespace StockSim.Controllers
{
   public class StockController : Controller
   {
      private readonly ILogger _log;
      private readonly IStockService _stockService;
      private readonly IPlayerService _playerService;
      private readonly IGameService _gameService;
      private readonly IClosedTimeService _closedTimeService;

      public StockController(ILoggerFactory loggerFactory, IStockService stockService, IPlayerService playerService, IGameService gameService, IClosedTimeService closedTimeService)
      {
         _log = loggerFactory.CreateLogger<StockController>();

         _stockService = stockService;
         _playerService = playerService;
         _gameService = gameService;
         _closedTimeService = closedTimeService;
      }

      [HttpGet]
      public IActionResult BuyStock(int gid)
      {
         if (!User.Identity.IsAuthenticated)
         {
            return RedirectToAction("ViewGame", "Game");
         }
         if(!_playerService.IsUserInGame(gid, User.Identity.Name))
         {
            return RedirectToAction("ViewGame", "Game");
         }

         return View(new BuySellStockViewModel
         {
            Gid = gid,
            GameTitle = _gameService.GetGameByGid(gid).Title,
            Balance = _playerService.GetPlayerBalance(gid, User.Identity.Name),
            PlayerStocks = _stockService.GetPlayerStocks(gid, User.Identity.Name),
            ClosedReason = _closedTimeService.IsStockMarketOpen()
         });
      }

      [HttpPost]
      public IActionResult BuyStock(BuySellStockViewModel model)
      {
         if (!User.Identity.IsAuthenticated)
         {
            return RedirectToAction("ViewGames", "Game");
         }
         if(!_playerService.IsUserInGame(model.Gid, User.Identity.Name))
         {
            return RedirectToAction("ViewGames", "Game");
         }
         if(_closedTimeService.IsStockMarketOpen() != null)
         {
            return RedirectToAction("ViewGames", "Game");
         }

         try
         {
            _stockService.BuyStock(model.Gid, User.Identity.Name, model.TickerSymbol.ToUpperInvariant(), model.Count);
            return RedirectToAction("BuyStockConfirm", new { gid = model.Gid });
         }
         catch (Exception e)
         {
            _log.LogError($"{e.Message}\n{e.StackTrace}");
         }
         return RedirectToAction("BuyStockConfirm", null);
      }

      [HttpGet]
      public IActionResult BuyStockConfirm(int? gid)
      {
         return View(gid);
      }

      [HttpGet]
      public IActionResult SellStock(int gid)
      {
         if (!User.Identity.IsAuthenticated)
         {
            return RedirectToAction("ViewGames", "Game");
         }
         if (!_playerService.IsUserInGame(gid, User.Identity.Name))
         {
            return RedirectToAction("ViewGames", "Game");
         }

         return View(new BuySellStockViewModel
         {
            Gid = gid,
            GameTitle = _gameService.GetGameByGid(gid).Title,
            Balance = _playerService.GetPlayerBalance(gid, User.Identity.Name),
            PlayerStocks = _stockService.GetPlayerStocks(gid, User.Identity.Name),
            ClosedReason = _closedTimeService.IsStockMarketOpen()
         });
      }

      [HttpPost]
      public IActionResult SellStock(BuySellStockViewModel model)
      {
         if (!User.Identity.IsAuthenticated)
         {
            return RedirectToAction("ViewGames", "Game");
         }
         if (!_playerService.IsUserInGame(model.Gid, User.Identity.Name))
         {
            return RedirectToAction("ViewGames", "Game");
         }
         if(_closedTimeService.IsStockMarketOpen() != null)
         {
            return RedirectToAction("ViewGames", "Game");
         }

         try
         {
            _stockService.SellStock(model.Gid, User.Identity.Name, model.TickerSymbol.ToUpperInvariant(), model.Count);
            return RedirectToAction("SellStockConfirm", new { gid = model.Gid });
         }
         catch(Exception e)
         {
            _log.LogError($"{e.Message}\n{e.StackTrace}");
         }
         return RedirectToAction("SellStockConfirm", null);
      }

      [HttpGet]
      public IActionResult SellStockConfirm(int? gid)
      {
         return View(gid);
      }
   }
}