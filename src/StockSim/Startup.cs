using Microsoft.AspNet.Builder;
using Microsoft.AspNet.Identity.EntityFramework;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using StockSim.Data.Access;
using StockSim.Data.Transfer;

namespace StockSim
{
   public class Startup
   {
      public void ConfigureServices(IServiceCollection services)
      {
         services.AddEntityFramework()
            .AddSqlite()
            .AddDbContext<StockSimDbContext>();
         services.AddIdentity<ApplicationUser, IdentityRole>()
            .AddEntityFrameworkStores<StockSimDbContext>()
            .AddDefaultTokenProviders();
         services.AddMvc();
         services.AddLogging();
      }

      public void Configure(IApplicationBuilder app, ILoggerFactory loggerFactory)
      {
         loggerFactory.MinimumLevel = LogLevel.Information;
         loggerFactory.AddConsole();
         app.UseDeveloperExceptionPage();

         app.UseMvcWithDefaultRoute();

         app.UseWelcomePage();

         app.UseStaticFiles();

         app.UseIdentity();
      }
   }
}
