using Microsoft.AspNet.Builder;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Logging.Console;

namespace StockSim
{
   public class Startup
   {
      public void ConfigureServices(IServiceCollection services)
      {
         services.AddMvc();
         services.AddLogging();
      }

      public void Configure(IApplicationBuilder app, ILoggerFactory loggerFactory)
      {
         var settings = new ConsoleLoggerSettings();
         loggerFactory.AddProvider(new ConsoleLoggerProvider(settings));
         //app.UseDeveloperExceptionPage();

         app.UseMvcWithDefaultRoute();

         app.UseWelcomePage();
      }
   }
}
