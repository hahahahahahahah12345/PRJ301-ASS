<%-- 
    Document   : login.jsp
    Created on : 15 thg 6, 2025, 21:36:10
    Author     : Ha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>Đăng nhập hệ thống</title>
</head>
<body>
<h2>Đăng nhập</h2>

<c:if test="${not empty error}">
    <div style="color:red">${error}</div>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/login">
    <label>Tên đăng nhập:</label><br/>
    <input type="text" name="username" required/><br/><br/>

    <label>Mật khẩu:</label><br/>
    <input type="password" name="password" required/><br/><br/>

    <button type="submit">Đăng nhập</button>
</form>
</body>
</html>
