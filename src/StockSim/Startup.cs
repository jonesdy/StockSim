﻿using Microsoft.AspNet.Builder;
using Microsoft.AspNet.Hosting;
using Microsoft.AspNet.Identity.EntityFramework;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.PlatformAbstractions;
using StockSim.Data.Access;
using StockSim.Data.Access.Interface;
using StockSim.Data.Transfer;
using StockSim.Services;
using StockSim.Services.Interface;
using System;

namespace StockSim
{
   public class Startup
   {
      public static IConfigurationRoot Configuration { get; private set; }
      public static IServiceProvider ServiceProvider { get; private set; }

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

         // DAOs
         services.AddTransient<IStockQuoteDao, StockQuoteDao>();
         services.AddTransient<IGameDao, GameDao>();
         services.AddTransient<IPlayerDao, PlayerDao>();
         services.AddTransient<IStockDao, StockDao>();
         services.AddTransient<ITransactionDao, TransactionDao>();
         services.AddTransient<IClosedTimeDao, ClosedTimeDao>();

         // Services
         services.AddTransient<IEmailService, EmailService>();
         services.AddTransient<IGameService, GameService>();
         services.AddTransient<IPlayerService, PlayerService>();
         services.AddTransient<IStockService, StockService>();
         services.AddTransient<ITransactionService, TransactionService>();
         services.AddTransient<IClosedTimeService, ClosedTimeService>();
         ServiceProvider = services.BuildServiceProvider();
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
