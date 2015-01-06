package com.jonesdy.web.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jonesdy.web.model.WebUser;

@Controller
@RequestMapping(value = "/register")
public class RegisterController
{
   @RequestMapping(method = RequestMethod.GET)
   public ModelAndView viewRegistration()
   {
      ModelAndView model = new ModelAndView();
      model.addObject("user", new WebUser());
      model.setViewName("registration");
      return model;
   }
   
   @RequestMapping(method = RequestMethod.POST)
   public ModelAndView processRegistration(@ModelAttribute WebUser user)
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
   
   private String registerUser(WebUser user)
   {
      final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
      dsLookup.setResourceRef(true);
      DataSource dataSource = dsLookup.getDataSource("java:comp/env/jdbc/stocksimdb");
      Connection connection = null;
      PreparedStatement ps = null;
      ResultSet rs = null;
      String confirmCode = generateConfirmCode();
      try
      {
         final String checkUsername = "SELECT * FROM users WHERE username = ?";
         final String addUser = "INSERT INTO users (username, password, email, confirmCode, enabled, confirmed) VALUES (?, ?, ?, ?, ?, ?)";
         final String addUserRole = "INSERT INTO user_roles (username, role) VALUES (?, ?)";
         final String checkConfirmCode = "SELECT * FROM users WHERE confirmCode = ?";
         
         connection = dataSource.getConnection();         
         
         ps = connection.prepareStatement(checkUsername);
         ps.setString(1, user.getUsername());
         rs = ps.executeQuery();
         if(rs.next())
         {
            return "User Name already exists.";
         }
         rs.close();
         ps.close();
         ps = null;
         rs = null;
         
         // Generate a confirm code and make sure it is unique
         boolean unique = false;
         while(!unique)
         {
            ps = connection.prepareStatement(checkConfirmCode);
            ps.setString(1, confirmCode);
            rs = ps.executeQuery();
            unique = !rs.next();
            rs.close();
            ps.close();
            ps = null;
            rs = null;
            
            if(!unique)
            {
               confirmCode = generateConfirmCode();
            }
         }
         
         // Time to add the user
         BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
         String passwordHash = passwordEncoder.encode(user.getPassword());
         ps = connection.prepareStatement(addUser);
         ps.setString(1, user.getUsername());
         ps.setString(2, passwordHash);
         ps.setString(3, user.getEmail());
         ps.setString(4, confirmCode);
         ps.setBoolean(5, true);
         ps.setBoolean(6, false);
         ps.executeUpdate();
         ps.close();
         ps = null;
         
         // Need to add user role as well
         ps = connection.prepareStatement(addUserRole);
         ps.setString(1, user.getUsername());
         ps.setString(2, "ROLE_USER");
         ps.executeUpdate();
         ps.close();
         ps = null;
         
         // Send the email
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
         removeUser(user.getUsername());   // Comment out for testing
         return "Failed with exception: " + e.toString();
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
            return "Failed to close connection to database.";
         }
      }
      return null;
   }
   
   private static String generateConfirmCode()
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
   
   private static void removeUser(String username)
   {
      final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
      dsLookup.setResourceRef(true);
      DataSource dataSource = dsLookup.getDataSource("java:comp/env/jdbc/stocksimdb");
      Connection connection = null;
      PreparedStatement ps = null;
      
      try
      {
         final String removeUserRole = "DELETE FROM user_roles WHERE username = ?";
         final String removeUser = "DELETE FROM users WHERE username = ?";
         connection = dataSource.getConnection();
         ps = connection.prepareStatement(removeUserRole);
         ps.setString(1, username);
         ps.executeQuery();
         
         ps.close();
         ps = null;
         
         ps = connection.prepareStatement(removeUser);
         ps.setString(1, username);
         ps.executeQuery();
         
         ps.close();
         ps = null;
      }
      catch(Exception e)
      {
         // Nothing we can do
      }
      finally
      {
         try
         {
            if(ps != null)
            {
               ps.close();
            }
            if(connection != null)
            {
               connection.close();
            }
         }
         catch(Exception e)
         {
            // Nothing we can do
         }
      }
   }
}
