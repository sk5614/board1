<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Board List</title>
 <style>   
.active {
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

    <button type="button" class="btn btn-outline-primary">홈</button>
    <button type="button" class="btn btn-outline-primary" onclick="goToList()">목록</button>
    <button type="button" class="btn btn-outline-primary" onclick="goToWrite()"> 글쓰기 </button>

    <script>
        function goToWrite() {
            window.location.href = "/board/write";
        }
        function goToList() {
            window.location.href = "/board/list";
        }
    </script>
<h1>Board List</h1>

<div class="container mt-3 ">
	    <div class="d-flex justify-content-center">
		    <table class="table table-hover " style="width:50%" >
		        <thead >
		            <tr>
		                <th style="width:5%;">ID</th>
		                <th style="width:70%;">Title</th>
		                <th style="width:25%;">Date</th>
		            </tr>
		        </thead>
		        <tbody>
		            <c:forEach var="board" items="${boards}">
		                <tr>
		                    <td>${board.bId}</td>
		                    <td>
		                        <a href="/board/info?bId=${board.bId}">
		                            <c:forEach begin="1" end="${board.bDepth}" step="1">
		                                <span class="badge bg-secondary">RE</span>
		                            </c:forEach>
		                            ${board.bTitle}
		                        </a>
		                    </td>
		                    <td style="font-size: 0.8em;">${board.bDate}</td>
		                </tr>
		            </c:forEach>
		        </tbody>
		    </table>
		</div>
		
    <div class="d-flex justify-content-center mt-3">
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <c:if test="${startPage > 1}">
                    <li class="page-item">
                        <a class="page-link" href="/board/list?page=${startPage - 1}&size=${size}" aria-label="Previous">
                            <span aria-hidden="true">&laquo; Previous</span>
                        </a>
                    </li>
                </c:if>

                <c:forEach var="pageNum" items="${pageNumbers}">
                    <c:choose>
                        <c:when test="${pageNum == nowPage}">
                            <li class="page-item active" aria-current="page">
                                <span class="page-link">${pageNum}</span>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item">
                                <a class="page-link" href="/board/list?page=${pageNum}&size=${size}">${pageNum}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${endPage < totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="/board/list?page=${endPage + 1}&size=${size}" aria-label="Next">
                            <span aria-hidden="true">Next &raquo;</span>
                        </a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</div>

</body>
</html>
