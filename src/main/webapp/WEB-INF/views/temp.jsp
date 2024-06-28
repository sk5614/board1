<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
<head>
  <title>답글글작성</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  <style>
    body {
      background-color: #f8f9fa;
    }
    .form-container {
      background-color: white;
      padding: 30px;
      border-radius: 10px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }
    .form-title {
      text-align: center;
      margin-bottom: 20px;
    }
  </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">게시판</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
      <ul class="navbar-nav">
        <c:choose>
          <c:when test="${not empty loggedInUser}">
            <li class="nav-item">
              <span class="navbar-text me-2">로그인 중: ${loggedInUser}</span>
            </li>
            <li class="nav-item">
              <form action="/logout" method="get" class="d-inline">
                <button type="submit" class="btn btn-outline-danger">로그아웃</button>
              </form>
            </li>
          </c:when>
          <c:otherwise>
            <li class="nav-item">
              <span class="navbar-text">로그인하지 않았습니다.</span>
            </li>
          </c:otherwise>
        </c:choose>
      </ul>
    </div>
  </div>
</nav>

<div class="container mt-5">
  <div class="form-container">
    <h2 class="form-title">답글작성</h2>
    <form action="/board/writepro" name="board" method="post">
    	<input type="hidden" name="bGroup" value="${board.bGroup}">
        <input type="hidden" name="bOrder" value="${board.bOrder}">
        <input type="hidden" name="bDepth" value="${board.bDepth}">
        <input type="hidden" name="bWriter" value="${loggedInUser != null ? loggedInUser : '익명'}">
      <div class="mb-3">
        <label for="bTitle" class="form-label">제목</label>
        <input type="text" class="form-control" id="bTitle" name="bTitle" placeholder="제목">
      </div>
      <div class="mb-3">
        <label for="bContent" class="form-label">내용</label>
        <textarea class="form-control" id="bContent" name="bContent" rows="10" placeholder="내용"></textarea>
      </div>
      <div class="mb-3">
        <label for="bWriter" class="form-label">작성자</label>
        <input type="text" class="form-control" id="bWriter" name="bWriter" value="${loggedInUser != null ? loggedInUser : '익명'}" readonly>
      </div>
      <button type="submit" class="btn btn-primary">Submit</button>
    </form>
  </div>
</div>
</body>
</html>
