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

      Quote quote = YqlHelper.getStockQuote("ACST");
      if(quote != null)
      {
         model.addObject("tickerSymbol", quote.getSymbol());
         model.addObject("name", quote.getName());
         String cents = String.valueOf(quote.getCents());
         String price = cents.substring(0, cents.length() - 2) + "." + cents.substring(cents.length() - 2, cents.length());
         model.addObject("price", price);
      }

      model.setViewName("viewStock");
      return model;
   }
}
