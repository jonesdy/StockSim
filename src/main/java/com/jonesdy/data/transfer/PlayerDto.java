package com.jonesdy.data.transfer;

import java.math.BigDecimal;

public class PlayerDto
{
   private int pid;
   private String username;
   private int gid;
   private BigDecimal balance;
   private boolean isAdmin;
   private String inviteCode;
   private boolean enabled;

   public PlayerDto()
   {
      pid = 0;
      username = null;
      gid = 0;
      balance = new BigDecimal(0);
      isAdmin = false;
      inviteCode = null;
      enabled = false;
   }

   public PlayerDto(int p, String user, int g, BigDecimal bal, boolean ia, String ic, boolean e)
   {
      pid = p;
      username = user;
      gid = g;
      balance = bal;
      isAdmin = ia;
      inviteCode = ic;
      enabled = e;
   }

   public int getPid()
   {
      return pid;
   }

   public void setPid(int p)
   {
      pid = p;
   }

   public String getUsername()
   {
      return username;
   }

   public void setUsername(String user)
   {
      username = user;
   }

   public int getGid()
   {
      return gid;
   }

   public void setGid(int g)
   {
      gid = g;
   }

   public BigDecimal getBalance()
   {
      return balance;
   }

   public void setBalance(BigDecimal bal)
   {
      balance = bal;
   }

   public boolean getIsAdmin()
   {
      return isAdmin;
   }

   public void setIsAdmin(boolean ia)
   {
      isAdmin = ia;
   }

   public String getInviteCode()
   {
      return inviteCode;
   }

   public void setInviteCode(String ic)
   {
      inviteCode = ic;
   }

   public boolean getEnabled()
   {
      return enabled;
   }

   public void setEnabled(boolean e)
   {
      enabled = e;
   }
}
