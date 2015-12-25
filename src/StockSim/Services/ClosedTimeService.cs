using StockSim.Data.Access.Interface;
using StockSim.Services.Interface;
using System;

namespace StockSim.Services
{
   public class ClosedTimeService : IClosedTimeService
   {
      private readonly IClosedTimeDao _closedTimeDao;

      public ClosedTimeService(IClosedTimeDao closedTimeDao)
      {
         _closedTimeDao = closedTimeDao;
      }

      public string IsStockMarketOpen()
      {
         TimeZoneInfo easternZone = null;
         try
         {
            easternZone = TimeZoneInfo.FindSystemTimeZoneById("Eastern Standard Time");
         }
         catch(Exception)
         {
            // Happens on linux
            easternZone = TimeZoneInfo.FindSystemTimeZoneById("US/Eastern");
         }
         var easternTime = TimeZoneInfo.ConvertTime(DateTime.UtcNow, easternZone);
         if(easternTime.DayOfWeek == DayOfWeek.Saturday || easternTime.DayOfWeek == DayOfWeek.Sunday || easternTime.Hour >= 16 || easternTime.Hour < 9 || (easternTime.Hour == 9 && easternTime.Minute < 30))
         {
            // Normally open monday-friday, 9:30 to 16:00 eastern
            return "Outside normal working hours";
         }
         
         // These need to be in eastern
         var closedTimes = _closedTimeDao.SelectClosedTimes();
         foreach(var closedTime in closedTimes)
         {
            if(easternTime >= closedTime.StartTime && easternTime < closedTime.EndTime)
            {
               return closedTime.ClosedReason;
            }
         }

         return null;
      }
   }
}