package com.jonesdy.yql.model;

public class Quote
{
   private String symbol;
   private String name;
   private int cents;

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

   public int getCents()
   {
      return cents;
   }
   
   public void setCents(int c)
   {
      cents = c;
   }
}
