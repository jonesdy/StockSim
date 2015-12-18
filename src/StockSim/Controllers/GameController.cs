using Microsoft.AspNet.Mvc;
using Microsoft.Extensions.Logging;
using StockSim.Data.Access.Interface;
using StockSim.Data.Transfer;
using StockSim.Models;
using System.Collections.Generic;

namespace StockSim.Controllers
{
   public class GameController : Controller
   {
      private readonly IGameDao _gameDao;
      private readonly ILogger _log;

      public GameController(ILoggerFactory loggerFactory, IGameDao gameDao)
      {
         _gameDao = gameDao;
         _log = loggerFactory.CreateLogger<GameController>();
      }

      [HttpGet]
      public IActionResult Index()
      {
         // Get logged in user, return public games and the user's games
         /*_log.LogInformation(string.Format("Username: {0}", User.Identity.Name));
         _log.LogInformation(string.Format("Is authenticated: {0}", User.Identity.IsAuthenticated));
         _log.LogInformation(string.Format("Authentication type: {0}", User.Identity.AuthenticationType));*/

         return View(new ViewGamesModel
         {
            PublicGames = _gameDao.SelectPublicGames() ?? new List<GameDto>(),
            UserGames = User.Identity.IsAuthenticated ?
               (_gameDao.SelectGamesByUsername(User.Identity.Name) ?? new List<GameDto>())
               : new List<GameDto>()
         });
      }
   }
}