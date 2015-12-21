using System.Threading.Tasks;

namespace StockSim.Services.Interface
{
   public interface IEmailService
   {
      Task SendEmailAsync(string email, string subject, string message);
   }
}