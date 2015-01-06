package com.jonesdy.database.model;

public class DbGame
{
   private int gid;
   private String title;
   private int startingMoney;
   private boolean privateGame;

   public DbGame()
   {
      gid = 0;
      title = null;
      startingMoney = 0;
      privateGame = false;
   }

   public DbGame(int g, String t, int sm, boolean pg)
   {
      gid = g;
      title = t;
      startingMoney = sm;
      privateGame = pg;
   }

   public int getGid()
   {
      return gid;
   }

   public void setGid(int g)
   {
      gid = g;
   }

   public String getTitle()
   {
      return title;
   }

   public void setTitle(String t)
   {
      title = t;
   }

   public int getStartingMoney()
   {
      return startingMoney;
   }

   public void setStartingMoney(int sm)
   {
      startingMoney = sm;
   }

   public boolean getPrivateGame()
   {
      return privateGame;
   }

   public void setPrivateGame(boolean pg)
   {
      privateGame = pg;
   }
};
