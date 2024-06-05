<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Board List</title>
</head>
<body>
<h1>Board List</h1>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Title</th>
        <th>Created At</th>
        <th>Action</th>
    </tr>
<!--     <c:forEach var="board" items="${boards}">
        <tr>
            <td>${board.id}</td>
            <td>${board.title}</td>
            <td>${board.date}</td>
        </tr>
    </c:forEach>  -->
</table>
    

</body>
</html>