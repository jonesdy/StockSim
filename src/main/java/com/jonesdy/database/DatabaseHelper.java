package com.jonesdy.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import com.jonesdy.database.model.*;

public class DatabaseHelper
{
   public static final String GET_USER_BY_USERNAME = "SELECT * FROM users WHERE username = ?";
   public static final String GET_USER_BY_CONFIRM_CODE = "SELECT * FROM users WHERE confirmCode = ?";

   private static JndiDataSourceLookup dsLookup = null;
   private static DataSource dataSource = null;

   static
   {
      dsLookup = new JndiDataSourceLookup();
      dsLookup.setResourceRef(true);
      dataSource = dsLookup.getDataSource("java:comp/env/jdbc/stocksimdb");
   }

   public static DataSource getDataSource()
   {
      return dataSource;
   }

   public static DbUser getDbUserByUsername(String username)
   {
      Connection connection = null;
      PreparedStatement ps = null;
      ResultSet rs = null;

      try
      {
         connection = dataSource.getConnection();
         ps = connection.prepareStatement(GET_USER_BY_USERNAME);
         ps.setString(1, username);
         rs = ps.executeQuery();
         if(!rs.next())
         {
            return null;
         }
         else
         {
            DbUser user = new DbUser();
            user.setUsername(username);
            user.setPasswordHash(rs.getString("passwordHash"));
            user.setEmail(rs.getString("email"));
            user.setConfirmCode(rs.getString("confirmCode"));
            user.setEnabled(rs.getBoolean("enabled"));
            user.setConfirmed(rs.getBoolean("confirmed"));
            return user;
         }
      }
      catch(Exception e)
      {
         return null;
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
            // Nothing we can do
         }
      }
   }

   public static DbUser getDbUserByConfirmCode(String confirmCode)
   {
      Connection connection = null;
      PreparedStatement ps = null;
      ResultSet rs = null;

      try
      {
         connection = dataSource.getConnection();
         ps = connection.prepareStatement(GET_USER_BY_CONFIRM_CODE);
         ps.setString(1, confirmCode);
         rs = ps.executeQuery();
         if(!rs.next())
         {
            return null;
         }
         else
         {
            DbUser user = new DbUser();
            user.setUsername(confirmCode);
            user.setPasswordHash(rs.getString("passwordHash"));
            user.setEmail(rs.getString("email"));
            user.setConfirmCode(rs.getString("confirmCode"));
            user.setEnabled(rs.getBoolean("enabled"));
            user.setConfirmed(rs.getBoolean("confirmed"));
            return user;
         }
      }
      catch(Exception e)
      {
         return null;
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
            // Nothing we can do
         }
      }
   }
}
