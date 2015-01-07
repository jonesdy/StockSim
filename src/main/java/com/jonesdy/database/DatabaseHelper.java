package com.jonesdy.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import com.jonesdy.database.model.*;

public class DatabaseHelper
{
   public static final String GET_USER_BY_USERNAME = "SELECT * FROM users WHERE username = ?";
   public static final String GET_USER_BY_CONFIRM_CODE = "SELECT * FROM users WHERE confirmCode = ?";
   public static final String SET_CONFIRMED_BY_CONFIRM_CODE = "UPDATE users SET confirmed = true WHERE confirmCode = ?";
   public static final String GET_PLAYERS_BY_USERNAME = "SELECT * FROM players WHERE username = ?";
   public static final String GET_GAME_BY_GID = "SELECT * FROM games WHERE gid = ?";
   public static final String GET_PUBLIC_GAMES = "SELECT * FROM games WHERE privateGame = 0";

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
            user.setPasswordHash(rs.getString("password"));
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

         DbUser user = new DbUser();
         user.setUsername(confirmCode);
         user.setPasswordHash(rs.getString("passwordHash"));
         user.setEmail(rs.getString("email"));
         user.setConfirmCode(rs.getString("confirmCode"));
         user.setEnabled(rs.getBoolean("enabled"));
         user.setConfirmed(rs.getBoolean("confirmed"));
         return user;
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

   public static boolean confirmUserByConfirmCode(String confirmCode)
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
            return false;
         }
         else if(rs.getBoolean("confirmed"))
         {
            return false;
         }

         ps.close();
         ps = null;

         ps = connection.prepareStatement(SET_CONFIRMED_BY_CONFIRM_CODE);
         ps.setString(1, confirmCode);
         ps.executeQuery();
         return true;
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
            // Nothing we can do
         }
      }
   }

   public static ArrayList<DbPlayer> getDbPlayersByUsername(String username)
   {
      Connection connection = null;
      PreparedStatement ps = null;
      ResultSet rs = null;

      try
      {
         connection = dataSource.getConnection();
         ps = connection.prepareStatement(GET_PLAYERS_BY_USERNAME);
         ps.setString(1, username);
         rs = ps.executeQuery();

         ArrayList<DbPlayer> players = new ArrayList<DbPlayer>();
         while(rs.next())
         {
            DbPlayer player = new DbPlayer();
            player.setPid(rs.getInt("pid"));
            player.setUsername(username);
            player.setGid(rs.getInt("gid"));
            player.setBalance(rs.getInt("balance"));
            player.setIsAdmin(rs.getBoolean("isAdmin"));
            players.add(player);
         }
         return players;
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

   public static DbGame getDbGameByGid(int gid)
   {
      Connection connection = null;
      PreparedStatement ps = null;
      ResultSet rs = null;

      try
      {
         connection = dataSource.getConnection();
         ps = connection.prepareStatement(GET_GAME_BY_GID);
         ps.setInt(1, gid);
         rs = ps.executeQuery();

         if(!rs.next())
         {
            return null;
         }

         DbGame game = new DbGame();
         game.setGid(gid);
         game.setTitle(rs.getString("title"));
         game.setStartingMoney(rs.getInt("startingMoney"));
         game.setPrivateGame(rs.getBoolean("privateGame"));
         return game;
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

   public static ArrayList<DbGame> getPublicDbGames()
   {
      Connection connection = null;
      PreparedStatement ps = null;
      ResultSet rs = null;

      try
      {
         connection = dataSource.getConnection();
         ps = connection.prepareStatement(GET_PUBLIC_GAMES);
         rs = ps.executeQuery();

         ArrayList<DbGame> games = new ArrayList<DbGame>();
         while(rs.next())
         {
            DbGame game = new DbGame();
            game.setGid(rs.getInt("gid"));
            game.setTitle(rs.getString("title"));
            game.setStartingMoney(rs.getInt("startingMoney"));
            game.setPrivateGame(rs.getBoolean("privateGame"));
            games.add(game);
         }
         return games;
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
