package com.jonesdy.yql.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Quote
{
   @XmlElement(name = "Symbol")
   private String symbol;

   @XmlElement(name = "Name")
   private String name;

   @XmlElement(name = "LastTradePriceOnly")
   private String lastTradePriceOnly;

   public String getSymbol()
   {
      return symbol;
   }

   public String getName()
   {
      return name;
   }

   public String getLastTradePriceOnly()
   {
      return lastTradePriceOnly;
   }
}
