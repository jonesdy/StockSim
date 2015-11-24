using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace StockSim.Data.Transfer
{
   [Table("Transaction")]
   public class TransactionDto
   {
      [Key]
      public int Tid { get; set; }
      public int Sid { get; set; }
      public int Count { get; set; }
      public decimal Price { get; set; }
      public DateTime Timestamp { get; set; }
      public bool Buy { get; set; }
   }
}
