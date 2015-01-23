package com.jonesdy.yql.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "query")
@XmlAccessorType(XmlAccessType.FIELD)
public class Query
{
   @XmlElement(name = "results")
   private QuoteResults results;

   public QuoteResults getQuoteResults()
   {
      return results;
   }
}
