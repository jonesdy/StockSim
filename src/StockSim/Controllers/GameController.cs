using Microsoft.AspNet.Mvc;
using Microsoft.Extensions.Logging;
using StockSim.Models.Game;
using StockSim.Services.Interface;
using System.Collections.Generic;

namespace StockSim.Controllers
{
   public class GameController : Controller
   {
      private readonly ILogger _log;
      private readonly IGamesService _gamesService;

      public GameController(ILoggerFactory loggerFactory, IGamesService gamesService)
      {
         _log = loggerFactory.CreateLogger<GameController>();

         _gamesService = gamesService;
      }

      [HttpGet]
      public IActionResult Index()
      {
         // Get logged in user, return public games and the user's games
         /*_log.LogInformation(string.Format("Username: {0}", User.Identity.Name));
         _log.LogInformation(string.Format("Is authenticated: {0}", User.Identity.IsAuthenticated));
         _log.LogInformation(string.Format("Authentication type: {0}", User.Identity.AuthenticationType));*/

         return ViewGames();
      }

      [HttpGet]
      public IActionResult ViewGames()
      {
         return View("ViewGames", new ViewGamesViewModel
         {
            PublicGames = _gamesService.GetPublicGames(),
            UserGames = User.Identity.IsAuthenticated ?
               _gamesService.GetGamesByUsername(User.Identity.Name)
               : new List<GameViewModel>()
         });
      }

      [HttpGet]
      public IActionResult ViewGame(int gid)
      {
         return View(_gamesService.GetGameByGid(gid));
      }

      /*[HttpGet]
      public IActionResult NewGame()
      {
         return View();
      }*/
   }
}