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
   public static final String ADD_USER = "INSERT INTO users (username, password, email, confirmCode, enabled, confirmed) VALUES (?, ?, ?, ?, ?, ?)";
   public static final String ADD_USER_ROLE = "INSERT INTO user_roles (username, role) VALUES (?, ?)";
   public static final String REMOVE_USER_ROLES_BY_USERNAME = "DELETE FROM user_roles WHERE username = ?";
   public static final String REMOVE_USER_BY_USERNAME = "DELETE FROM users WHERE username = ?";
   public static final String GET_PLAYER_BY_USERNAME_AND_GID = "SELECT * FROM players WHERE username = ? AND gid = ?";
   public static final String GET_NUMBER_OF_PLAYERS_FROM_GAME_BY_GID = "SELECT COUNT(gid) AS playerCount FROM players WHERE gid = ?";
   public static final String ADD_GAME = "INSERT INTO games (title, startingMoney, privateGame, startTimestamp) VALUES (?, ?, ?, ?)";
   public static final String GET_GAME_BY_TITLE = "SELECT * FROM games WHERE title = ?";
   public static final String ADD_PLAYER = "INSERT INTO players (username, gid, balance, isAdmin, inviteCode, enabled) VALUES (?, ?, ?, ?, ?, ?)";
   public static final String GET_STOCKS_BY_PID = "SELECT * FROM stocks WHERE pid = ?";
   public static final String GET_PLAYER_BY_INVITE_CODE = "SELECT * FROM players WHERE inviteCode = ?";
   public static final String REMOVE_PLAYER_BY_INVITE_CODE = "DELETE FROM players WHERE inviteCode = ?";
   public static final String SET_ENABLED_BY_INVITE_CODE = "UPDATE players SET enabled = true WHERE inviteCode = ?";
   // TODO: Write this better using a join
   public static final String GET_GAMES_BY_USERNAME = "SELECT * FROM games WHERE gid IN (SELECT gid FROM players WHERE username = ?)";

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

         return getDbUserFromResultSet(rs);
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

         return getDbUserFromResultSet(rs);
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

      try
      {
         DbUser user = getDbUserByConfirmCode(confirmCode);
         if(user == null || user.getConfirmed())
         {
            return false;
         }

         connection = dataSource.getConnection();
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
            DbPlayer player = getDbPlayerFromResultSet(rs);
            if(player != null)
            {
               players.add(player);
            }
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

         return getDbGameFromResultSet(rs);
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
            DbGame game = getDbGameFromResultSet(rs);
            if(game != null)
            {
               games.add(game);
            }
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
   
   public static boolean addNewUserAndRole(DbUser user)
   {
      Connection connection = null;
      PreparedStatement ps = null;

      try
      {
         connection = dataSource.getConnection();

         // Add the user first
         ps = connection.prepareStatement(ADD_USER);
         ps.setString(1, user.getUsername());
         ps.setString(2, user.getPassword());
         ps.setString(3, user.getEmail());
         ps.setString(4, user.getConfirmCode());
         ps.setBoolean(5, user.getEnabled());
         ps.setBoolean(6, user.getConfirmed());
         ps.executeUpdate();
         ps.close();
         ps = null;

         // Then add the user role
         ps = connection.prepareStatement(ADD_USER_ROLE);
         ps.setString(1, user.getUsername());
         ps.setString(2, "ROLE_USER");
         ps.executeUpdate();

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

   public static boolean removeUserAndRolesByUsername(String username)
   {
      Connection connection = null;
      PreparedStatement ps = null;

      try
      {
         connection = dataSource.getConnection();

         // Remove the user roles first
         ps = connection.prepareStatement(REMOVE_USER_ROLES_BY_USERNAME);
         ps.setString(1, username);
         ps.executeUpdate();
         ps.close();
         ps = null;

         // Then remove the user
         ps = connection.prepareStatement(REMOVE_USER_BY_USERNAME);
         ps.setString(1, username);
         ps.executeUpdate();
         
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

   public static DbPlayer getDbPlayerByUsernameAndGid(String username, int gid)
   {
      Connection connection = null;
      PreparedStatement ps = null;
      ResultSet rs = null;

      try
      {
         connection = dataSource.getConnection();

         ps = connection.prepareStatement(GET_PLAYER_BY_USERNAME_AND_GID);
         ps.setString(1, username);
         ps.setInt(2, gid);
         rs = ps.executeQuery();
         if(!rs.next())
         {
            return null;
         }

         return getDbPlayerFromResultSet(rs);
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

   public static int getPlayerCountFromGameByGid(int gid)
   {
      Connection connection = null;
      PreparedStatement ps = null;
      ResultSet rs = null;

      try
      {
         connection = dataSource.getConnection();

         ps = connection.prepareStatement(GET_NUMBER_OF_PLAYERS_FROM_GAME_BY_GID);
         ps.setInt(1, gid);
         rs = ps.executeQuery();
         if(!rs.next())
         {
            return -1;
         }
         return rs.getInt("playerCount");
      }
      catch(Exception e)
      {
         return -1;
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

   public static boolean addNewGame(DbGame game)
   {
      Connection connection = null;
      PreparedStatement ps = null;

      try
      {
         connection = dataSource.getConnection();

         // Add the game
         ps = connection.prepareStatement(ADD_GAME);
         ps.setString(1, game.getTitle());
         ps.setInt(2, game.getStartingMoney());
         ps.setBoolean(3, game.getPrivateGame());
         ps.setLong(4, game.getStartTimestamp());
         ps.executeUpdate();

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

   public static DbGame getDbGameByTitle(String title)
   {
      Connection connection = null;
      PreparedStatement ps = null;
      ResultSet rs = null;

      try
      {
         connection = dataSource.getConnection();
         ps = connection.prepareStatement(GET_GAME_BY_TITLE);
         ps.setString(1, title);
         rs = ps.executeQuery();

         if(!rs.next())
         {
            return null;
         }

         return getDbGameFromResultSet(rs);
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

   public static boolean addNewPlayer(DbPlayer player)
   {
      Connection connection = null;
      PreparedStatement ps = null;

      try
      {
         connection = dataSource.getConnection();

         // Add the game
         ps = connection.prepareStatement(ADD_PLAYER);
         ps.setString(1, player.getUsername());
         ps.setInt(2, player.getGid());
         ps.setInt(3, player.getBalance());
         ps.setBoolean(4, player.getIsAdmin());
         ps.setString(5, player.getInviteCode());
         ps.setBoolean(6, player.getEnabled());
         ps.executeUpdate();

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

   public static ArrayList<DbStock> getDbStocksByPid(int pid)
   {
      Connection connection = null;
      PreparedStatement ps = null;
      ResultSet rs = null;

      try
      {
         connection = dataSource.getConnection();
         ps = connection.prepareStatement(GET_STOCKS_BY_PID);
         ps.setInt(1, pid);
         rs = ps.executeQuery();

         ArrayList<DbStock> stocks = new ArrayList<DbStock>();

         while(rs.next())
         {
            DbStock stock = getDbStockFromResultSet(rs);
            if(stock != null)
            {
               stocks.add(stock);
            }
         }
         return stocks;
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

   public static DbPlayer getDbPlayerByInviteCode(String inviteCode)
   {
      Connection connection = null;
      PreparedStatement ps = null;
      ResultSet rs = null;

      try
      {
         connection = dataSource.getConnection();

         ps = connection.prepareStatement(GET_PLAYER_BY_INVITE_CODE);
         ps.setString(1, inviteCode);
         rs = ps.executeQuery();
         if(!rs.next())
         {
            return null;
         }

         return getDbPlayerFromResultSet(rs);
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

   public static boolean removePlayerByInviteCode(String inviteCode)
   {
      Connection connection = null;
      PreparedStatement ps = null;

      try
      {
         connection = dataSource.getConnection();

         // Remove the user roles first
         ps = connection.prepareStatement(REMOVE_PLAYER_BY_INVITE_CODE);
         ps.setString(1, inviteCode);
         ps.executeUpdate();

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

   public static boolean enablePlayerByInviteCode(String inviteCode)
   {
      Connection connection = null;
      PreparedStatement ps = null;

      try
      {
         DbPlayer player = getDbPlayerByInviteCode(inviteCode);
         if(player == null || player.getEnabled())
         {
            return false;
         }

         connection = dataSource.getConnection();
         ps = connection.prepareStatement(SET_ENABLED_BY_INVITE_CODE);
         ps.setString(1, inviteCode);
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

   public static ArrayList<DbGame> getDbGamesByUsername(String username)
   {
      Connection connection = null;
      PreparedStatement ps = null;
      ResultSet rs = null;

      try
      {
         connection = dataSource.getConnection();
         ps = connection.prepareStatement(GET_GAMES_BY_USERNAME);
         ps.setString(1, username);
         rs = ps.executeQuery();

         ArrayList<DbGame> games = new ArrayList<DbGame>();
         while(rs.next())
         {
            DbGame game = getDbGameFromResultSet(rs);
            if(game != null)
            {
               games.add(game);
            }
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
         }
      }
   }

   /**
    * Returns the DbGame from the current result set (so call rs.next() before calling this function)
   */
   private static DbGame getDbGameFromResultSet(ResultSet rs)
   {
      try
      {
         DbGame game = new DbGame();
         game.setGid(rs.getInt("gid"));
         game.setTitle(rs.getString("title"));
         game.setStartingMoney(rs.getInt("startingMoney"));
         game.setPrivateGame(rs.getBoolean("privateGame"));
         game.setStartTimestamp(rs.getLong("startTimestamp"));
         return game;
      }
      catch(Exception e)
      {
         return null;
      }
   }

   private static DbPlayer getDbPlayerFromResultSet(ResultSet rs)
   {
      try
      {
         DbPlayer player = new DbPlayer();
         player.setPid(rs.getInt("pid"));
         player.setUsername(rs.getString("username"));
         player.setGid(rs.getInt("gid"));
         player.setBalance(rs.getInt("balance"));
         player.setIsAdmin(rs.getBoolean("isAdmin"));
         player.setInviteCode(rs.getString("inviteCode"));
         player.setEnabled(rs.getBoolean("enabled"));
         return player;
      }
      catch(Exception e)
      {
         return null;
      }
   }

   private static DbStock getDbStockFromResultSet(ResultSet rs)
   {
      try
      {
         DbStock stock = new DbStock();
         stock.setSid(rs.getInt("sid"));
         stock.setTickerSymbol(rs.getString("tickerSymbol"));
         stock.setPid(rs.getInt("pid"));
         stock.setCount(rs.getInt("count"));
         return stock;
      }
      catch(Exception e)
      {
         return null;
      }
   }

   private static DbTransaction getDbTransactionFromResultSet(ResultSet rs)
   {
      try
      {
         DbTransaction transaction = new DbTransaction();
         transaction.setTid(rs.getInt("tid"));
         transaction.setSid(rs.getInt("sid"));
         transaction.setCount(rs.getInt("count"));
         transaction.setPrice(rs.getInt("price"));
         transaction.setTimestamp(rs.getLong("timestamp"));
         transaction.setBuy(rs.getBoolean("buy"));
         return transaction;
      }
      catch(Exception e)
      {
         return null;
      }
   }

   private static DbUser getDbUserFromResultSet(ResultSet rs)
   {
      try
      {
         DbUser user = new DbUser();
         user.setUsername(rs.getString("username"));
         user.setPassword(rs.getString("password"));
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
   }
}
