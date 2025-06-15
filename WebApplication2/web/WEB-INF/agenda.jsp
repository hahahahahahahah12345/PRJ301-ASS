<%-- 
    Document   : agenda
    Created on : 15 thg 6, 2025, 22:27:22
    Author     : Ha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
    <html>
<head>
    <title>Agenda phòng ban</title>
    <style>
        table {border-collapse: collapse;}
        td, th {border: 1px solid #999; padding: 5px 10px; text-align: center;}
        .working {background-color: #a3d5a3;}  /* xanh lá = đi làm */
        .leave {background-color: #f7a7a7;}    /* đỏ = nghỉ phép */
    </style>
</head>
<body>
<h2>Agenda phòng ban từ ${startDate} đến ${endDate}</h2>

<table>
    <thead>
    <tr>
        <th>Nhân sự</th>
        <c:forEach var="date" items="${dates}">
            <th>${date}</th>
        </c:forEach>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="person" items="${staffs}">
        <tr>
            <td>${person.fullName}</td>
            <c:forEach var="date" items="${dates}">
                <c:set var="status" value="${agendaMap[person.uid][date]}" />
                <td class="${status == 'leave' ? 'leave' : 'working'}">
                    <c:choose>
                        <c:when test="${status == 'leave'}">Nghỉ</c:when>
                        <c:otherwise>Đi làm</c:otherwise>
                    </c:choose>
                </td>
            </c:forEach>
        </tr>
    </c:forEach>
    </tbody>
</table>

<a href="${pageContext.request.contextPath}/dashboard">Quay lại Dashboard</a>

</body>
</html>