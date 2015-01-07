package com.jonesdy.web.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
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
      if(auth instanceof AnonymousAuthenticationToken)
      {
         userGames.add(new WebGame(0, "Please log in to see your games"));
      }
      else
      {
         String username = auth.getName();
         ArrayList<DbPlayer> dbPlayers = DatabaseHelper.getDbPlayersByUsername(username);
         for(DbPlayer dbPlayer : dbPlayers)
         {
            userGids.add(dbPlayer.getGid());
            DbGame dbGame = DatabaseHelper.getDbGameByGid(dbPlayer.getGid());
            userGames.add(new WebGame(dbGame.getGid(), dbGame.getTitle()));
         }
      }

      ArrayList<DbGame> publicDbGames = DatabaseHelper.getPublicDbGames();
      for(DbGame dbGame : publicDbGames)
      {
         if(!userGids.contains(dbGame.getGid()))
         {
            publicGames.add(new WebGame(dbGame.getGid(), dbGame.getTitle()));
         }
      }
      
      if(userGames.isEmpty())
      {
         userGames.add(new WebGame(0, "You are currently not in any games"));
      }
      if(publicGames.isEmpty())
      {
         publicGames.add(new WebGame(0, "There are no public games available"));
      }

      model.addObject("userGames", userGames);
      model.addObject("publicGames", publicGames);

      model.setViewName("games");

      return model;
   }

   @RequestMapping(value = "/game", method = RequestMethod.GET)
   public ModelAndView gamePage(@RequestParam(value = "gid", required = true) String gid)
   {
      ModelAndView model = new ModelAndView();

      final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
      dsLookup.setResourceRef(true);
      DataSource dataSource = dsLookup.getDataSource("java:comp/env/jdbc/stocksimdb");
      Connection connection = null;
      PreparedStatement ps = null;
      ResultSet rs = null;

      try
      {
         final String getGame = "SELECT * FROM games WHERE gid = ?";
         int gidInt = Integer.parseInt(gid);
         connection = dataSource.getConnection();
         ps = connection.prepareStatement(getGame);
         ps.setInt(1, gidInt);
         rs = ps.executeQuery();
         if(rs.next())
         {
         }
      }
      catch(Exception e)
      {
      }
      finally
      {
         try
         {
            if(ps != null)
            {
               ps.close();
            }
            if(rs != null)
            {
               rs.close();
            }
            if(connection != null)
            {
               connection.close();
            }
         }
         catch(Exception e)
         {
         }
      }

      model.setViewName("game");
      return model;
   }
}
