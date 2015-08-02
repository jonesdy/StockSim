package com.jonesdy.data.access;

public class StockDao extends DatabaseDao
{
   private static final String SELECT_STOCKS_BY_PID = "SELECT sid, tickerSymbol, pid, count FROM stocks WHERE pid = ?";
}
