using Microsoft.AspNet.Identity.EntityFramework;
using Microsoft.Data.Entity;
using StockSim.Data.Transfer;

namespace StockSim.Data.Access
{
   public class StockSimIdentityDbContext : IdentityDbContext<ApplicationUser>
   {
      /*protected override void OnModelCreating(ModelBuilder builder)
      {
         base.OnModelCreating(builder);
      }*/

      protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
      {
         optionsBuilder.UseSqlite(StockSimDbContext.ConnectionString);
      }
   }
}