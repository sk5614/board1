<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Board List</title>
 <style>   .active {
    font-weight: bold;
    color: red;
}

</style>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

<div> 
<button type="button" class="btn btn-outline-primary">홈</button>
<button type="button" class="btn btn-outline-primary" onclick="goToList()">목록</button>
<button type="button" class="btn btn-outline-primary" onclick="goToWrite()"> 글쓰기 </button>

    <script>
        function goToWrite() {
            window.location.href = "/board/write";
        }
    </script>
	    <script>
        function goToList() {
            window.location.href = "/board/list";
        }
    </script>
</div>
<h1>Board List</h1>

<div class="container mt-3">
  <table class="table table-hover" style="width:50%">
    <thead>
    	<tr>
	        <td style="width:5%;">ID</td>
	        <td style="width:70%;">Title</td>
	        <td style="width:25%;">Date</td>
        </tr>
    </thead>
     <c:forEach var="board" items="${boards}">
        <tbody>
        	<tr>
	            <td>${board.b_id}</td>
	            <td><a href="/board/info?b_id=${board.b_id}">
	            <c:forEach begin="1" end="${board.b_depth}" step="1">
				<span class="badge bg-secondary">RE</span>
				</c:forEach>
				${board.b_title}</a></td>
	            <td style="font-size: 0.8em;">${board.b_date}</td>
            </tr>
        </tbody>
    </c:forEach>  
</table>
 </div>   
   <div>
        <c:if test="${startPage > 1}">
            <a href="/board/list?page=${startPage - 1}&size=${size}">&laquo; Previous</a>
        </c:if>
        <c:forEach var="pageNum" items="${pageNumbers}">
            <a href="/board/list?page=${pageNum}&size=${size}" >
            
				<c:choose>
				    <c:when test="${pageNum == nowPage}">
				        <strong class="active">${pageNum}</strong>
				    </c:when>
				    <c:otherwise>
				        <strong>${pageNum}</strong>
				    </c:otherwise>
				</c:choose>
            </a>
	    
        </c:forEach>
        <c:if test="${endPage < totalPages}">
            <a href="/board/list?page=${endPage + 1}&size=${size}">Next &raquo;</a>
        </c:if>
    </div>
</body>
</html>