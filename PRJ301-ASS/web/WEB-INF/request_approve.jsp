<%-- 
    Document   : request_approve
    Created on : 15 thg 6, 2025, 22:25:45
    Author     : Ha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head><title>Duyệt đơn nghỉ phép</title></head>
<body>
<h2>Duyệt đơn nghỉ phép</h2>

<c:if test="${not empty error}">
    <div style="color: red">${error}</div>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/request/approve">
    <input type="hidden" name="id" value="${request.requestId}"/>
    <p>Tạo bởi: ${request.createdBy}</p>
    <p>Từ ngày: ${request.fromDate}</p>
    <p>Đến ngày: ${request.toDate}</p>
    <p>Lý do: ${request.reason}</p>
    <p>Trạng thái hiện tại: ${request.status}</p>

    <p>
        <label>Lý do duyệt / từ chối (comment):</label><br/>
        <textarea name="comment" rows="4" cols="50">${request.comment}</textarea>
    </p>

    <button type="submit" name="action" value="approve">Approve</button>
    <button type="submit" name="action" value="reject">Reject</button>
</form>

<a href="${pageContext.request.contextPath}/request/list">Quay lại danh sách</a>
</body>
</html>
