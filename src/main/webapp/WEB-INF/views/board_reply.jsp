<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>글작성</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

	<div class="container mt-3">
		<h2>글작성</h2>
		<form action="/board/replypro" name="board" method="post">
			<div class="mb-3 mt-3">
				<input type="hidden" name="bGroup" value="${board.bGroup}">
				<input type="hidden" name="bOrder" value="${board.bOrder}">
				<input type="hidden" name="bDepth" value="${board.bDepth}">
				<input type="text" class="form-control" style="width: 50%"
					placeholder="제목" name="bTitle">
				<textarea class="form-control" style="width: 50%" rows="10"
					id="comment" name="bContent" placeholder="내용"></textarea>
			</div>
			<button type="submit" class="btn btn-primary">Submit</button>
		</form>
	</div>

</body>
</html>