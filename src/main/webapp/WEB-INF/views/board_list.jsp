<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
     <c:forEach var="board" items="${boards}">
        <tr>
            <td>${board.b_id}</td>
            <td>${board.b_title}</td>
            <td>${board.b_date}</td>
            <td>${board.b_content}</td>
        </tr>
    </c:forEach>  
</table>
    

</body>
</html>