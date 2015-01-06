package com.jonesdy.database.model;

public class DbTransaction
{
   private int tid;
   private int sid;
   private int count;
   private int price;
   private long timestamp;
   private boolean buy;

   public DbTransaction()
   {
      tid = 0;
      sid = 0;
      count = 0;
      price = 0;
      timestamp = 0;
      buy = false;
   }

   public DbTransaction(int t, int s, int c, int p, long ts, boolean b)
   {
      tid = t;
      sid = s;
      count = c;
      price = p;
      timestamp = ts;
      buy = b;
   }

   public int getTid()
   {
      return tid;
   }

   public void setTid(int t)
   {
      tid = t;
   }

   public int getSid()
   {
      return sid;
   }

   public void setSid(int s)
   {
      sid = s;
   }

   public int getCount()
   {
      return count;
   }

   public void setCount(int c)
   {
      count = c;
   }

   public int getPrice()
   {
      return price;
   }

   public void setPrice(int p)
   {
      price = p;
   }

   public long getTimestamp()
   {
      return timestamp;
   }

   public void setTimestamp(long ts)
   {
      timestamp = ts;
   }

   public boolean getBuy()
   {
      return buy;
   }

   public void setBuy(boolean b)
   {
      buy = b;
   }
}
