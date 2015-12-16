namespace StockSim.Data.Transfer
{
   public class StockQuoteDto
   {
      public string Symbol { get; set; }
      public string Name { get; set; }
      public decimal Cost { get; set; }
   }
}