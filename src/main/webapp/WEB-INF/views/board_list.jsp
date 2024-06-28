<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <title>Board List</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .active {
            font-weight: bold;
            color: red;
        }
        /* 테이블 크기 조정 */
        .table-custom {
            width: 70%;
            margin: auto;
        }
        /* 제목과 작성자, 날짜 열 너비 조정 */
        .table-custom th:nth-child(2),
        .table-custom td:nth-child(2) {
            width: 65%;
        }
        .table-custom th:nth-child(3),
        .table-custom td:nth-child(3) {
            width: 10%;
        }
        .table-custom th:nth-child(4),
        .table-custom td:nth-child(4) {
            width: 25%;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">Board</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <button type="button" class="btn btn-outline-primary me-2" onclick="window.location.href='/'">홈</button>
                    </li>
                    <li class="nav-item">
                        <button type="button" class="btn btn-outline-primary me-2" onclick="goToList()">목록</button>
                    </li>
                    <li class="nav-item">
                        <button type="button" class="btn btn-outline-primary" onclick="goToWrite()">글쓰기</button>
                    </li>
                </ul>
                <form class="d-flex" action="/logout" method="get">
                    <c:choose>
                        <c:when test="${not empty loggedInUser}">
                            <span class="navbar-text me-2">로그인 중: ${loggedInUser}</span>
                            <button type="submit" class="btn btn-outline-danger">로그아웃</button>
                        </c:when>
                        <c:otherwise>
                            <span class="navbar-text">로그인하지 않았습니다.</span>
                        </c:otherwise>
                    </c:choose>
                </form>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <h1 class="text-center">Board List</h1>
        <div class="table-responsive table-custom">
            <table class="table table-hover">
                <thead class="table-light">
                    <tr>
                        <th style="width:5%;">ID</th>
                        <th style="width:65%;">Title</th>
                        <th style="width:10%;">Writer</th>
                        <th style="width:20%;">Date</th>
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
							<td style="font-size: 0.8em; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">
							    <a href="/userInfo?username=${board.bWriter}">
							        ${board.bWriter}
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

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function goToWrite() {
            window.location.href = "/board/write";
        }
        function goToList() {
            window.location.href = "/board/list";
        }
    </script>
</body>
</html>
