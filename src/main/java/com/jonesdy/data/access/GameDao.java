package com.jonesdy.data.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.jonesdy.data.transfer.GameDto;

public class GameDao extends DatabaseDao
{
   private static final String SELECT_GAME_BY_GID = "select gid, title, startingMoney, privateGame, startTimestamp from games where gid = ?";
   private static final String SELECT_PUBLIC_GAMES = "select gid, title, startingMoney, privateGame, startTimestamp from games where privateGame = 0";
   private static final String SELECT_GAME_BY_TITLE = "select gid, title, startingMoney, privateGame, startTimestamp from games where title = ?";
   private static final String SELECT_GAMES_BY_USERNAME = "select gid, title, startingMoney, privateGame, startTimestamp from games g join players p on p.gid = g.gid where p.username = ?"; 
   private static final String SELECT_NUMBER_OF_PLAYERS_FROM_GAME_BY_GID = "SELECT COUNT(gid) AS playerCount FROM players WHERE gid = ?";
   private static final String INSERT_GAME = "insert into game (gid, title, startingMoney, privateGame, startTimestamp) values (?, ?, ?, ?, ?)";

   public GameDto selectGameByGid(int gid)
   {
      Connection con = null;
      PreparedStatement ps = null;
      ResultSet rs = null;

      try
      {
         con = getDataSource().getConnection();
         ps = con.prepareStatement(SELECT_GAME_BY_GID);
         ps.setInt(1, gid);
         rs = ps.executeQuery();
         if(!rs.next())
         {
            return null;
         }

         return getGameFromResultSet(rs);
      }
      catch(Exception e)
      {
         return null;
      }
      finally
      {
         closeConPsRs(con, ps, rs);
      }
   }

   public List<GameDto> getPublicGames()
   {
      Connection con = null;
      PreparedStatement ps = null;
      ResultSet rs = null;

      try
      {
         con = getDataSource().getConnection();
         ps = con.prepareStatement(SELECT_PUBLIC_GAMES);
         rs = ps.executeQuery();

         List<GameDto> games = new ArrayList<GameDto>();
         while(rs.next())
         {
            GameDto game = getGameFromResultSet(rs);
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
         closeConPsRs(con, ps, rs);
      }
   }

   public GameDto selectGameByTitle(String title)
   {
      Connection con = null;
      PreparedStatement ps = null;
      ResultSet rs = null;

      try
      {
         con = getDataSource().getConnection();
         ps = con.prepareStatement(SELECT_GAME_BY_TITLE);
         ps.setString(1, title);
         rs = ps.executeQuery();

         if(!rs.next())
         {
            return null;
         }

         return getGameFromResultSet(rs);
      }
      catch(Exception e)
      {
         return null;
      }
      finally
      {
         closeConPsRs(con, ps, rs);
      }
   }

   public List<GameDto> selectGamesByUsername(String username)
   {
      Connection con = null;
      PreparedStatement ps = null;
      ResultSet rs = null;

      try
      {
         con = getDataSource().getConnection();
         ps = con.prepareStatement(SELECT_GAMES_BY_USERNAME);
         ps.setString(1, username);
         rs = ps.executeQuery();

         List<GameDto> games = new ArrayList<GameDto>();
         while(rs.next())
         {
            GameDto game = getGameFromResultSet(rs);
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
         closeConPsRs(con, ps, rs);
      }
   }

   public int selectPlayerCountFromGameByGid(int gid)
   {
      Connection con = null;
      PreparedStatement ps = null;
      ResultSet rs = null;

      try
      {
         con = getDataSource().getConnection();
         ps = con.prepareStatement(SELECT_NUMBER_OF_PLAYERS_FROM_GAME_BY_GID);
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
         closeConPsRs(con, ps, rs);
      }
   }

   public boolean insertGame(GameDto game)
   {
      Connection con = null;
      PreparedStatement ps = null;

      try
      {
         con = getDataSource().getConnection();

         ps = con.prepareStatement(INSERT_GAME);
         ps.setString(1, game.getTitle());
         ps.setBigDecimal(2, game.getStartingMoney());
         ps.setBoolean(3, game.getPrivateGame());
         ps.setTimestamp(4, new Timestamp(game.getStartTimestamp().getTime()));
         ps.executeUpdate();

         return true;
      }
      catch(Exception e)
      {
         return false;
      }
      finally
      {
         closeConPsRs(con, ps, null);
      }
   }

   private static GameDto getGameFromResultSet(ResultSet rs)
   {
      try
      {
         GameDto game = new GameDto();
         game.setGid(rs.getInt("gid"));
         game.setTitle(rs.getString("title"));
         game.setStartingMoney(rs.getBigDecimal("startingMoney"));
         game.setPrivateGame(rs.getBoolean("privateGame"));
         game.setStartTimestamp(rs.getTimestamp("startTimestamp"));
         return game;
      }
      catch(Exception e)
      {
         return null;
      }
   }
}
