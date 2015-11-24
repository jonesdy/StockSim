using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace StockSim.Data.Transfer
{
   [Table("Player")]
   public class PlayerDto
   {
      [Key]
      public int Pid { get; set; }
      public string Username { get; set; }
      public int Gid { get; set; }
      public decimal Balance { get; set; }
      public bool IsAdmin { get; set; }
      public string InviteCode { get; set; }
      public bool Enabled { get; set; }
   }
}
