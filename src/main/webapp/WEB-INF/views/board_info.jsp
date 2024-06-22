<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1>상세페이지</h1>
	<table>
	
		<colgroup>
		<col width="20%" >
		<col width="80%">
		</colgroup>
			<tr>
				<td>게시글 번호</td>
				<td>${board.bId}</td>
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
	</table>
	<a href="/board/reply?bId=${board.bId}&bGroup=${board.bGroup}&bOrder=${board.bOrder}&bDepth=${board.bDepth}" style="width: 10%; background-color: #818181; color: #fff;">답글</a>
	<a href="/board/edit?bId=${board.bId}" style="width: 10%; background-color: #818181; color: #fff;">수정</a>
	<a href="/board/delete?bId=${board.bId}" style="width: 10%; background-color: #818181; color: #fff;">삭제</a>
</body>
</html>