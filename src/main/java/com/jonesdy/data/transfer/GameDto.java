package com.jonesdy.data.transfer;

import java.sql.Date;
import java.math.BigDecimal;

public class GameDto
{
   private int gid;
   private String title;
   private BigDecimal startingMoney;
   private boolean privateGame;
   private Date startTimestamp;

   public GameDto()
   {
      gid = 0;
      title = null;
      startingMoney = new BigDecimal(0);
      privateGame = false;
      startTimestamp = null;
   }

   public GameDto(int g, String t, BigDecimal sm, boolean pg, Date st)
   {
      gid = g;
      title = t;
      startingMoney = sm;
      privateGame = pg;
      startTimestamp = st;
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

   public BigDecimal getStartingMoney()
   {
      return startingMoney;
   }

   public void setStartingMoney(BigDecimal sm)
   {
      startingMoney = sm;
   }

   public boolean getPrivateGame()
   {
      return privateGame;
   }

   public void setPrivateGame(boolean pg)
   {
      privateGame = pg;
   }

   public Date getStartTimestamp()
   {
      return startTimestamp;
   }

   public void setStartTimestamp(Date st)
   {
      startTimestamp = st;
   }
}
