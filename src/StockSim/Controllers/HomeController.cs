using Microsoft.AspNet.Mvc;
using Microsoft.Extensions.Logging;

namespace StockSim.Controllers
{
   public class HomeController : Controller
   {
      private readonly ILogger _log;

      public HomeController(ILoggerFactory loggerFactory)
      {
         _log = loggerFactory.CreateLogger<HomeController>();
      }

      public IActionResult Index()
      {
         return View();
      }
   }
}
