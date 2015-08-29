package com.jonesdy.data.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.jonesdy.data.transfer.UserDto;
import com.jonesdy.data.transfer.UserRoleDto;
import com.jonesdy.data.access.UserRoleDao;

public class UserDao extends DatabaseDao
{
   private static final String SELECT_USER_BY_USERNAME = "SELECT username, password, email, confirmCod, enabled, confirmed FROM users WHERE username = ?";
   private static final String SELECT_USER_BY_CONFIRM_CODE = "SELECT username, password, email, confirmCod, enabled, confirmed FROM users WHERE confirmCode = ?";
   private static final String SET_CONFIRMED_BY_CONFIRM_CODE = "UPDATE users SET confirmed = true WHERE confirmCode = ?";
   private static final String INSERT_USER = "INSERT INTO users (username, password, email, confirmCode, enabled, confirmed) VALUES (?, ?, ?, ?, ?, ?)";
   private static final String DELETE_USER_BY_USERNAME = "DELETE FROM users WHERE username = ?";

   public UserDto SelectUserByUsername(String username)
   {
      Connection con = null;
      PreparedStatement ps = null;
      ResultSet rs = null;

      try
      {
         con = getDataSource().getConnection();
         ps = con.prepareStatement(SELECT_USER_BY_USERNAME);
         ps.setString(1, username);
         rs = ps.executeQuery();
         if(!rs.next())
         {
            return null;
         }

         return getUserFromResultSet(rs);
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

   public UserDto SelectUserByConfirmCode(String confirmCode)
   {
      Connection con = null;
      PreparedStatement ps = null;
      ResultSet rs = null;

      try
      {
         con = getDataSource().getConnection();
         ps = con.prepareStatement(SELECT_USER_BY_CONFIRM_CODE);
         ps.setString(1, confirmCode);
         rs = ps.executeQuery();
         if(!rs.next())
         {
            return null;
         }

         return getUserFromResultSet(rs);
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

   public boolean setConfirmedByConfirmCode(String confirmCode)
   {
      Connection con = null;
      PreparedStatement ps = null;

      try
      {
         con = getDataSource().getConnection();
         ps = con.prepareStatement(SET_CONFIRMED_BY_CONFIRM_CODE);
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
         closeConPsRs(con, ps, null);
      }
   }

   public boolean insertUser(UserDto user)
   {
      Connection con = null;
      PreparedStatement ps = null;

      try
      {
         con = getDataSource().getConnection();

         // Add the user first
         ps = con.prepareStatement(INSERT_USER);
         ps.setString(1, user.getUsername());
         ps.setString(2, user.getPassword());
         ps.setString(3, user.getEmail());
         ps.setString(4, user.getConfirmCode());
         ps.setBoolean(5, user.getEnabled());
         ps.setBoolean(6, user.getConfirmed());
         ps.executeUpdate();

         // Then add the user role
         UserRoleDto role = new UserRoleDto();
         role.setUsername(user.getUsername());
         role.setRole("ROLE_USER");
         UserRoleDao dao = new UserRoleDao();
         return dao.insertUserRole(role);
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

   public boolean deleteUserByUsername(String username)
   {
      Connection con = null;
      PreparedStatement ps = null;

      try
      {
         con = getDataSource().getConnection();

         // Remove the user roles first
         UserRoleDao dao = new UserRoleDao();
         if(!dao.deleteUserRolesByUsername(username))
         {
            return false;
         }

         // Then remove the user
         ps = con.prepareStatement(DELETE_USER_BY_USERNAME);
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
         closeConPsRs(con, ps, null);
      }
   }

   private UserDto getUserFromResultSet(ResultSet rs)
   {
      try
      {
         UserDto user = new UserDto();
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
