using Microsoft.Extensions.Logging;
using StockSim.Services.Interface;
using System;
using System.IO;
using System.Net.Sockets;
using System.Threading.Tasks;

namespace StockSim.Services
{
   public class EmailService : IEmailService
   {
      private readonly ILogger _log;
      private const string Server = "openstocksim.com";
      private const int Port = 25;
      private const string OkResponse = "250";

      public EmailService(ILoggerFactory loggerFactory)
      {
         _log = loggerFactory.CreateLogger<EmailService>();
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
                     var input = reader.ReadLine();
                     if (!input.StartsWith(OkResponse))
                     {
                        throw new InvalidOperationException($"Failed to send email. Response from email server: {input}");
                     }
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
