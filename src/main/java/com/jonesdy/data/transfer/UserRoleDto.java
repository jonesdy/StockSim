package com.jonesdy.data.transfer;

public class UserRoleDto
{
   private int userRoleId;
   private String username;
   private String role;

   public UserRoleDto()
   {
      userRoleId = 0;
      username = null;
      role = null;
   }

   public UserRoleDto(int id, String user, String r)
   {
      userRoleId = id;
      username = user;
      role = r;
   }

   public int getUserRoleId()
   {
      return userRoleId;
   }

   public void setUserRoleId(int id)
   {
      userRoleId = id;
   }

   public String getUsername()
   {
      return username;
   }

   public void setUsername(String user)
   {
      username = user;
   }

   public String getRole()
   {
      return role;
   }

   public void setRole(String r)
   {
      role = r;
   }
}
