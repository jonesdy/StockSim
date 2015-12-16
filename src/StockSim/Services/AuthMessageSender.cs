using Microsoft.Extensions.Logging;
using System.IO;
using System.Net.Sockets;
using System.Threading.Tasks;

namespace StockSim.Services
{
   public class AuthMessageSender : IEmailSender
   {
      private readonly ILogger Log;
      private const string Server = "openstocksim.com";
      private const int Port = 25;

      public AuthMessageSender(ILoggerFactory loggerFactory)
      {
         Log = loggerFactory.CreateLogger<AuthMessageSender>();
      }

      public Task SendEmailAsync(string email, string subject, string message)
      {
         using (var client = new TcpClient())
         {
            var connect = client.ConnectAsync(Server, Port);
            connect.Wait();
            using (var stream = client.GetStream())
            {
               using (var reader = new StreamReader(stream))
               {
                  using (var writer = new StreamWriter(stream) { AutoFlush = true })
                  {
                     writer.WriteLine(string.Format("HELO {0}", Server));
                     writer.WriteLine("MAIL FROM: <noreply@openstocksim.com>");
                     writer.WriteLine(string.Format("RCPT TO: <{0}>", email));
                     writer.WriteLine("DATA");
                     writer.WriteLine("From: <noreply@openstocksim.com>");
                     writer.WriteLine(string.Format("To: <{0}>", email));
                     writer.WriteLine(string.Format("Subject: {0}", subject));
                     writer.WriteLine();
                     writer.WriteLine(message);
                     writer.WriteLine(".");
                     writer.WriteLine("QUIT");
                  }
               }
            }
         }
         return Task.FromResult(0);
      }
   }
}
