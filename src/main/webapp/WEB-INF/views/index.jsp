<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <title>게시판 메인페이지 </title>
   <link rel="stylesheet" type="text/css" href="/css/signupform.css">
</head>
<body>
	<h2>회원가입</h2>
	<div class="container" id="container">
		<div class="form-container sign-up-container">
			<form id="signupForm" action="/signUpPro" method="post">
				<h1>Create Account</h1>
				<input type="text" name="username" id="username" placeholder="Id" />
				<input type="password" id="password" name="password" placeholder="Password" />
				<input type="password" id="confirmPassword" placeholder="Confirm Password" />
				<button>회원가입</button>
				<p id="errormatch" style="color:red;display:none;">패스워드가 일치하지 않습니다.</p>
				<p id="errorusername" style="color:red;display:none;">이미 존재하는 ID 입니다.</p>
			</form>
		</div>
		
		<div class="form-container sign-in-container">
			<form id="signinForm" action="#">
				<h1>Sign in</h1>
				<input type="text" placeholder="Id" />
				<input type="password" placeholder="Password" />
				<button>로그인</button>
				<p id="error-id" style="color:red;display:none;">아이디 불일치</p>
				<p id="error-password" style="color:red;display:none;">비밀번호 불일치</p>
			</form>
		</div>
		<div class="overlay-container">
			<div class="overlay">
				<div class="overlay-panel overlay-left">
					<h1>Welcome Back!</h1>
					<button class="ghost" id="signIn">로그인</button>
				</div>
				<div class="overlay-panel overlay-right">
					<h1>Hello, Friend!</h1>
					<button class="ghost" id="signUp">회원가입</button>
				</div>
			</div>
		</div>
	</div>
	<script src="/js/signup.js?after"></script>
</body>
</html>