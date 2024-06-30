<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
                        <td>Username</td>
                        <td>${user.username}</td>
                    </tr>
                    <tr>
                        <td>가입일</td>
                        <td>${user.uDate}</td>
                    </tr>
                    	<a href="/board/list?bWriter=${user.username}">작성글 목록</a>
                    <tr>
                    <tr>
                    	<td>작성글수</td>
                    	<td>${boardcount} </td>
                    </tr>
                </tbody>
            </table>
            <a href="/board/list" class="btn btn-custom">목록</a>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
