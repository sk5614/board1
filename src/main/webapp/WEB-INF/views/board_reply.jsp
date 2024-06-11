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
				<input type="hidden" name="b_group" value="${board.b_group}">
				<input type="hidden" name="b_order" value="${board.b_order}">
				<input type="hidden" name="b_depth" value="${board.b_depth}">
				<input type="text" class="form-control" style="width: 50%"
					placeholder="제목" name="b_title">
				<textarea class="form-control" style="width: 50%" rows="10"
					id="comment" name="b_content" placeholder="내용"></textarea>
			</div>
			<button type="submit" class="btn btn-primary">Submit</button>
		</form>
	</div>

</body>
</html>