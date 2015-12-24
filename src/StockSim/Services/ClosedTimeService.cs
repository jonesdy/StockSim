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
         var easternZone = TimeZoneInfo.FindSystemTimeZoneById("Eastern Standard Time");
         var easternTime = TimeZoneInfo.ConvertTime(DateTime.UtcNow, easternZone);
         if(easternTime.DayOfWeek == DayOfWeek.Saturday || easternTime.DayOfWeek == DayOfWeek.Sunday)
         {
            // Never open on weekends
            return "Outside normal working hours";
         }
         if(easternTime.Hour >= 16 || easternTime.Hour < 9 || (easternTime.Hour == 9 && easternTime.Minute < 30))
         {
            // Open from 9:30 to 16:00, normally
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