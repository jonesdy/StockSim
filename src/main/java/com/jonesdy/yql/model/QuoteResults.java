package com.jonesdy.yql.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "results")
@XmlAccessorType(XmlAccessType.FIELD)
public class QuoteResults
{
   @XmlElement(name = "quote")
   private ArrayList<Quote> quotes;

   public ArrayList<Quote> getQuotes()
   {
      return quotes;
   }
}
