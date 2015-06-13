package com.jonesdy.web.controller;

import java.util.ArrayList;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jonesdy.database.DatabaseHelper;
import com.jonesdy.database.model.DbGame;
import com.jonesdy.database.model.DbPlayer;
import com.jonesdy.database.model.DbStock;
import com.jonesdy.web.model.WebGame;

@Controller
@RequestMapping(value = "games")
public class GamesController
{
   @RequestMapping(value = "/viewGame", method = RequestMethod.GET)
   public ModelAndView viewGamePage(@RequestParam(value = "gid", required = true) String gid)
   {
      ModelAndView model = new ModelAndView();

      if(gid != null && gid.equalsIgnoreCase("all"))
      {
         return allGames();
      }

      try
      {
         DbGame game = DatabaseHelper.getDbGameByGid(Integer.parseInt(gid));
         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         boolean userLoggedIn = !(auth instanceof AnonymousAuthenticationToken);
         model.addObject("userLoggedIn", userLoggedIn);

         if(!game.getPrivateGame())
         {
            // Public game
            model.addObject("game", game);
            model.addObject("convertedStartingMoney", (double)(game.getStartingMoney() / (double)100));
         }
         else if(userLoggedIn)
         {
            // Private game, only show information if user is in this game
            UserDetails userDetails = (UserDetails)auth.getPrincipal();
            if(DatabaseHelper.getDbPlayerByUsernameAndGid(userDetails.getUsername(), Integer.parseInt(gid)) != null)
            {
               model.addObject("game", game);
               model.addObject("convertedStartingMoney", (double)(game.getStartingMoney() / (double)100));
            }
         }
            
         if(userLoggedIn)
         {
            UserDetails userDetails = (UserDetails)auth.getPrincipal();
            DbPlayer player = DatabaseHelper.getDbPlayerByUsernameAndGid(userDetails.getUsername(), Integer.parseInt(gid));
            if(player != null)
            {
               model.addObject("player", player);
               ArrayList<DbStock> stocks = DatabaseHelper.getDbStocksByPid(player.getPid());
               if(stocks != null && !stocks.isEmpty())
               {
                  model.addObject("stocks", stocks);
               }
               model.addObject("convertedBalance", (double)(player.getBalance() / (double)100));
            }
         }
      }
      catch(Exception e)
      {
         // Game is not valid, handled in the page
      }

      model.setViewName("games/game");
      return model;
   }

   private ModelAndView allGames()
   {
      ModelAndView model = new ModelAndView();

      ArrayList<WebGame> userGames = new ArrayList<WebGame>();
      ArrayList<WebGame> publicGames = new ArrayList<WebGame>();
      ArrayList<Integer> userGids = new ArrayList<Integer>();
      
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      if(!(auth instanceof AnonymousAuthenticationToken))
      {
         String username = auth.getName();
         ArrayList<DbPlayer> dbPlayers = DatabaseHelper.getDbPlayersByUsername(username);
         for(DbPlayer dbPlayer : dbPlayers)
         {
            userGids.add(dbPlayer.getGid());
            DbGame dbGame = DatabaseHelper.getDbGameByGid(dbPlayer.getGid());
            userGames.add(new WebGame(dbGame.getGid(), dbGame.getTitle(),
               DatabaseHelper.getPlayerCountFromGameByGid(dbGame.getGid())));
         }
      }

      ArrayList<DbGame> publicDbGames = DatabaseHelper.getPublicDbGames();
      for(DbGame dbGame : publicDbGames)
      {
         if(!userGids.contains(dbGame.getGid()))
         {
            publicGames.add(new WebGame(dbGame.getGid(), dbGame.getTitle(),
               DatabaseHelper.getPlayerCountFromGameByGid(dbGame.getGid())));
         }
      }
      
      if(!userGames.isEmpty())
      {
         model.addObject("userGames", userGames);
      }
      if(!publicGames.isEmpty())
      {
         model.addObject("publicGames", publicGames);
      }

      model.setViewName("games/games");

      return model;
   }

}
