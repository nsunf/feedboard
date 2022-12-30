<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/FeedBoard/stylesheets/aside.css" />
</head>
<body>
	<c:choose>
		<c:when test="${user != null}">
			<%-- Profile --%>
			<div class="aside__profile">
				<div class="aside__profile-wrap">
 					<div class="profile__info">
						<div class="profile__id">${user.id}</div>
						<div class="profile__posts-cnt">${user.numOfPost} posts</div>
					</div>
					<div class="profile__log-out">
						<a href="auth/logout">logout</a>
					</div>
				</div>
				<a href="/FeedBoard/newpost"><button class="new-post-btn">New Post</button></a>
			</div>
		</c:when>
		<c:otherwise>
			<%-- Login --%>
			<div class="aside__login">
				<div class="login__text">LOGIN</div>
				<form class="aside__form" name="loginFrm" action="auth/login"
					method="post">
					<div class="form__input-box">
						<label for="login_user_id">ID</label>
						<input type="text" id="login_user_id" name="user_id" maxlength="13" required />
					</div>
					<div class="form__input-box">
						<label for="login_user_pw">PW</label>
						<input type="password" id="login_user_pw" name="user_pw" maxlength="13" required />
					</div>
					<button type="submit" class="login-btn">LOGIN</button>
				</form>
			</div>
			<%-- Sign Up --%>
			<div class="aside__signup">
				<div class="signup__text">SIGNUP</div>
				<form class="aside__form" name="signupFrm" action="auth/signup"
					method="post">
					<div class="form__input-box">
						<label for="signup_user_id">ID</label>
						<input type="text" id="signup_user_id" name="user_id" maxlength="13" required />
					</div>
					<div class="form__input-box">
						<label for="signup_user_pw">PW</label>
						<input type="password" id="signup_user_pw" name="user_pw" maxlength="13" required />
					</div>
					<div class="form__input-box">
						<label for="signup_user_conf">CONF</label>
						<input type="password" id="signup_user_conf" name="user_conf" maxlength="13" required />
					</div>
					<div class="form__input-box">
						<label for="signup_user_name">NAME</label>
						<input type="text" id="signup_user_name" name="user_name" />
					</div>
					<div class="form__input-box">
						<label for="signup_user_ssn">SSN</label>
						<input type="text" id="signup_user_ssn" name="user_ssn" />
					</div>
					<div class="form__input-box">
						<label for="signup_user_phone">PHONE</label>
						<input type="tel" id="signup_user_phone" name="user_phone" />
					</div>
					<button type="submit" class="sign-btn">SIGN UP</button>
				</form>
			</div>
		</c:otherwise>
	</c:choose>
	<script src="/FeedBoard/scripts/aside.js"></script>
</body>
</html>