using System.Threading.Tasks;

namespace StockSim.Services
{
   public interface IEmailSender
   {
      Task SendEmailAsync(string email, string subject, string message);
   }
}