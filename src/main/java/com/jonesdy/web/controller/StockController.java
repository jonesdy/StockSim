package com.jonesdy.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jonesdy.yql.model.Quote;
import com.jonesdy.yql.YqlHelper;

@Controller
public class StockController
{
   @RequestMapping(value = "/viewStock", method = RequestMethod.GET)
   public ModelAndView viewStockPage()
   {
      ModelAndView model = new ModelAndView();

      Quote quote = YqlHelper.getStockQuote("YHOO");
      if(quote != null)
      {
         model.addObject("tickerSymbol", quote.getSymbol());
         model.addObject("name", quote.getName());
         model.addObject("price", quote.getLastTradePriceOnly());
      }

      model.setViewName("viewStock");
      return model;
   }
}
