package com.jonesdy.data.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

public abstract class DatabaseDao
{
   private JndiDataSourceLookup dsLookup;
   private DataSource dataSource;

   public DatabaseDao()
   {
      dsLookup = new JndiDataSourceLookup();
      dsLookup.setResourceRef(true);
      dataSource = dsLookup.getDataSource("java:comp/env/jdbc/stocksimdb");
   }

   protected DataSource getDataSource()
   {
      return dataSource;
   }

   protected static void closeConPsRs(Connection con, PreparedStatement ps, ResultSet rs)
   {
      try
      {
         if(con != null)
         {
            con.close();
         }
         if(ps != null)
         {
            ps.close();
         }
         if(rs != null)
         {
            rs.close();
         }
      }
      catch(Exception e)
      {
         // Nothing we can do
      }
   }
}
