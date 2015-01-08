package com.jonesdy.database.model;

public class DbUser
{
   private String username;
   private String password;
   private String email;
   private String confirmCode;
   private boolean enabled;
   private boolean confirmed;

   public DbUser()
   {
      username = null;
      password = null;
      email = null;
      confirmCode = null;
      enabled = true;
      confirmed = false;
   }

   public DbUser(String u, String p, String e, String cc, boolean en, boolean c)
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

   public void setConfirmCode(String cc)
   {
      confirmCode = cc;
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
