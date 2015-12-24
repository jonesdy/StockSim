using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace StockSim.Data.Transfer
{
   [Table("ClosedTime")]
   public class ClosedTimeDto
   {
      [Key]
      public int Cid { get; set; }
      public string ClosedReason { get; set; }
      public DateTime StartTime { get; set; }
      public DateTime EndTime { get; set; }
   }
}