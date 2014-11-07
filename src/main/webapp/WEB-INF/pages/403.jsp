<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
   <h1>HTTP Status 403 - Access is denied</h1>
   <c:choose>
      <c:when test="${empty username}">
         <h2>You do not have permission to access this page!</h2>
      </c:when>
      <c:otherwise>
         <h2>User Name: ${username} <br/>
            You do not have permission to access this page!
         </h2>
      </c:otherwise>
   </c:choose>
</body>
</html>