<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>글작성</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

<div class="container mt-3">
  <h2>글작성</h2>
  <form action="/board/editpro?b_id=${board.b_id}" name="board" method="post">
    <div class="mb-3 mt-3">
    	<input type="hidden" name="id" value="${board.b_id}"/>
      	<input type="text" class="form-control" style="width:50%"  value="${board.b_title}" name="b_title" >
      <textarea class="form-control" style="width:50%" rows="10"  name="b_content"  placeholder="${board.b_content}" ></textarea>
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
  </form>
</div>

</body>
</html>