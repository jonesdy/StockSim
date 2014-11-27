package com.jonesdy.web.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
   
   @RequestMapping(value = "/isUsernameAvailable", method=RequestMethod.GET, produces="application/json")
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
         String checkUsername = "SELECT * FROM users WHERE username = ?";
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
}
