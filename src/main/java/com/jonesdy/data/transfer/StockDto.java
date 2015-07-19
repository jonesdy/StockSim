package com.jonesdy.data.transfer;

public class StockDto
{
   private int sid;
   private String tickerSymbol;
   private int pid;
   private int count;

   public StockDto()
   {
      sid = 0;
      tickerSymbol = null;
      pid = 0;
      count = 0;
   }

   public StockDto(int s, String ts, int p, int c)
   {
      sid = s;
      tickerSymbol = ts;
      pid = p;
      count = c;
   }

   public int getSid()
   {
      return sid;
   }

   public void setSid(int s)
   {
      sid = s;
   }

   public String getTickerSymbol()
   {
      return tickerSymbol;
   }

   public void setTickerSymbol(String ts)
   {
      tickerSymbol = ts;
   }

   public int getPid()
   {
      return pid;
   }

   public void setPid(int p)
   {
      pid = p;
   }

   public int getCount()
   {
      return count;
   }

   public void setCount(int c)
   {
      count = c;
   }
}
