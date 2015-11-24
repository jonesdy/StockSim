using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace StockSim.Data.Transfer
{
   [Table("Game")]
   public class GameDto
   {
      [Key]
      public int Gid { get; set; }
      public string Title { get; set; }
      public decimal StartingMoney { get; set; }
      public bool Private { get; set; }
      public DateTime StartTimestamp { get; set; }
   }
}