namespace StockSim.Services.Interface
{
   public interface IPlayerService
   {
      void AddNewPlayerToGame(int gid, string username, bool isAdmin = false);
      bool IsUserInGame(int gid, string username);
      bool RemovePlayerFromGame(int gid, string username);
      decimal GetPlayerBalance(int gid, string username);
   }
}