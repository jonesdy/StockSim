package com.jonesdy.database.model;

public class DbUser
{
   private String username;
   private String passwordHash;
   private String email;
   private String confirmCode;
   private boolean enabled;
   private boolean confirmed;

   public DbUser()
   {
      username = null;
      passwordHash = null;
      email = null;
      confirmCode = null;
      enabled = true;
      confirmed = false;
   }

   public DbUser(String u, String ph, String e, String cc, boolean en, boolean c)
   {
      username = u;
      passwordHash = ph;
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

   public String getPasswordHash()
   {
      return passwordHash;
   }

   public void setPasswordHash(String ph)
   {
      passwordHash = ph;
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
