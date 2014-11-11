<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<head>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<title>Login Page</title>
</head>
<body onload='document.loginForm.username.focus();'>
   <div class='container'>
	   <h1>Spring Security Login Form (Database Authentication)</h1>
	   <div id='login-box'>
	      <h3>Login with User Name and Password</h3>
	      <c:if test="${not empty error}">
	         <div class='alert alert-danger'>${error}</div>
	      </c:if>
	      <c:if test="${not empty msg}">
	         <div class='alert alert-info'>${msg}</div>
	      </c:if>
	      
	      <form name='loginForm' action="<c:url value='/login' />" method='POST'>
            <div class='form-group'>
               <label>User Name:</label>
               <input type='text' name='username' class='form-control' placeholder='Enter user name'>
            </div>
            <div class='form-group'>
               <label>Password:</label>
               <input type='password' name='password' class='form-control' placeholder='Enter password'/>
            </div>
            <button type='submit' class='btn btn-default'>Log In</button>
	         <input type='hidden' name="${_csrf.parameterName}" value="${_csrf.token}" />
	      </form>
	   </div>
   </div>
</body>
</html>