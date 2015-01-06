package com.jonesdy.web.model;

public class WebGame
{
   private int gid;
   private String title;

   public WebGame(int g, String t)
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
