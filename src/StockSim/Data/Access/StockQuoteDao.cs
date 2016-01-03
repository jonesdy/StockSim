using Microsoft.Extensions.Logging;
using StockSim.Data.Access.Interface;
using StockSim.Data.Transfer;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Xml;

namespace StockSim.Data.Access
{
   public class StockQuoteDao : IStockQuoteDao
   {
      private const string QueryString = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quote%20where%20symbol%20%3D%20%22{0}%22&diagnostics=false&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
      private const string NameTag = "Name";
      private const string SymbolTag = "Symbol";
      private const string CostTag = "LastTradePriceOnly";
      private readonly ILogger _log;

      public StockQuoteDao(ILoggerFactory loggerFactory)
      {
         _log = loggerFactory.CreateLogger<StockQuoteDao>();
      }

      public StockQuoteDto GetStockQuoteBySymbol(string symbol)
      {
         var query = string.Format(QueryString, symbol);

         try
         {
            var httpClient = new HttpClient();
            var task = httpClient.GetStreamAsync(query);
            task.Wait();
            var response = task.Result;
            var document = new XmlDocument();
            document.Load(response);
            return new StockQuoteDto
            {
               Name = document.GetElementsByTagName(NameTag)[0].InnerText,
               Symbol = document.GetElementsByTagName(SymbolTag)[0].InnerText,
               Cost = decimal.Parse(document.GetElementsByTagName(CostTag)[0].InnerText)
            };
         }
         catch (Exception e)
         {
            _log.LogError(e.Message);
            _log.LogError(e.StackTrace);
            throw;
         }
      }

      public IList<StockQuoteDto> GetStockQuotesBySymbols(IList<string> symbols)
      {
         var query = string.Format(QueryString, string.Join(",", symbols));

         try
         {
            var httpClient = new HttpClient();
            var task = httpClient.GetStreamAsync(query);
            task.Wait();
            var response = task.Result;
            var document = new XmlDocument();
            document.Load(response);
            var resultNames = document.GetElementsByTagName(NameTag);
            var resultSymbols = document.GetElementsByTagName(SymbolTag);
            var resultCosts = document.GetElementsByTagName(CostTag);
            var stockQuotes = new List<StockQuoteDto>();
            for(var i = 0; i < resultNames.Count; i++)
            {
               stockQuotes.Add(new StockQuoteDto
               {
                  Name = resultNames[i].InnerText,
                  Symbol = resultSymbols[i].InnerText,
                  Cost = decimal.Parse(resultCosts[i].InnerText)
               });
            }
            return stockQuotes;
         }
         catch(Exception e)
         {
            _log.LogError(e.Message);
            _log.LogError(e.StackTrace);
            throw;
         }
      }
   }
}