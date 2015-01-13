package com.jonesdy.web.model;

public class FormGame
{
   private String title;
   private int startingMoney;
   private boolean privateGame;

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

   public boolean isPrivateGame()
   {
      return privateGame;
   }

   public void setPrivateGame(boolean pg)
   {
      privateGame = pg;
   }
}
