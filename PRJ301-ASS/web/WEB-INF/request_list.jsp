<%-- 
    Document   : request_create.jsp
    Created on : 22 thg 6, 2025, 21:07:41
    Author     : Ha
--%>

<%-- 
    Document   : request_list
    Created on : 15 thg 6, 2025
    Author     : Grok
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <title>Danh sách đơn xin nghỉ</title>
</head>
<body>
<h2>Danh sách đơn xin nghỉ</h2>

<table border="1">
    <thead>
        <tr>
            <th>Tiêu đề</th>
            <th>Từ ngày</th>
            <th>Tới ngày</th>
            <th>Lý do</th>
            <th>Trạng thái</th>
            <th>Người xử lý</th>
            <th>Hành động</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="request" items="${requests}">
            <tr>
                <td>${request.reason}</td>
                <td>${request.fromDate}</td>
                <td>${request.toDate}</td>
                <td>${request.reason}</td>
                <td>${request.status}</td>
                <td>${request.processedByName}</td>
                <td>
                    <c:if test="${user.rname == 'Trưởng phòng' && request.status == 'Inprogress'}">
                        <a href="${pageContext.request.contextPath}/request/approve?id=${request.requestId}">Duyệt</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<a href="${pageContext.request.contextPath}/dashboard">Quay lại Dashboard</a>
</body>
</html>
