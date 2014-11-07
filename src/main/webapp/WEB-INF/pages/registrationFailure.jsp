<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page isELIgnored="false"%>
<html>
<head>
<title>Registration Failure</title>
</head>
<body>
   <div align="center">
      <table border="0">
         <tr>
            <td colspan="2" align="center"><h2>Registration Failed.</h2></td>
         </tr>
         <tr>
            <td>Failed for the following reason:</td>
            <td>${failureReason}</td>
         </tr>
      </table>
   </div>
</body>
</html>