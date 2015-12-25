using Microsoft.AspNet.Identity;
using Microsoft.Extensions.DependencyInjection;
using StockSim.Data.Transfer;
using System.ComponentModel.DataAnnotations;

namespace StockSim
{
   public class UniqueUserEmailAttribute : ValidationAttribute
   {
      public override bool IsValid(object value)
      {
         var userManager = Startup.ServiceProvider.GetService<UserManager<ApplicationUser>>();
         var task = userManager.FindByEmailAsync(value.ToString());
         task.Wait();
         var existing = task.Result;
         if(existing != null)
         {
            ErrorMessage = "This email is already being used.";
            return false;
         }
         return true;
      }
   }
}
