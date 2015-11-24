using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace StockSim.Data.Transfer
{
   [Table("Stock")]
   public class StockDto
   {
      [Key]
      public int Sid { get; set; }
      public string TickerSymbol { get; set; }
      public int Pid { get; set; }
      public int Count { get; set; }
   }
}
