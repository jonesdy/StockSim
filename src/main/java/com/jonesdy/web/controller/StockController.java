package com.jonesdy.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jonesdy.yql.model.Quote;
import com.jonesdy.yql.YqlHelper;

@Controller
public class StockController
{
   @RequestMapping(value = "/viewStock", method = RequestMethod.GET)
   public ModelAndView viewStockPage(@RequestParam(value = "symbol", required = true) String symbol)
   {
      ModelAndView model = new ModelAndView();

      Quote quote = YqlHelper.getStockQuote(symbol);
      // Sometimes yahoo returns a stock with a price of 0
      if(quote != null && quote.getCents() != 0)
      {
         model.addObject("tickerSymbol", quote.getSymbol());
         model.addObject("name", quote.getName());
         String total = String.valueOf(quote.getCents());
         String dollars = "0";
         String cents = "00";
         if(quote.getCents() >= 100)
         {
            total.substring(0, total.length() - 2);
            cents = total.substring(total.length() - 2, total.length());
         }
         else
         {
            cents = total;
         }
         model.addObject("price", dollars + "." + cents);
      }

      model.setViewName("viewStock");
      return model;
   }
}
