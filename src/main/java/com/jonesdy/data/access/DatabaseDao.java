package com.jonesdy.data.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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
}
