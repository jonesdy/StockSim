using Microsoft.AspNet.Mvc;
using Microsoft.Extensions.Logging;
using StockSim.Models.Transaction;
using StockSim.Services.Interface;
using System;

namespace StockSim.Controllers
{
   public class TransactionController : Controller
   {
      private readonly ILogger _log;
      private readonly ITransactionService _transactionService;
      private readonly IPlayerService _playerService;
      private readonly IGameService _gameService;

      public TransactionController(ILoggerFactory loggerFactory, ITransactionService transactionService, IPlayerService playerService, IGameService gameService)
      {
         _log = loggerFactory.CreateLogger<TransactionController>();

         _transactionService = transactionService;
         _playerService = playerService;
         _gameService = gameService;
      }

      public IActionResult ViewTransactions(int gid)
      {
         if (!User.Identity.IsAuthenticated)
         {
            return RedirectToAction("ViewGame", "Game", gid);
         }
         var game = _gameService.GetGameByGid(gid);

         try
         {
            return View(new ViewTransactionsViewModel
            {
               Transactions = _transactionService.GetTransactionsForGameAndUser(gid, User.Identity.Name),
               Gid = gid,
               StartingMoney = game.StartingMoney,
               Balance = _playerService.GetPlayerBalance(gid, User.Identity.Name),
               GameTitle = game.Title
            });
         }
         catch(Exception e)
         {
            _log.LogError($"{e.Message}\n{e.StackTrace}");
         }
         return RedirectToAction("ViewGame", "Game", gid);
      }
   }
}