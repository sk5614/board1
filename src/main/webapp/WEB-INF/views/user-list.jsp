<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <title>User List</title>
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
            margin-top: 20px;
        }
        /* 제목과 작성자, 날짜 열 너비 조정 */
        .table-custom th:nth-child(1),
        .table-custom td:nth-child(1) {
            width: 15%;
        }
        .table-custom th:nth-child(2),
        .table-custom td:nth-child(2) {
            width: 25%;
        }
        .table-custom th:nth-child(3),
        .table-custom td:nth-child(3) {
            width: 40%;
        }
        .table-custom th:nth-child(4),
        .table-custom td:nth-child(4) {
            width: 20%;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">User</a>
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
        <h1 class="text-center">User List</h1>
        <div class="table-responsive table-custom">
            <table class="table table-hover">
                <thead class="table-light">
                    <tr>
                        <th>ID</th>
                        <th>현재권한</th>
                        <th>권한처리</th>
                        <th>Date</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${users}">
                        <tr>
                            <td>
                                <a href="/userInfo?username=${user.username}">
                                    ${user.username}
                                </a>
                            </td>
                            <td class="auth">${user.uAuth}</td>
                            <td><button class="btn btn-sm btn-primary" onclick="changeAuthority('${user.username}', '${user.uAuth}', this)">
                                    관리자 권한 변경
                                </button>
                            </td>
                            <td style="font-size: 0.8em;">${user.uDate}</td>
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
                            <a class="page-link" href="/user/list?page=${startPage - 1}&size=${size}" aria-label="Previous">
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
                                    <a class="page-link" href="/user/list?page=${pageNum}&size=${size}">${pageNum}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${endPage < totalPages}">
                        <li class="page-item">
                            <a class="page-link" href="/user/list?page=${endPage + 1}&size=${size}" aria-label="Next">
                                <span aria-hidden="true">Next &raquo;</span>
                            </a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script>
        function goToWrite() {
            window.location.href = "/board/write";
        }
        function goToList() {
            window.location.href = "/board/list";
        }
        let pageInfo = {
                page: ${nowPage}, // 현재 페이지 번호
                size: ${size} // 페이지 사이즈
            };

        	function changeAuthority(username, currentAuth, buttonElement) {
        	    let newAuth = currentAuth === 'ROLE_ADMIN' ? 'ROLE_USER' : 'ROLE_ADMIN';

        	    $.ajax({
        	        url: '/user/edit',
        	        type: 'POST',
        	        contentType: 'application/json',
        	        data: JSON.stringify({ 
        	            username: username, 
        	            uAuth: newAuth,
        	            pageInfo: pageInfo // 초기 페이지 정보 전송
        	        }),
        	        success: function() {
        	            // 성공적으로 업데이트되면 현재 페이지 정보를 기반으로 데이터 다시 로드
        	            $.get('/user/list?page=' + pageInfo.page + '&size=' + pageInfo.size, function(data) {
        	                $('.table-custom tbody').html($(data).find('.table-custom tbody').html());
        	            });
        	        },
        	        error: function() {
        	            console.error('권한 변경 중 오류가 발생했습니다.');
        	        }
        	    });
        	}

    </script>
</body>
</html>
