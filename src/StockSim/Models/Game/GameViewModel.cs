namespace StockSim.Models.Game
{
   public class GameViewModel
   {
      public int Gid { get; set; }
      public string Title { get; set; }
      public bool Private { get; set; }
      public int PlayerCount { get; set; }
      public decimal StartingMoney { get; set; }
   }
}