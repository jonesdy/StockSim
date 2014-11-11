<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<title>Welcome</title>
</head>
<body>
   <h1>Title: ${title}</h1>
   <h1>Message: ${message}</h1>
   
   <sec:authorize access="hasRole('ROLE_USER')">
      <!--  For login user -->
      <c:url value="/logout" var="logoutUrl" />
      <form action="${logoutUrl}" method="post" id="logoutForm">
         <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
      </form>
      <script>
         function formSubmit()
         {
            document.getElementById("logoutForm").submit();
         }
      </script>
      
      <c:if test="${pageContext.request.userPrincipal.name != null}">
         <h2>
            User: ${pageContext.request.userPrincipal.name} |
               <a href="javascript:formSubmit()"> Logout</a>
         </h2>
      </c:if>
   </sec:authorize>
</body>
</html>