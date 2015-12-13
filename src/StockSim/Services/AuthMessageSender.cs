using System.Threading.Tasks;
using MailKit.Net.Smtp;
using MimeKit;

namespace StockSim.Services
{
   public class AuthMessageSender : IEmailSender
   {
      public Task SendEmailAsync(string email, string subject, string message)
      {
         var mimeMessage = new MimeMessage();
         mimeMessage.From.Add(new MailboxAddress("OpenStockSim", "noreply@openstocksim.com"));
         mimeMessage.To.Add(new MailboxAddress(email, email));
         mimeMessage.Subject = subject;
         mimeMessage.Body = new TextPart("plain") { Text = message };
         using(var client = new SmtpClient())
         {
            client.Connect("openstocksim.com", 587, false);
            client.AuthenticationMechanisms.Remove("XOAUTH2");
            client.Send(mimeMessage);
            client.Disconnect(true);
         }
         return Task.FromResult(0);
      }
   }
}