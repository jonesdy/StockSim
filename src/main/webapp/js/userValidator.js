// This code is kind of gross, but it works

var usernamePrestine;
var emailPrestine;
var emailConfirmPrestine;
var passwordPrestine;
var passwordConfirmPrestine;

function initializeForm()
{
   // Initialize prestine variables, make sure error messages aren't displayed
   usernamePrestine = true;
   emailPrestine = true;
   emailConfirmPrestine = true;
   passwordPrestine = true;
   passwordConfirmPrestine = true;
   
   document.getElementById("usernameError").innerHTML = "";
   document.getElementById("usernameErrorDiv").style.display = "none";
   document.getElementById("emailError").innerHTML = "";
   document.getElementById("emailErrorDiv").style.display = "none";
   document.getElementById("emailConfirmError").innerHTML = "";
   document.getElementById("emailConfirmErrorDiv").style.display = "none";
   document.getElementById("passwordError").innerHTML = "";
   document.getElementById("passwordErrorDiv").style.display = "none";
   document.getElementById("passwordConfirmError").innerHTML = "";
   document.getElementById("passwordConfirmErrorDiv").style.display = "none";
}

function validateUsername()
{
   var valid = true;
   var usernameElement = document.getElementById("username");
   
   if(usernameElement.value.length != 0)
   {
      usernamePrestine = false;
   }
   
   if(usernamePrestine)
   {
      document.getElementById("usernameError").innerHTML = "";
      document.getElementById("usernameErrorDiv").style.display = "none";
      valid = false;
   }
   else
   {
      usernameElement = document.getElementById("username");
      if(usernameElement.value.length == 0)
      {
         document.getElementById("usernameError").innerHTML = "Error: User Name is blank.";
         document.getElementById("usernameErrorDiv").style.display = "block";
         valid = false;
      }
      else if(usernameElement.value.length > 60)
      {
         document.getElementById("usernameError").innerHTML = "Error: User Name is too long.";
         document.getElementById("usernameErrorDiv").style.display = "block";
         valid = false;
      }
      else
      {
         $.getJSON("isUsernameAvailable?username=" + usernameElement.value, function(data)
            {
               if(!data)
               {
                  document.getElementById("usernameError").innerHTML = "Error: User Name is already taken.";
                  document.getElementById("usernameErrorDiv").style.display = "block";
                  valid = false;
               }
            });
      }
   }
   
   if(valid)
   {
      document.getElementById("usernameError").innerHTML = "";
      document.getElementById("usernameErrorDiv").style.display = "none";
   }
   
   return valid;
}

function disableUsernamePrestine()
{
   usernamePrestine = false;
}

function validateEmail()
{
   var valid = true;
   var emailElement = document.getElementById("email");
   
   if(emailElement.value.length != 0)
   {
      emailPrestine = false;
   }
   
   if(emailPrestine)
   {
      document.getElementById("emailError").innerHTML = "";
      document.getElementById("emailErrorDiv").style.display = "none";
      valid = false;
   }
   else
   {
      if(emailElement.value.length == 0)
      {
         document.getElementById("emailError").innerHTML = "Error: Email is blank.";
         document.getElementById("emailErrorDiv").style.display = "block";
         valid = false;
      }
      else if(emailElement.value.length > 60)
      {
         document.getElementById("emailError").innerHTML = "Error: Email is too long.";
         document.getElementById("emailErrorDiv").style.display = "block";
         valid = false;
      }
      else if(emailElement.value.indexOf("@") == -1)
      {
         document.getElementById("emailError").innerHTML = "Error: Email is not valid.";
         document.getElementById("emailErrorDiv").style.display = "block";
         valid = false;
      }
   }
   
   if(valid)
   {
      document.getElementById("emailError").innerHTML = "";
      document.getElementById("emailErrorDiv").style.display = "none";
   }
   
   return valid;
}

function disableEmailPrestine()
{
   emailPrestine = false;
}

function validateEmailConfirm()
{
   var valid = true;
   var emailConfirmElement = document.getElementById("emailConfirm");
   
   if(emailConfirmElement.value.length != 0)
   {
      emailConfirmPrestine = false;
   }
   
   if(emailConfirmPrestine)
   {
      document.getElementById("emailConfirmError").innerHTML = "";
      document.getElementById("emailConfirmErrorDiv").style.display = "none";
      valid = false;
   }
   else
   {
      var emailElement = document.getElementById("email");
      emailConfirmElement = document.getElementById("emailConfirm");
      if(emailConfirmElement.value.length == 0)
      {
         document.getElementById("emailConfirmError").innerHTML = "Error: Email is blank.";
         document.getElementById("emailConfirmErrorDiv").style.display = "block";
         valid = false;
      }
      else if(emailConfirmElement.value.length > 60)
      {
         document.getElementById("emailConfirmError").innerHTML = "Error: Email is too long.";
         document.getElementById("emailConfirmErrorDiv").style.display = "block";
         valid = false;
      }
      else if(emailElement.value.indexOf("@") == -1)
      {
         document.getElementById("emailConfirmError").innerHTML = "Error: Email is not valid.";
         document.getElementById("emailConfirmErrorDiv").style.display = "block";
         valid = false;
      }
      if(emailElement.value != emailConfirmElement.value)
      {
         document.getElementById("emailConfirmError").innerHTML = "Error: Email is not the same.";
         document.getElementById("emailConfirmErrorDiv").style.display = "block";
         valid = false;
      }
   }
   
   if(valid)
   {
      document.getElementById("emailConfirmError").innerHTML = "";
      document.getElementById("emailConfirmErrorDiv").style.display = "none";
   }
   
   return valid;
}

function disableEmailConfirmPrestine()
{
   emailConfirmPrestine = false;
}

function validatePassword()
{
   var valid = true;
   var passwordElement = document.getElementById("password");
   
   if(passwordElement.value.length != 0)
   {
      passwordPrestine = false;
   }
   
   if(passwordPrestine)
   {
      document.getElementById("passwordError").innerHTML = "";
      document.getElementById("passwordErrorDiv").style.display = "none";
      valid = false;
   }
   else
   {
      passwordElement = document.getElementById("password");
      if(passwordElement.value.length == 0)
      {
         document.getElementById("passwordError").innerHTML = "Error: Password is blank.";
         document.getElementById("passwordErrorDiv").style.display = "block";
         valid = false;
      }
   }
   
   if(valid)
   {
      document.getElementById("passwordError").innerHTML = "";
      document.getElementById("passwordErrorDiv").style.display = "none";
   }
   
   return valid;
}

function disablePasswordPrestine()
{
   passwordPrestine = false;
}

function validatePasswordConfirm()
{
   var valid = true;
   var passwordConfirmElement = document.getElementById("passwordConfirm");
   
   if(passwordConfirmElement.value.length != 0)
   {
      passwordConfirmPrestine = false;
   }
   
   if(passwordConfirmPrestine)
   {
      document.getElementById("passwordConfirmError").innerHTML = "";
      document.getElementById("passwordConfirmErrorDiv").style.display = "none";
      valid = false;
   }
   else
   {
      var passwordElement = document.getElementById("password");
      passwordConfirmElement = document.getElementById("passwordConfirm");
      if(passwordConfirmElement.value.length == 0)
      {
         document.getElementById("passwordConfirmError").innerHTML = "Error: Password is blank.";
         document.getElementById("passwordConfirmErrorDiv").style.display = "block";
         valid = false;
      }
      if(passwordElement.value != passwordConfirmElement.value)
      {
         document.getElementById("passwordConfirmError").innerHTML = "Error: Password is not the same.";
         document.getElementById("passwordConfirmErrorDiv").style.display = "block";
         valid = false;
      }
   }
   
   if(valid)
   {
      document.getElementById("passwordConfirmError").innerHTML = "";
      document.getElementById("passwordConfirmErrorDiv").style.display = "none";
   }
   
   return valid;
}

function disablePasswordConfirmPrestine()
{
   passwordConfirmPrestine = false;
}

function validateForm()
{
   // Do this to make sure every function is called
   // (doing it in one line in the if statement short circuits)
   var valid = validateUsername();
   valid &= validateEmail();
   valid &= validateEmailConfirm();
   valid &= validatePassword();
   valid &= validatePasswordConfirm();
   
   if(!valid)
   {
      return false;
   }
}
