using Microsoft.AspNet.Mvc;
using StockSim.Data.Access.Interface;
using System;

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
         if (!User.Identity.IsAuthenticated)
         {
            throw new InvalidOperationException("You must be logged in to get a quote!");
         }
         var quote = _stockQuoteDao.GetStockQuoteBySymbol(symbol.ToUpperInvariant());
         return Json(quote);
      }
   }
}