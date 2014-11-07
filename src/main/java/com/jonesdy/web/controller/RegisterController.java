package com.jonesdy.web.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jonesdy.model.User;

@Controller
@RequestMapping(value = "/register")
public class RegisterController
{
   @RequestMapping(method = RequestMethod.GET)
   public ModelAndView viewRegistration()
   {
      ModelAndView model = new ModelAndView();
      model.addObject("user", new User());
      model.setViewName("registration");
      return model;
   }
   
   @RequestMapping(method = RequestMethod.POST)
   public ModelAndView processRegistration(@ModelAttribute User user)
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
   
   private String registerUser(User user)
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
         String checkEmail = "SELECT * FROM users WHERE email = ?";
         String addUser = "INSERT INTO users (username, password, email, enabled, confirmed) VALUES (?, ?, ?, ?, ?)";
         String addUserRole = "INSERT INTO user_roles (username, role) VALUES (?, ?)";
         
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
         
         ps = connection.prepareStatement(checkEmail);
         ps.setString(1, user.getEmail());
         rs = ps.executeQuery();
         if(rs.next())
         {
            // TODO: This might not actually be important
            return "Email already used.";
         }
         rs.close();
         ps.close();
         
         // Time to add the user
         BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
         String passwordHash = passwordEncoder.encode(user.getPassword());
         ps = connection.prepareStatement(addUser);
         ps.setString(1, user.getUsername());
         ps.setString(2, passwordHash);
         ps.setString(3, user.getEmail());
         ps.setBoolean(4, true);
         ps.setBoolean(5, true); // TODO: Confirmation later
         ps.executeUpdate();
         ps.close();
         
         // Need to add user role as well
         // TODO: If this fails, remove the user
         ps = connection.prepareStatement(addUserRole);
         ps.setString(1, user.getUsername());
         ps.setString(2, "ROLE_USER");
         ps.executeUpdate();
         ps.close();
         
         ps = null;
         rs = null;
      }
      catch(Exception e)
      {
         return "Failed to connect to database.";
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
}