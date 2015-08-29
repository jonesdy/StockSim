package com.jonesdy.data.access;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.jonesdy.data.transfer.UserRoleDto;

public class UserRoleDao extends DatabaseDao
{
   private static final String INSERT_USER_ROLE = "INSERT INTO user_roles (username, role) VALUES (?, ?)";
   private static final String DELETE_USER_ROLES_BY_USERNAME = "DELETE FROM user_roles WHERE username = ?";

   public boolean insertUserRole(UserRoleDto role)
   {
      Connection con = null;
      PreparedStatement ps = null;

      try
      {
         con = getDataSource().getConnection();
         ps = con.prepareStatement(INSERT_USER_ROLE);
         ps.setString(1, role.getUsername());
         ps.setString(2, role.getRole());
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

   public boolean deleteUserRolesByUsername(String username)
   {
      Connection con = null;
      PreparedStatement ps = null;

      try
      {
         con = getDataSource().getConnection();

         ps = con.prepareStatement(DELETE_USER_ROLES_BY_USERNAME);
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
}
