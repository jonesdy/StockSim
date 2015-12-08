using Microsoft.AspNet.Identity.EntityFramework;
using Microsoft.Data.Entity;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.PlatformAbstractions;
using StockSim.Data.Transfer;

namespace StockSim.Data.Access
{
   public class StockSimDbContext : IdentityDbContext<ApplicationUser>
   {
      public DbSet<GameDto> GameDtos { get; set; }
      public DbSet<PlayerDto> PlayerDtos { get; set; }
      public DbSet<StockDto> StockDtos { get; set; }
      public DbSet<TransactionDto> TransactionDtos { get; set; }

      public static string ConnectionString
      {
         get
         {
            var appEnv = CallContextServiceLocator.Locator.ServiceProvider.GetRequiredService<IApplicationEnvironment>();
            return $"Data Source={appEnv.ApplicationBasePath}/stocksim.db";
         }
      }

      protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
      {
         optionsBuilder.UseSqlite(ConnectionString);
      }
   }
}