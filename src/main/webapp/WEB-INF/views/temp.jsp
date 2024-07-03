<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
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
        /* 검색 폼 스타일 */
        .search-form {
            margin-top: 20px;
            text-align: center;
        }
        .search-form .form-select,
        .search-form .form-control,
        .search-form .btn {
            display: inline-block;
            width: auto;
            margin: 5px 0;
        }
        /* 날씨 정보 스타일 */
        .weather-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 20px;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .weather-details {
            flex: 1;
            margin-left: 20px;
        }
        .weather-details p {
            margin: 5px 0;
        }
        .weather-icon {
            width: 100px;
            height: 100px;
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
                    <li class="nav-item">
                        <button type="button" class="btn btn-outline-primary" onclick="goToUser()">회원목록</button>
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
                            <a class="page-link" href="/board/search?page=${startPage - 1}&size=${size}" aria-label="Previous">
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
                                    <a class="page-link" href="/board/search?page=${pageNum}&size=${size}">${pageNum}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${endPage < totalPages}">
                        <li class="page-item">
                            <a class="page-link" href="/board/search?page=${endPage + 1}&size=${size}" aria-label="Next">
                                <span aria-hidden="true">Next &raquo;</span>
                            </a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>
        
        <div class="container mt-4 search-form">
            <form action="/board/search" method="get" class="d-flex justify-content-center align-items-center">
                <select name="searchType" class="form-select me-2">
                    <option value="title">Title</option>
                    <option value="content">Content</option>
                    <option value="writer">Writer</option>
                </select>
                <input type="text" name="keyword" placeholder="Enter keyword" class="form-control me-2" />
                <button type="submit" class="btn btn-primary">Search</button>
            </form>
        </div>
        
    </div>

    <!-- Weather Information Section -->
  <div class="container mt-4">
    <h2 class="text-center">Weather Information</h2>
    <c:choose>
        <c:when test="${not empty weatherData}">
            <div class="row">
                <!-- Dummy column for left spacing -->
                <div class="col-lg-6 d-none d-lg-block"></div>
                <!-- Weather Details -->
                <div class="col-lg-6">
                    <div class="weather-container">
                        <!-- Weather Icon -->
                        <div class="weather-icon">
                            <img src="http://openweathermap.org/img/wn/${weatherData.weather[0].icon}.png" alt="Weather Icon">
                        </div>
                        <!-- Weather Details -->
	                        <div class="weather-details">
	                  		 <p><strong>위치:</strong> ${weatherData.name}</p>
	                        <p><strong>온도:</strong> ${weatherData.tempCelsius} °C</p>
	                        <p><strong>체감 온도:</strong> ${weatherData.feelsLikeCelsius} °C</p>
	                        <p><strong>최저 온도:</strong> ${weatherData.tempMinCelsius} °C</p>
	                        <p><strong>최고 온도:</strong> ${weatherData.tempMaxCelsius} °C</p>
	                        <p><strong>습도:</strong> ${weatherData.main.humidity} %</p>
	                        <p><strong>날씨:</strong> ${weatherData.weather[0].description}</p>
                        </div>
                    </div>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <p>No weather data available</p>
        </c:otherwise>
    </c:choose>
</div>


    <!-- End of Weather Information Section -->

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function goToWrite() {
            window.location.href = "/board/write";
        }
        function goToList() {
            window.location.href = "/board/list";
        }
        function goToUser() {
            window.location.href = "/user/list";
        }
    </script>
</body>
</html>
