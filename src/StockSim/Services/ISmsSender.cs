using System.Threading.Tasks;

namespace StockSim.Services
{
   public interface ISmsSender
   {
      Task SendSmsAsync(string number, string message);
   }
}