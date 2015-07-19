package com.jonesdy.data.transfer;

import java.math.BigDecimal;
import java.sql.Date;

public class TransactionDto
{
   private int tid;
   private int sid;
   private int count;
   private BigDecimal price;
   private Date timestamp;
   private boolean buy;

   public TransactionDto()
   {
      tid = 0;
      sid = 0;
      count = 0;
      price = new BigDecimal(0);
      timestamp = null;
      buy = false;
   }

   public TransactionDto(int t, int s, int c, BigDecimal p, Date ts, boolean b)
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

   public BigDecimal getPrice()
   {
      return price;
   }

   public void setPrice(BigDecimal p)
   {
      price = p;
   }

   public Date getTimestamp()
   {
      return timestamp;
   }

   public void setTimestamp(Date ts)
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
