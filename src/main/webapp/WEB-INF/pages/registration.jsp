<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page isELIgnored="false"%>
<html>
<head>
<title>User Registration Page</title>
</head>
<body>
   <div align="center">
      <form:form method="POST" commandName="user">
         <table border="0">
	         <tr>
	            <td>User Name:</td>
	            <td><form:input path="username" /></td>
	         </tr>
	         <tr>
	           <td>Email:</td>
	           <td><form:input path="email" /></td>
	         </tr>
	         <tr>
	           <td>Confirm email:</td>
	           <td><form:input path="emailConfirm" /></td>
	         </tr>
	         <tr>
	            <td>Password:</td>
	            <td><form:password path="password" /></td>
	         </tr>
	         <tr>
	           <td>Confirm password:</td>
	           <td><form:password path="passwordConfirm" /></td>
	         </tr>
	         <tr>
	            <td><input type="submit" name="submit" value="Submit"></td>
	         </tr>
         </table>
         <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
      </form:form>
   </div>
</body>
</html>