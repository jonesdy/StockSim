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

      public StockController(ILoggerFactory loggerFactory, IStockService stockService)
      {
         _log = loggerFactory.CreateLogger<StockController>();

         _stockService = stockService;
      }

      [HttpGet]
      public IActionResult BuyStock(int gid)
      {
         return View(new BuySellStockViewModel
         {
            Gid = gid
         });
      }

      [HttpPost]
      public IActionResult BuyStock(BuySellStockViewModel model)
      {
         if (!User.Identity.IsAuthenticated)
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
         return View(new BuySellStockViewModel
         {
            Gid = gid
         });
      }

      [HttpPost]
      public IActionResult SellStock(BuySellStockViewModel model)
      {
         if (!User.Identity.IsAuthenticated)
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