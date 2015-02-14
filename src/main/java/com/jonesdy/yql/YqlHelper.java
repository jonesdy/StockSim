package com.jonesdy.yql;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jonesdy.yql.model.Quote;

public class YqlHelper
{
   public static final String REPLACE = "#";
   public static final String GET_QUOTE = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quote%20where%20symbol%20%3D%20%22" + REPLACE + "%22&diagnostics=false&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

   public static Quote getStockQuote(String symbol)
   {
      try
      {
         String queryString = GET_QUOTE.replace(REPLACE, symbol);
         URL url = new URL(queryString);
         HttpURLConnection connection = (HttpURLConnection)url.openConnection();
         connection.setRequestMethod("GET");
         connection.setRequestProperty("Accept", "application/xml");
         InputStream xml = connection.getInputStream();
         
         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
         DocumentBuilder db = dbf.newDocumentBuilder();
         Document dom = db.parse(xml);

         connection.disconnect();
         
         Element docEle = dom.getDocumentElement();
         NodeList nl = docEle.getElementsByTagName("quote");
         if(nl == null || nl.getLength() != 1)
         {
            return null;
         }
         
         Element el = (Element)nl.item(0);
         Quote quote = new Quote();
         quote.setSymbol(getStringValue(el, "Symbol"));
         quote.setName(getStringValue(el, "Name"));
         String lpto = getStringValue(el, "LastTradePriceOnly");

         // 1234.56 -> {"1234", "56"}
         String[] splits = lpto.split("\\.");
         int dollars = Integer.parseInt(splits[0]);
         int cents = Integer.parseInt(splits[1]);

         // Round up the cents, if needed
         if(cents >= 100)
         {
            cents = (int)roundToSignificantFigures(cents, 2);
            String c = String.valueOf(cents);
            cents = Integer.parseInt(String.valueOf(c.charAt(0)) + String.valueOf(c.charAt(1)));
         }

         quote.setCents((dollars * 100) + cents);
         return quote;
      }
      catch(Exception e)
      {
         return null;
      }
   }
   
   private static String getStringValue(Element ele, String tagName)
   {
      String value = null;
      
      NodeList nl = ele.getElementsByTagName(tagName);
      if(nl != null && nl.getLength() > 0)
      {
         Element el = (Element)nl.item(0);
         value = el.getFirstChild().getNodeValue();
      }
      
      return value;
   }

   public static double roundToSignificantFigures(double num, int n)
   {
      if(num == 0)
      {
         return 0;
      }

      final double d = Math.ceil(Math.log10(num < 0 ? -num : num));
      final int power = n - (int)d;

      final double magnitude = Math.pow(10, power);
      final long shifted = Math.round(num * magnitude);
      return shifted/magnitude;
   }
}
