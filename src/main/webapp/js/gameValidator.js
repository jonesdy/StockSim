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
   return true;
}

function disableTitlePrestine()
{
   titlePrestine = false;
}

function validateStartingMoney()
{
   return true;
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
