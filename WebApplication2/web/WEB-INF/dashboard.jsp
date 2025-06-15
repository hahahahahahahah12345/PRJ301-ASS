<%-- 
    Document   : dashboard
    Created on : 15 thg 6, 2025, 22:27:01
    Author     : Ha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>Dashboard</title>
</head>
<body>
<h2>Chào mừng, ${user.fullName}!</h2>

<nav>
    <ul>
        <li><a href="${pageContext.request.contextPath}/request/create">Tạo đơn xin nghỉ</a></li>
        <li><a href="${pageContext.request.contextPath}/request/list">Xem danh sách đơn</a></li>
        <li><a href="${pageContext.request.contextPath}/agenda">Xem agenda phòng ban</a></li>
        <li><a href="${pageContext.request.contextPath}/logout">Đăng xuất</a></li>
    </ul>
</nav>

</body>
</html>
