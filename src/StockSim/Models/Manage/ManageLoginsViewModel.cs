using Microsoft.AspNet.Http.Authentication;
using Microsoft.AspNet.Identity;
using System.Collections.Generic;

namespace StockSim.Models.Manage
{
   public class ManageLoginsViewModel
   {
      public IList<UserLoginInfo> CurrentLogins { get; set; }

      public IList<AuthenticationDescription> OtherLogins { get; set; }
   }
}