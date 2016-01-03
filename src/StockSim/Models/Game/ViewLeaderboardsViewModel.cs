using System.Collections.Generic;

namespace StockSim.Models.Game
{
   public class ViewLeaderboardsViewModel
   {
      public int Gid { get; set; }
      public string GameTitle { get; set; }
      public IList<LeaderboardViewModel> Leaderboards { get; set; }
   }
}