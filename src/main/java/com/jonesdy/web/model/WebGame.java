package com.jonesdy.web.model;

public class WebGame
{
   private int gid;
   private String title;
   private int playerCount;

   public WebGame(int g, String t, int pc)
   {
      gid = g;
      title = t;
      playerCount = pc;
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

   public int getPlayerCount()
   {
      return playerCount;
   }

   public void setPlayerCount(int pc)
   {
      playerCount = pc;
   }
}
