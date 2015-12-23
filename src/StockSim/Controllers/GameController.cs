﻿using Microsoft.AspNet.Mvc;
using Microsoft.Extensions.Logging;
using StockSim.Models.Game;
using StockSim.Services.Interface;
using System;
using System.Collections.Generic;

namespace StockSim.Controllers
{
   public class GameController : Controller
   {
      private readonly ILogger _log;
      private readonly IGameService _gameService;
      private readonly IPlayerService _playerService;

      public GameController(ILoggerFactory loggerFactory, IGameService gameService, IPlayerService playerService)
      {
         _log = loggerFactory.CreateLogger<GameController>();

         _gameService = gameService;
         _playerService = playerService;
      }

      [HttpGet]
      public IActionResult Index()
      {
         // Get logged in user, return public games and the user's games
         /*_log.LogInformation(string.Format("Username: {0}", User.Identity.Name));
         _log.LogInformation(string.Format("Is authenticated: {0}", User.Identity.IsAuthenticated));
         _log.LogInformation(string.Format("Authentication type: {0}", User.Identity.AuthenticationType));*/

         return RedirectToAction("ViewGames");
      }

      [HttpGet]
      public IActionResult ViewGames()
      {
         return View(new ViewGamesViewModel
         {
            PublicGames = _gameService.GetPublicGames(),
            UserGames = User.Identity.IsAuthenticated ?
               _gameService.GetGamesByUsername(User.Identity.Name)
               : new List<GameViewModel>()
         });
      }

      [HttpGet]
      public IActionResult ViewGame(int gid)
      {
         var game = _gameService.GetGameByGid(gid);
         ViewBag.UserCanJoin = User.Identity.IsAuthenticated && !_playerService.IsUserInGame(game.Gid, User.Identity.Name) && !game.Private;
         return View(game);
      }

      [HttpGet]
      public IActionResult NewGame()
      {
         if (User.Identity.IsAuthenticated)
         {
            return View(new NewGameViewModel());
         }
         return RedirectToAction("ViewGames");
      }

      [HttpPost]
      public IActionResult NewGame(NewGameViewModel newGame)
      {
         if (!User.Identity.IsAuthenticated)
         {
            // The user should only get into this if they are messing with stuff
            return RedirectToAction("ViewGames");
         }

         try
         {
            var game = _gameService.AddNewGame(newGame);
            _playerService.AddNewPlayerToGame(game.Gid, User.Identity.Name);
            return RedirectToAction("NewGameConfirm", new { gid = game.Gid });
         }
         catch(Exception e)
         {
            _log.LogError($"{e.Message}\n{e.StackTrace}");
         }
         return RedirectToAction("NewGameConfirm", null);
      }

      [HttpGet]
      public IActionResult NewGameConfirm(int? gid)
      {
         return View(gid);
      }

      [HttpGet]
      public IActionResult JoinGame(int gid)
      {
         if (!User.Identity.IsAuthenticated)
         {
            return RedirectToAction("ViewGames");
         }

         try
         {
            if (_playerService.IsUserInGame(gid, User.Identity.Name))
            {
               return View(null);
            }
            _playerService.AddNewPlayerToGame(gid, User.Identity.Name);
            return View(gid);
         }
         catch (Exception e)
         {
            _log.LogError($"{e.Message}\n{e.StackTrace}");
         }
         return View(null);
      }
   }
}