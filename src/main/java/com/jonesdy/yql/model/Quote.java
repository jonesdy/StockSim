package com.jonesdy.yql.model;

public class Quote
{
   private String symbol;
   private String name;
   private String lastTradePriceOnly;

   public String getSymbol()
   {
      return symbol;
   }
   
   public void setSymbol(String s)
   {
      symbol = s;
   }

   public String getName()
   {
      return name;
   }
   
   public void setName(String n)
   {
      name = n;
   }

   public String getLastTradePriceOnly()
   {
      return lastTradePriceOnly;
   }
   
   public void setLastTradePriceOnly(String ltpo)
   {
      lastTradePriceOnly = ltpo;
   }
}
