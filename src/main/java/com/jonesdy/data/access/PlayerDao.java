package com.jonesdy.data.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.jonesdy.data.transfer.PlayerDto;

public class PlayerDao extends DatabaseDao
{
   private static final String SELECT_PLAYERS_BY_USERNAME = "SELECT pid, username, gid, balance, isAdmin, inviteCode, enabled FROM players WHERE username = ?";
   private static final String SELECT_PLAYER_BY_USERNAME_AND_GID = "SELECT pid, username, gid, balance, isAdmin, inviteCode, enabled FROM players WHERE username = ? AND gid = ?";
   private static final String SELECT_PLAYER_BY_INVITE_CODE = "SELECT * FROM players WHERE inviteCode = ?";
   private static final String INSERT_PLAYER = "INSERT INTO players (username, gid, balance, isAdmin, inviteCode, enabled) VALUES (?, ?, ?, ?, ?, ?)";
   private static final String SET_ENABLED_BY_INVITE_CODE = "UPDATE players SET enabled = true WHERE inviteCode = ?";
   private static final String DELETE_PLAYER_BY_INVITE_CODE = "DELETE FROM players WHERE inviteCode = ?";

   public List<PlayerDto> selectPlayersByUsername(String username)
   {
      Connection con = null;
      PreparedStatement ps = null;
      ResultSet rs = null;

      try
      {
         con = getDataSource().getConnection();
         ps = con.prepareStatement(SELECT_PLAYERS_BY_USERNAME);
         ps.setString(1, username);
         rs = ps.executeQuery();

         List<PlayerDto> players = new ArrayList<PlayerDto>();
         while(rs.next())
         {
            PlayerDto player = getPlayerFromResultSet(rs);
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
         closeConPsRs(con, ps, rs);
      }
   }

   public PlayerDto selectPlayerByUsernameAndGid(String username, int gid)
   {
      Connection con = null;
      PreparedStatement ps = null;
      ResultSet rs = null;

      try
      {
         con = getDataSource().getConnection();
         ps = con.prepareStatement(SELECT_PLAYER_BY_USERNAME_AND_GID);
         ps.setString(1, username);
         ps.setInt(2, gid);
         rs = ps.executeQuery();
         if(!rs.next())
         {
            return null;
         }

         return getPlayerFromResultSet(rs);
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

   public PlayerDto selectPlayerByInviteCode(String inviteCode)
   {
      Connection con = null;
      PreparedStatement ps = null;
      ResultSet rs = null;

      try
      {
         con = getDataSource().getConnection();
         ps = con.prepareStatement(SELECT_PLAYER_BY_INVITE_CODE);
         ps.setString(1, inviteCode);
         rs = ps.executeQuery();
         if(!rs.next())
         {
            return null;
         }

         return getPlayerFromResultSet(rs);
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

   public boolean insertPlayer(PlayerDto player)
   {
      Connection con = null;
      PreparedStatement ps = null;

      try
      {
         con = getDataSource().getConnection();

         ps = con.prepareStatement(INSERT_PLAYER);
         ps.setString(1, player.getUsername());
         ps.setInt(2, player.getGid());
         ps.setBigDecimal(3, player.getBalance());
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
         closeConPsRs(con, ps, null);
      }
   }

   public boolean setEnabledByInviteCode(String inviteCode)
   {
      Connection con = null;
      PreparedStatement ps = null;

      try
      {
         PlayerDto player = selectPlayerByInviteCode(inviteCode);
         if(player == null || player.getEnabled())
         {
            return false;
         }

         con = getDataSource().getConnection();
         ps = con.prepareStatement(SET_ENABLED_BY_INVITE_CODE);
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
         closeConPsRs(con, ps, null);
      }
   }

   public boolean deletePlayerByInviteCode(String inviteCode)
   {
      Connection con = null;
      PreparedStatement ps = null;

      try
      {
         con = getDataSource().getConnection();

         ps = con.prepareStatement(DELETE_PLAYER_BY_INVITE_CODE);
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
         closeConPsRs(con, ps, null);
      }
   }

   private PlayerDto getPlayerFromResultSet(ResultSet rs)
   {
      try
      {
         PlayerDto player = new PlayerDto();
         player.setPid(rs.getInt("pid"));
         player.setUsername(rs.getString("username"));
         player.setGid(rs.getInt("gid"));
         player.setBalance(rs.getBigDecimal("balance"));
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
}
