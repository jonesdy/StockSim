package com.jonesdy.data.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.jonesdy.data.transfer.StockDto;

public class StockDao extends DatabaseDao
{
   private static final String SELECT_STOCKS_BY_PID = "SELECT sid, tickerSymbol, pid, count FROM stocks WHERE pid = ?";

   public List<StockDto> selectStocksByPid(int pid)
   {
      Connection con = null;
      PreparedStatement ps = null;
      ResultSet rs = null;

      try
      {
         con = getDataSource().getConnection();
         ps = con.prepareStatement(SELECT_STOCKS_BY_PID);
         ps.setInt(1, pid);
         rs = ps.executeQuery();

         List<StockDto> stocks = new ArrayList<StockDto>();
         while(rs.next())
         {
            StockDto stock = getStockFromResultSet(rs);
            if(stock != null)
            {
               stocks.add(stock);
            }
         }

         return stocks;
      }
      catch(Exception e)
      {
         return null;
      }
      finally
      {
         closeConPsRs(con, ps, rs);
      }
   }

   private static StockDto getStockFromResultSet(ResultSet rs)
   {
      try
      {
         StockDto stock = new StockDto();
         stock.setSid(rs.getInt("sid"));
         stock.setTickerSymbol(rs.getString("tickerSymbol"));
         stock.setPid(rs.getInt("pid"));
         stock.setCount(rs.getInt("count"));
         return stock;
      }
      catch(Exception e)
      {
         return null;
      }
   }

}
