var titlePrestine;
var startingMoneyPrestine;

function initializeForm()
{
   // Initialize prestine variables, make sure error messages aren't displayed
   titlePrestine = true;
   startingMoneyPrestine = true;

   document.getElementById("titleError").innerHTML = "";
   document.getElementById("titleErrorDiv").style.display = "none";
   document.getElementById("startingMoneyError").innerHTML = "";
   document.getElementById("startingMoneyErrorDiv").style.display = "none";
}

function validateTitle()
{
   var valid = true;
   var titleElement = document.getElementById("title");

   if(titleElement.value.length != 0)
   {
      titlePrestine = false;
   }

   if(titlePrestine)
   {
      document.getElementById("titleError").innerHTML = "";
      document.getElementById("titleErrorDiv").style.display = "none";
      valid = false;
   }
   else if(titleElement.value.length == 0)
   {
      document.getElementById("titleError").innerHTML = "Error: Game Title is blank.";
      document.getElementById("titleErrorDiv").style.display = "block";
      valid = false;
   }
   else if(titleElement.value.length > 60)
   {
      document.getElementById("titleError").innerHTML = "Error: Game Title is too long (must be less than or equal to 60 characters).";
      document.getElementById("titleErrorDiv").style.display = "block";
      valid = false;
   }
   else
   {
      $.ajax(
      {
         url: "isGameTitleAvailable?title=" + titleElement.value,
         dataType: "json",
         async: false,
         success: function(data)
            {
               if(!data)
               {
                  document.getElementById("titleError").innerHTML = "Error: Game Title is already taken.";
                  document.getElementById("titleErrorDiv").style.display = "block";
                  valid = false;
               }
            }
      });
   }

   if(valid)
   {
      document.getElementById("titleError").innerHTML = "";
      document.getElementById("titleErrorDiv").style.display = "none";
   }

   return valid;
}

function disableTitlePrestine()
{
   titlePrestine = false;
}

function validateStartingMoney()
{
   var valid = true;
   var startingMoneyElement = document.getElementById("startingMoney");

   if(startingMoneyElement.value.length != 0)
   {
      startingMoneyPrestine = false;
   }

   if(startingMoneyPrestine)
   {
      document.getElementById("startingMoneyError").innerHTML = "";
      document.getElementById("startingMoneyErrorDiv").style.display = "none";
      valid = false;
   }
   else if(!isInt(startingMoney.value))
   {
      document.getElementById("startingMoneyError").innerHTML = "Error: Starting Money is not an integer.";
      document.getElementById("startingMoneyErrorDiv").style.display = "block";
      valid = false;
   }
   else if(parseInt(startingMoney.value) <= 0)
   {
      document.getElementById("startingMoneyError").innerHTML = "Error: Starting Money is less than or equal to 0.";
      document.getElementById("startingMoneyErrorDiv").style.display = "block";
      valid = false;
   }

   if(valid)
   {
      document.getElementById("startingMoneyError").innerHTML = "";
      document.getElementById("startingMoneyErrorDiv").style.display = "none";
   }

   return valid;
}

function isInt(value)
{
   return !isNaN(value) && (function(x) { return (x | 0) === x; })(parseFloat(value));
}

function disableStartingMoneyPrestine()
{
   startingMoneyPrestine = false;
}

function validateForm()
{
   // Do this to make sure every function is called
   // (doing it in one line in the if statement short circuits)
   var valid = validateTitle();
   valid &= validateStartingMoney();

   if(!valid)
   {
      return false;
   }
}
