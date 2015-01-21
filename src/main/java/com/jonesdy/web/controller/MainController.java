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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jonesdy.web.model.WebGame;
import com.jonesdy.database.DatabaseHelper;
import com.jonesdy.database.model.DbGame;
import com.jonesdy.database.model.DbPlayer;
import com.jonesdy.database.model.DbStock;

@Controller
public class MainController
{
   @RequestMapping(value = {"/", "index"}, method = RequestMethod.GET)
   public ModelAndView defaultPage()
   {
      ModelAndView model = new ModelAndView();
      model.addObject("title", "Spring Security Login Form");
      model.addObject("message", "This is the default page!");
      model.setViewName("index");
      return model;
   }
   
   @RequestMapping(value = "/admin**", method = RequestMethod.GET)
   public ModelAndView adminPage()
   {
      ModelAndView model = new ModelAndView();
      model.addObject("title", "Spring Security Login Form");
      model.addObject("message", "This page is for admins only!");
      model.setViewName("admin");
      return model;
   }
   
   @RequestMapping(value = "/login", method = RequestMethod.GET)
   public ModelAndView login(@RequestParam(value = "error", required = false) String error,
         @RequestParam(value = "logout", required = false) String logout)
   {
      ModelAndView model = new ModelAndView();
      if(error != null)
      {
         model.addObject("error", "Invalid username or password!");
      }
      if(logout != null)
      {
         model.addObject("msg", "You've been logged out succesfully.");
      }
      model.setViewName("login");
      return model;
   }
   
   @RequestMapping(value = "/isUsernameAvailable", method = RequestMethod.GET, produces="application/json")
   public @ResponseBody boolean isUsernameAvailable(@RequestParam(value = "username", required = true) String username)
   {
      return DatabaseHelper.getDbUserByUsername(username) == null;
   }
   
   @RequestMapping(value = "/confirm", method = RequestMethod.GET)
   public ModelAndView confirm(@RequestParam(value = "code", required = true) String code)
   {
      ModelAndView model = new ModelAndView();
      
      if(!DatabaseHelper.confirmUserByConfirmCode(code))
      {
         model.addObject("error", "Confirmation code does not exist, or the user was already confirmed.");
      }
      else
      {
         model.addObject("message", "User successfully confirmed.");
      }
      
      model.setViewName("confirmation");
      return model;
   }
   
   @RequestMapping(value = "/403", method = RequestMethod.GET)
   public ModelAndView accessDenied()
   {
      ModelAndView model = new ModelAndView();
      
      // Check if a user is logged in
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      if(!(auth instanceof AnonymousAuthenticationToken))
      {
         UserDetails userDetails = (UserDetails)auth.getPrincipal();
         model.addObject("username", userDetails.getUsername());
      }
      model.setViewName("403");
      
      return model;
   }

   @RequestMapping(value = "/games", method = RequestMethod.GET)
   public ModelAndView gamesPage()
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

      model.setViewName("games");

      return model;
   }

   @RequestMapping(value = "/game", method = RequestMethod.GET)
   public ModelAndView gamePage(@RequestParam(value = "gid", required = true) String gid)
   {
      ModelAndView model = new ModelAndView();

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

      model.setViewName("game");
      return model;
   }

   @RequestMapping(value = "/isGameTitleAvailable", method = RequestMethod.GET, produces="application/json")
   public @ResponseBody boolean isGameTitleAvailable(@RequestParam(value = "title", required = true) String title)
   {
      return DatabaseHelper.getDbGameByTitle(title) == null;
   }

   @RequestMapping(value = "/joinGame", method = RequestMethod.GET)
   public ModelAndView joinGame(@RequestParam(value = "gid", required = true) String gid)
   {
      ModelAndView model = new ModelAndView();

      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      if(auth instanceof AnonymousAuthenticationToken)
      {
         model.addObject("error", "You are not logged in.");
      }
      else
      {
         UserDetails details = (UserDetails)auth.getPrincipal();
         String username = details.getUsername();
         
         try
         {
            DbGame game = DatabaseHelper.getDbGameByGid(Integer.parseInt(gid));
            if(game == null)
            {
               model.addObject("error", "GID provided is not valid.");
            }
            else if(DatabaseHelper.getDbPlayerByUsernameAndGid(username, game.getGid()) != null)
            {
               model.addObject("error", "You are already in this game.");
            }
            else if(game.getPrivateGame())
            {
               model.addObject("error", "Game is private");
            }
            else
            {
               DbPlayer player = new DbPlayer();
               player.setUsername(username);
               player.setGid(game.getGid());
               player.setBalance(game.getStartingMoney());
               player.setIsAdmin(false);
               player.setInviteCode(null);
               player.setEnabled(true);

               if(!DatabaseHelper.addNewPlayer(player))
               {
                  model.addObject("error", "Unable to join game.");
               }
               else
               {
                  model.addObject("msg", "Successfully joined game: " + game.getTitle());
               }
            }
         }
         catch(Exception e)
         {
            model.addObject("error", "GID provided is not valid.");
         }
      }

      model.setViewName("joinGame");
      return model;
   }
}
