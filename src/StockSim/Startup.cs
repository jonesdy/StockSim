using Microsoft.AspNet.Builder;
using Microsoft.AspNet.Hosting;
using Microsoft.AspNet.Identity.EntityFramework;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.PlatformAbstractions;
using StockSim.Data.Access;
using StockSim.Data.Transfer;
using StockSim.Services;

namespace StockSim
{
   public class Startup
   {
      public IConfigurationRoot Configuration { get; private set; }

      public Startup(IHostingEnvironment env, IApplicationEnvironment appEnv)
      {
         var builder = new ConfigurationBuilder()
            .SetBasePath(appEnv.ApplicationBasePath);
            /*.AddJsonFile("appsettings.json")
            .AddJsonFile($"appsettings.{env.EnvironmentName}.json", true);*/

         builder.AddEnvironmentVariables();
         Configuration = builder.Build();
      }

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

         services.AddTransient<IEmailSender, AuthMessageSender>();
      }

      public void Configure(IApplicationBuilder app, ILoggerFactory loggerFactory)
      {
         loggerFactory.MinimumLevel = LogLevel.Information;
         loggerFactory.AddConsole();
         loggerFactory.AddDebug();

         app.UseDeveloperExceptionPage();
         //app.UseExceptionHandler("/Home/Error");
         app.UseStaticFiles();
         app.UseIdentity();
         app.UseMvcWithDefaultRoute();
      }
   }
}
