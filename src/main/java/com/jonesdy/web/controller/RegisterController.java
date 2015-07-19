package com.jonesdy.web.controller;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jonesdy.database.DatabaseHelper;
import com.jonesdy.database.model.DbUser;
import com.jonesdy.web.model.FormUser;

@Controller
@RequestMapping(value = "/register")
public class RegisterController
{
   @RequestMapping(method = RequestMethod.GET)
   public ModelAndView viewRegistration()
   {
      ModelAndView model = new ModelAndView();
      model.addObject("user", new FormUser());
      model.setViewName("registration");
      return model;
   }
   
   @RequestMapping(method = RequestMethod.POST)
   public ModelAndView processRegistration(@ModelAttribute FormUser user)
   {
      ModelAndView model = new ModelAndView();
      
      if(user.getUsername().isEmpty())
      {
         model.addObject("failureReason", "User Name is empty.");
         model.setViewName("registrationFailure");
      }
      else if(user.getEmail().isEmpty() || user.getEmailConfirm().isEmpty())
      {
         model.addObject("failureReason", "Email is empty.");
         model.setViewName("registrationFailure");
      }
      else if(!user.getEmail().contains("@"))
      {
         model.addObject("failureReason", "Email is not valid.");
         model.setViewName("registrationFailure");
      }
      else if(user.getPassword().isEmpty() || user.getPasswordConfirm().isEmpty())
      {
         model.addObject("failureReason", "Password is empty.");
         model.setViewName("registrationFailure");
      }
      else if(!user.getEmail().equals(user.getEmailConfirm()))
      {
         model.addObject("failureReason", "Emails did not match.");
         model.setViewName("registrationFailure");
      }
      else if(!user.getPassword().equals(user.getPasswordConfirm()))
      {
         model.addObject("failureReason", "Passwords did not match.");
         model.setViewName("registrationFailure");
      }
      else if(user.getUsername().length() > 60)
      {
         model.addObject("failureReason", "Username is too long.");
         model.setViewName("registrationFailure");
      }
      else if(user.getEmail().length() > 60)
      {
         model.addObject("failureReason", "Email is too long.");
         model.setViewName("registrationFailure");
      }
      else
      {
         String registerError = registerUser(user);
         if(registerError == null)
         {
            model.addObject("username", user.getUsername());
            model.addObject("email", user.getEmail());
            model.setViewName("registrationSuccess");
         }
         else
         {
            model.addObject("failureReason", registerError);
            model.setViewName("registrationFailure");
         }
      }
      
      return model;
   }
   
   private String registerUser(FormUser user)
   {
      String confirmCode = generateConfirmCode();

      // Make sure the user doesn't already exist
      if(DatabaseHelper.getDbUserByUsername(user.getUsername()) != null)
      {
         return "User Name already exists.";
      }

      // Make sure our confirm code is unique
      while(DatabaseHelper.getDbUserByConfirmCode(confirmCode) != null)
      {
         confirmCode = generateConfirmCode();
      }
         
      // Time to add the user
      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      String passwordHash = passwordEncoder.encode(user.getPassword());

      DbUser dbUser = new DbUser();
      dbUser.setUsername(user.getUsername());
      dbUser.setPassword(passwordHash);
      dbUser.setEmail(user.getEmail());
      dbUser.setConfirmCode(confirmCode);
      dbUser.setEnabled(true);
      dbUser.setConfirmed(false);
      if(!DatabaseHelper.addNewUserAndRole(dbUser))
      {
         DatabaseHelper.removeUserAndRolesByUsername(dbUser.getUsername());  // Comment out for testing
         return "Error while adding new user.";
      }
               
      // Send the email
      try
      {
         Properties properties = System.getProperties();
         properties.setProperty("mail.smtp.host", "openstocksim.com");
         Session session = Session.getDefaultInstance(properties);
         MimeMessage message = new MimeMessage(session);
         message.setFrom(new InternetAddress("noreply@openstocksim.com"));
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
         message.setSubject("OpenStockSim confirmation code");
         message.setText("Welcome to Open Stock Sim!\nPlease click https://www.openstocksim.com/confirm?code=" + confirmCode + " to confirm your account.");
         Transport.send(message);
      }
      catch(Exception e)
      {
         //DatabaseHelper.removeUserAndRolesByUsername(dbUser.getUsername());  // Comment out for testing
         return "Unable to send confirmation email.";
      }

      // No errors
      return null;
   }
   
   public static String generateConfirmCode()
   {
      final String letters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
      final int codeLength = 32;
      Random random = new Random();
      StringBuilder sb = new StringBuilder(codeLength);
      for(int i = 0; i < codeLength; i++)
      {
         sb.append(letters.charAt(random.nextInt(letters.length())));
      }
      return sb.toString();
   }
}
