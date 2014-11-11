<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page isELIgnored="false"%>
<html>
<head>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<title>User Registration Page</title>
</head>
<body>
   <div class="container">
      <h1>Register here</h1>
      <form:form method="POST" commandName="user">
         <div class='form-group'>
	            <label>User Name:</label>
	            <input type='text' name='username' class='form-control' placeholder='Enter user name'>
	      </div>
	      <div class='form-group'>
               <label>Email:</label>
               <input type='text' name='email' class='form-control' placeholder='Enter email'>
         </div>
         <div class='form-group'>
               <label>Confirm email:</label>
               <input type='text' name='emailConfirm' class='form-control' placeholder='Enter email again'>
         </div>
         <div class='form-group'>
               <label>Password:</label>
               <input type='password' name='password' class='form-control' placeholder='Enter password'>
         </div>
         <div class='form-group'>
               <label>Confirm password:</label>
               <input type='password' name='passwordConfirm' class='form-control' placeholder='Enter password again'>
         </div>
         <button type='submit' class='btn btn-default'>Register</button>
         <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
      </form:form>
   </div>
</body>
</html>