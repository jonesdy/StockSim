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
      final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
      dsLookup.setResourceRef(true);
      DataSource dataSource = dsLookup.getDataSource("java:comp/env/jdbc/stocksimdb");
      Connection connection = null;
      PreparedStatement ps = null;
      ResultSet rs = null;
      
      try
      {
         final String checkUsername = "SELECT * FROM users WHERE username = ?";
         connection = dataSource.getConnection();
         ps = connection.prepareStatement(checkUsername);
         ps.setString(1, username);
         rs = ps.executeQuery();
         return !rs.next();
      }
      catch(Exception e)
      {
         return false;
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
            // Nothing we can really do here
         }
      }
   }
   
   @RequestMapping(value = "/confirm", method = RequestMethod.GET)
   public ModelAndView confirm(@RequestParam(value = "code", required = true) String code)
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
         // Get the user
         final String getConfirm = "SELECT * FROM users WHERE confirmCode = ?";
         final String updateConfirmed = "UPDATE users SET confirmed = true WHERE confirmCode = ?";
         connection = dataSource.getConnection();
         ps = connection.prepareStatement(getConfirm);
         ps.setString(1, code);
         rs = ps.executeQuery();
         if(!rs.next())    // Check that the confirmation code is valid
         {
            model.addObject("error", "Confirmation code does not exist!");
         }
         else if(rs.getBoolean("confirmed"))    // Check that wasn't already confirmed
         {
            model.addObject("error", "User has already been confirmed!");
         }
         else     // Confirm the user
         {
            ps.close();
            ps = null;
            
            ps = connection.prepareStatement(updateConfirmed);
            ps.setString(1, code);
            ps.executeQuery();
            
            model.addObject("message", "User successfully confirmed!");
         }
         
         ps.close();
         rs.close();
         ps = null;
         rs = null;
      }
      catch(Exception e)
      {
         model.addObject("error", "An error occurred: " + e.toString());
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
            model.addObject("error", "An error occurred: " + e.toString());
         }
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

      ArrayList<String> userGames = new ArrayList<String>();
      ArrayList<String> publicGames = new ArrayList<String>();
      ArrayList<Integer> userGids = new ArrayList<Integer>();

      final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
      dsLookup.setResourceRef(true);
      DataSource dataSource = dsLookup.getDataSource("java:comp/env/jdbc/stocksimdb");
      Connection connection = null;
      PreparedStatement ps = null;
      ResultSet rs = null;
      
      try
      {
         final String getPlayers = "SELECT gid FROM players WHERE username = ?";
         final String getGameNames = "SELECT title FROM games WHERE gid = ?";
         final String getPublicGames = "SELECT * FROM games WHERE private = 0";

         connection = dataSource.getConnection();

         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         if(!(auth instanceof AnonymousAuthenticationToken))
         {
            String username = auth.getName();

            ps = connection.prepareStatement(getPlayers);
            ps.setString(1, username);
            rs = ps.executeQuery();
            while(rs.next())
            {
               int gid = rs.getInt("gid");
               userGids.add(gid);
               PreparedStatement psGames = null;
               ResultSet rsGames = null;
               try
               {
                  psGames = connection.prepareStatement(getGameNames);
                  psGames.setInt(1, gid);
                  rsGames = psGames.executeQuery();
                  rsGames.next();
                  String gameName = rsGames.getString("title");
                  userGames.add(gameName);
                  psGames.close();
                  psGames = null;
                  rsGames.close();
                  rsGames = null;
               }
               catch(Exception e)
               {
                  if(psGames != null)
                  {
                     psGames.close();
                  }
                  if(rsGames != null)
                  {
                     rsGames.close();
                  }
               }
            }
            ps.close();
            ps = null;
            rs.close();
            rs = null;
         }
         else
         {
            userGames.add("Please log in to see your games");
         }
         
         ps = connection.prepareStatement(getPublicGames);
         rs = ps.executeQuery();
         while(rs.next())
         {
            int gid = rs.getInt("gid");
            if(!userGids.contains(gid))
            {
               String gameName = rs.getString("title");
               publicGames.add(gameName);
            }
         }
         ps.close();
         ps = null;
         rs.close();
         rs = null;

         connection.close();
         connection = null;
      }
      catch(Exception e)
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
         catch(Exception ex)
         {
            // Nothing we can do
         }
      }
      
      if(userGames.isEmpty())
      {
         userGames.add("You are current not in any games");
      }
      if(publicGames.isEmpty())
      {
         publicGames.add("There are no public games available");
      }

      model.addObject("userGames", userGames);
      model.addObject("publicGames", publicGames);

      model.setViewName("games");

      return model;
   }
}
