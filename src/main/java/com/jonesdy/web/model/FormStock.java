package com.jonesdy.web.model;

public class FormStock
{
   private String symbol;
   private int amount;
   private String game;

   public String getSymbol()
   {
      return symbol;
   }

   public void setSymbol(String s)
   {
      symbol = s;
   }

   public int getAmount()
   {
      return amount;
   }

   public void setAmount(int a)
   {
      amount = a;
   }

   public String getGame()
   {
      return game;
   }

   public void setGame(String g)
   {
      game = g;
   }
}
