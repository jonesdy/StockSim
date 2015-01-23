package com.jonesdy.yql;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.jonesdy.yql.model.Quote;
import com.jonesdy.yql.model.QuoteResults;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class YqlHelper
{
   public static final String REPLACE = "?";
   public static final String GET_QUOTE = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quote%20where%20symbol%20%3D%20%22" + REPLACE + "%22&diagnostics=false&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

   public static Quote getStockQuote(String symbol)
   {
      try
      {
         String query = GET_QUOTE.replace(REPLACE, symbol);
         URL url = new URL(query);
         HttpURLConnection connection = (HttpURLConnection)url.openConnection();
         connection.setRequestMethod("GET");
         connection.setRequestProperty("Accept", "application/xml");

         JAXBContext jc = JAXBContext.newInstance(QuoteResults.class);
         InputStream xml = connection.getInputStream();

         Unmarshaller unmarshaller = jc.createUnmarshaller();
         QuoteResults results = (QuoteResults)unmarshaller.unmarshal(xml);

         connection.disconnect();

         ArrayList<Quote> quotes = results.getQuotes();

         if(quotes.size() != 1)
         {
            return null;
         }

         return quotes.get(0);
      }
      catch(Exception e)
      {
         return null;
      }
   }
}
