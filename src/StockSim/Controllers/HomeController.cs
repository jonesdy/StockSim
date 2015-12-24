using Microsoft.AspNet.Mvc;
using Microsoft.Extensions.Logging;
using StockSim.Services.Interface;

namespace StockSim.Controllers
{
   public class HomeController : Controller
   {
      private readonly ILogger _log;
      private readonly IClosedTimeService _closedTimeService;

      public HomeController(ILoggerFactory loggerFactory, IClosedTimeService closedTimeService)
      {
         _log = loggerFactory.CreateLogger<HomeController>();

         _closedTimeService = closedTimeService;
      }

      public IActionResult Index()
      {
         return View(model: _closedTimeService.IsStockMarketOpen());
      }
   }
}
