<%-- 
    Document   : request_create.jsp
    Created on : 22 thg 6, 2025, 21:09:58
    Author     : Ha
--%>


--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <title>Tạo đơn xin nghỉ phép</title>
</head>
<body>
<h2>Tạo đơn xin nghỉ phép</h2>

<c:if test="${not empty error}">
    <div style="color: red">${error}</div>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/request/create">
    <p>User: ${user.fullname}, Role: ${user.rname}, Department: IT</p>
    <label>Từ ngày:</label><br/>
    <input type="date" name="fromDate" required/><br/><br/>
    <label>Tới ngày:</label><br/>
    <input type="date" name="toDate" required/><br/><br/>
    <label>Lý do:</label><br/>
    <textarea name="reason" rows="4" cols="50" required></textarea><br/><br/>
    <button type="submit">Gửi</button>
</form>

<a href="${pageContext.request.contextPath}/request/list">Quay lại danh sách</a>
</body>
</html>