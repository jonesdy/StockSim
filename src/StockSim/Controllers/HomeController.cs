using Microsoft.AspNet.Mvc;
using Microsoft.Extensions.Logging;
using StockSim.Data.Access;
using StockSim.Data.Transfer;
using System;

namespace StockSim.Controllers
{
   public class HomeController : Controller
   {
      private static ILoggerFactory LogFactory = new LoggerFactory();
      private static ILogger Log = LogFactory.CreateLogger("HomeController");

      public IActionResult Index()
      {
         try
         {
            using (var db = new StockSimDbContext())
            {
               db.GameDtos.Add(new GameDto
               {
                  Title = "test title",
                  StartingMoney = 0m,
                  Private = false,
                  StartTimestamp = DateTime.UtcNow
               });

               db.SaveChanges();
            }
         }
         catch (Exception e)
         {
            Log.LogError(e.Message);
            Log.LogError(e.StackTrace);
         }

         return View();
      }
   }
}
