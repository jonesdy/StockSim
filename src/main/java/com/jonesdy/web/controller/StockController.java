package com.jonesdy.web.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.jonesdy.yql.model.Quote;
import com.jonesdy.yql.YqlHelper;
import com.jonesdy.database.DatabaseHelper;
import com.jonesdy.database.model.DbGame;
import com.jonesdy.web.model.FormStock;

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
            dollars = total.substring(0, total.length() - 2);
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

   @RequestMapping(value = "/buyStock", method = RequestMethod.GET)
   public ModelAndView buyStockPage(@RequestParam(value = "symbol", required = false) String symbol)
   {
      ModelAndView model = new ModelAndView();

      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      if(auth instanceof AnonymousAuthenticationToken)
      {
         model.addObject("error", "You are not logged in");
         return model;
      }

      UserDetails user = (UserDetails)auth.getPrincipal();
      ArrayList<DbGame> games = DatabaseHelper.getDbGamesByUsername(user.getUsername());

      if(games != null && !games.isEmpty())
      {
         ArrayList<String> gameTitles = new ArrayList<String>();
         for(DbGame game : games)
         {
            gameTitles.add(game.getTitle());
         }

         model.addObject("gameTitles", gameTitles);
      }

      if(symbol != null)
      {
         Quote quote = YqlHelper.getStockQuote(symbol);

         if(quote != null && quote.getCents() != 0)
         {
            model.addObject("tickerSymbol", quote.getSymbol());
            model.addObject("name", quote.getName());
            String total = String.valueOf(quote.getCents());
            String dollars = "0";
            String cents = "00";
            if(quote.getCents() >= 100)
            {
               dollars = total.substring(0, total.length() - 2);
               cents = total.substring(total.length() - 2, total.length());
            }
            else
            {
               cents = total;
            }
            model.addObject("price", dollars + "." + cents);
         }
         else
         {
            model.addObject("error", "Unable to find stock price");
            return model;
         }

         FormStock stock = new FormStock();
         stock.setSymbol(symbol);
         model.addObject("stock", stock);
      }
      else
      {
         model.addObject("stock", new FormStock());
      }

      model.setViewName("buyStock");
      return model;
   }
}
