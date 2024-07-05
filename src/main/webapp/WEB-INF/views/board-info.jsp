<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>상세페이지</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .container {
            margin-top: 50px;
        }
        .card {
            border: none;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .card-header {
            background-color: #f8f9fa;
            border-bottom: none;
            font-size: 1.2rem;
            font-weight: bold;
        }
        .card-body {
            padding: 20px;
        }
        .btn-custom {
            width: 80px;
            background-color: #818181;
            color: #fff;
            margin-right: 10px;
            border: none;
        }
        .btn-custom:hover {
            background-color: #616161;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="card">
        <div class="card-header">
            상세페이지
        </div>
        <div class="card-body">
            <table class="table">
                <colgroup>
                    <col width="20%">
                    <col width="80%">
                </colgroup>
                <tbody>
                    <tr>
                        <td>게시글 번호</td>
                        <td>${board.bId}</td>
                    </tr>
                    <tr>
                        <td>작성자</td>
                        <td>${board.bWriter}</td>
                    </tr>
                    <tr>
                        <td>제목</td>
                        <td>${board.bTitle}</td>
                    </tr>
                    <tr>
                        <td>작성 날짜</td>
                        <td>${board.bDate}</td>
                    </tr>
                    <tr>
                        <td>내용</td>
                        <td height="300">${board.bContent}</td>
                    </tr>
                </tbody>
            </table>
            <a href="/board/reply?bId=${board.bId}&bGroup=${board.bGroup}&bOrder=${board.bOrder}&bDepth=${board.bDepth}" class="btn btn-custom">답글</a>
			<c:choose>
			    <c:when test="${board.bWriter == loggedInUser || userAuth == 'ROLE_ADMIN'}">
			        <a href="/board/edit?bId=${board.bId}" class="btn btn-custom">수정</a>
			        <a href="/board/delete?bId=${board.bId}" class="btn btn-custom">삭제</a>
			    </c:when>
			</c:choose>
            <a href="/board/search" class="btn btn-custom">목록</a>
            
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
