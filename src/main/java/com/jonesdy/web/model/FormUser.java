package com.jonesdy.web.model;

public class FormUser
{
   private String username;
   private String email;
   private String emailConfirm;
   private String password;
   private String passwordConfirm;

   public String getUsername()
   {
      return username;
   }
   
   public void setUsername(String user)
   {
      username = user;
   }
   
   public String getEmail()
   {
      return email;
   }
   
   public void setEmail(String e)
   {
      email = e;
   }
   
   public String getEmailConfirm()
   {
      return emailConfirm;
   }
   
   public void setEmailConfirm(String ec)
   {
      emailConfirm = ec;
   }
   
   public String getPassword()
   {
      return password;
   }
   
   public void setPassword(String pass)
   {
      password = pass;
   }
   
   public String getPasswordConfirm()
   {
      return passwordConfirm;
   }
   
   public void setPasswordConfirm(String passConf)
   {
      passwordConfirm = passConf;
   }
}
