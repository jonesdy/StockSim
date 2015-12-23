using StockSim.Data.Access.Interface;
using StockSim.Data.Transfer;
using StockSim.Services.Interface;

namespace StockSim.Services
{
   public class PlayerService : IPlayerService
   {
      private readonly IPlayerDao _playerDao;
      private readonly IGameDao _gameDao;

      public PlayerService(IPlayerDao playerDao, IGameDao gameDao)
      {
         _playerDao = playerDao;
         _gameDao = gameDao;
      }

      public void AddNewPlayerToGame(int gid, string username, bool isAdmin = false)
      {
         var game = _gameDao.SelectGameByGid(gid);

         _playerDao.InsertPlayer(new PlayerDto
         {
            Username = username,
            Gid = gid,
            Balance = game.StartingMoney,
            IsAdmin = isAdmin,
            InviteCode = null,
            Enabled = true
         });
      }

      public bool IsUserInGame(int gid, string username)
      {
         return _playerDao.IsUserInGame(gid, username);
      }

      public bool RemovePlayerFromGame(int gid, string username)
      {
         return _playerDao.DeletePlayerByGidAndUsername(gid, username);
      }
   }
}