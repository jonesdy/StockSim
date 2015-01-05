package com.jonesdy.model;

public class Game
{
   private int gid;
   private String title;

   public Game(int g, String t)
   {
      gid = g;
      title = t;
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
}
