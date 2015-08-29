package com.jonesdy.data.transfer;

public class UserDto
{
   private String username;
   private String password;         // Hashed of course
   private String email;
   private String confirmCode;
   private boolean enabled;
   private boolean confirmed;

   public UserDto()
   {
      username = null;
      password = null;
      email = null;
      confirmCode = null;
      enabled = false;
      confirmed = false;
   }

   public UserDto(String u, String p, String e, String cc, boolean en, boolean c)
   {
      username = u;
      password = p;
      email = e;
      confirmCode = cc;
      enabled = en;
      confirmed = c;
   }

   public String getUsername()
   {
      return username;
   }

   public void setUsername(String u)
   {
      username = u;
   }

   public String getPassword()
   {
      return password;
   }

   public void setPassword(String p)
   {
      password = p;
   }

   public String getEmail()
   {
      return email;
   }

   public void setEmail(String e)
   {
      email = e;
   }

   public String getConfirmCode()
   {
      return confirmCode;
   }

   public void setConfirmCode(String code)
   {
      confirmCode = code;
   }

   public boolean getEnabled()
   {
      return enabled;
   }

   public void setEnabled(boolean en)
   {
      enabled = en;
   }

   public boolean getConfirmed()
   {
      return confirmed;
   }

   public void setConfirmed(boolean c)
   {
      confirmed = c;
   }
}
