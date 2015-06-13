package com.jonesdy.web.controller;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jonesdy.database.DatabaseHelper;
import com.jonesdy.database.model.DbGame;
import com.jonesdy.database.model.DbPlayer;
import com.jonesdy.database.model.DbUser;
import com.jonesdy.web.model.FormUsername;

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

   @RequestMapping(value = "/isGameTitleAvailable", method = RequestMethod.GET, produces="application/json")
   public @ResponseBody boolean isGameTitleAvailable(@RequestParam(value = "title", required = true) String title)
   {
      return DatabaseHelper.getDbGameByTitle(title) == null;
   }

   @RequestMapping(value = "/joinGame", method = RequestMethod.GET)
   public ModelAndView joinGame(@RequestParam(value = "gid", required = false) String gid, @RequestParam(value = "inviteCode", required = false) String inviteCode)
   {
      ModelAndView model = new ModelAndView();

      if(inviteCode != null)
      {
         if(!DatabaseHelper.enablePlayerByInviteCode(inviteCode))
         {
            model.addObject("error", "Invalid invite code or player has already confirmed their invitation.");
         }
         else
         {
            model.addObject("msg", "Your invitation code has been successfully confirmed.");
         }
      }
      else if(gid == null)
      {
         model.addObject("error", "You must specify either a GID or invite code");
      }
      else
      {
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
                  model.addObject("error", "Game is private, you must be invited to it.");
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
      }

      model.setViewName("joinGame");
      return model;
   }

   @RequestMapping(value = "/manageGame", method = RequestMethod.GET)
   public ModelAndView manageGame(@RequestParam(value = "gid", required = true) String gid)
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
            else
            {
               DbPlayer player = DatabaseHelper.getDbPlayerByUsernameAndGid(username, game.getGid());
               if(player == null)
               {
                  model.addObject("error", "You are not in this game.");
               }
               else if(!player.getIsAdmin())
               {
                  model.addObject("error", "You are not an admin of this game.");
               }
               else
               {
                  model.addObject("usernameObject", new FormUsername());
               }
            }
         }
         catch(Exception e)
         {
            model.addObject("error", "GID provided is not valid.");
         }
      }

      model.setViewName("manageGame");
      model.addObject("gid", gid);
      return model;
   }

   @RequestMapping(value = "/inviteUser", method = RequestMethod.POST)
   public ModelAndView inviteUser(@ModelAttribute FormUsername username, @RequestParam(value = "gid", required = true) String gid)
   {
      ModelAndView model = new ModelAndView();

      DbUser user = DatabaseHelper.getDbUserByUsername(username.getUsername());
      if(user == null)
      {
         model.addObject("error", "User '" + username.getUsername() + "' does not exist.");
      }
      else if(!user.getConfirmed())
      {
         model.addObject("error", "User '" + username.getUsername() + "' has not confirmed their email, so they cannot be invited to any games.");
      }
      else
      {
         try
         {
            DbPlayer player = DatabaseHelper.getDbPlayerByUsernameAndGid(username.getUsername(), Integer.parseInt(gid));
            if(player != null)
            {
               if(player.getEnabled())
               {
                  model.addObject("error", "User '" + username.getUsername() + "' is already in the game.");
               }
               else
               {
                  model.addObject("error", "User '" + username.getUsername() + "' is in the game but has not accepted the invitation yet.");
               }
            }
            else
            {
               DbGame game = DatabaseHelper.getDbGameByGid(Integer.parseInt(gid));
               String inviteCode = RegisterController.generateConfirmCode();
               while(DatabaseHelper.getDbPlayerByInviteCode(inviteCode) != null)
               {
                  inviteCode = RegisterController.generateConfirmCode();
               }

               // Add the player
               player = new DbPlayer();
               player.setUsername(username.getUsername());
               player.setGid(Integer.parseInt(gid));
               player.setBalance(game.getStartingMoney());
               player.setIsAdmin(false);
               player.setInviteCode(inviteCode);
               player.setEnabled(false);
               if(!DatabaseHelper.addNewPlayer(player))
               {
                  model.addObject("error", "Unable to add User '" + username.getUsername() + "' to game.");
               }
               else
               {
                  try
                  {
                     // Send the user an email with join link
                     Properties properties = System.getProperties();
                     properties.setProperty("mail.smtp.host", "openstocksim.com");
                     Session session = Session.getDefaultInstance(properties);
                     MimeMessage message = new MimeMessage(session);
                     message.setFrom(new InternetAddress("noreply@openstocksim.com"));
                     message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
                     message.setSubject("OpenStockSim game invitation");
                     message.setText("You were invited to the game '" + game.getTitle() + "'.\nPlease click https://www.openstocksim.com/joinGame?inviteCode=" + inviteCode + " to join the game.");
                     Transport.send(message);

                     model.addObject("message", "User '" + username.getUsername() + "' was invited succesfully.");
                  }
                  catch(Exception e)
                  {
                     DatabaseHelper.removePlayerByInviteCode(inviteCode);  // Comment out for testing
                     model.addObject("error", "Unable to email User '" + username.getUsername() + "' their invite code.");
                  }
               }
            }
         }
         catch(Exception e)
         {
            model.addObject("error", "GID provided is not valid.");
         }
      }

      model.setViewName("confirmation");
      return model;
   }
}
