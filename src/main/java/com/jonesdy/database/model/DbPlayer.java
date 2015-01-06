package com.jonesdy.database.model;

public class DbPlayer
{
   private int pid;
   private String username;
   private int gid;
   private int balance;
   private boolean isAdmin;

   public DbPlayer()
   {
      pid = 0;
      username = null;
      gid = 0;
      balance = 0;
      isAdmin = false;
   }

   public DbPlayer(int p, String u, int g, int b, boolean ia)
   {
      pid = p;
      username = u;
      gid = g;
      balance = b;
      isAdmin = ia;
   }

   public int getPid()
   {
      return pid;
   }

   public void setPid(int p)
   {
      pid = p;
   }

   public String getUsername()
   {
      return username;
   }

   public void setUsername(String u)
   {
      username = u;
   }

   public int getGid()
   {
      return gid;
   }

   public void setGid(int g)
   {
      gid = g;
   }

   public int getBalance()
   {
      return balance;
   }

   public void setBalance(int b)
   {
      balance = b;
   }

   public boolean getIsAdmin()
   {
      return isAdmin;
   }

   public void setIsAdmin(boolean ia)
   {
      isAdmin = ia;
   }
}
