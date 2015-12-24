using Microsoft.AspNet.Mvc;
using StockSim.Data.Access.Interface;

namespace StockSim.Controllers
{
   public class StockQuoteController : Controller
   {
      private readonly IStockQuoteDao _stockQuoteDao;

      public StockQuoteController(IStockQuoteDao stockQuoteDao)
      {
         _stockQuoteDao = stockQuoteDao;
      }

      [HttpGet]
      public JsonResult GetStockQuoteBySymbol(string symbol)
      {
         var quote = _stockQuoteDao.GetStockQuoteBySymbol(symbol.ToUpperInvariant());
         return Json(quote);
      }
   }
}