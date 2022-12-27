<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css" integrity="sha512-MV7K8+y+gLIBoVD59lQIYicR65iaqukzvf/nwasF0nqhPay5w/9lJmVM2hMDcnK1OnMGCdVK+iQrJ7lzPJQd1w==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<link rel="stylesheet" href="/FeedBoard/stylesheets/global.css" />
<title>FeedBoard</title>
</head>
<body>
    <%@ include file="../components/header.jsp" %>
    <section>
    	<main class="main">
			<%@ include file="../components/post.jsp" %>
    	</main>
    	<aside class="aside">
    		<c:choose>
    			<c:when test="${user != null}">
    		<!-- Profile -->
				<div class="aside__profile">
					<div class="profile__img-wrap">
						<img class="profile__img" src="https://img.freepik.com/free-photo/portrait-young-beautiful-woman-gesticulating_273609-40467.jpg?w=1380&t=st=1671887930~exp=1671888530~hmac=ddcce059c475a821d7063bfe96cff64c87c20a8696f307333e4d92f905dca9a0" alt="profile-img"/>
						<div class="profile__edit"><i class="add-icon fa-solid fa-plus"></i></div>
					</div>
					<div class="profile__info">
						<div class="profile__id">${user.id}</div>
						<div class="profile__posts-cnt">${user.numOfPost} posts</div>
					</div>
					<div class="profile__log-out">
						<a href="logout" >logout</a>
					</div>
				</div>
    			</c:when>
    			<c:otherwise>
    		<!-- Login -->
    			<div class="aside__login">
    				<div class="login__text">LOGIN</div>
    				<form class="aside__form" name="loginFrm" action="login" method="post">
    					<div class="form__input-box">
    						<label for="login_user_id">ID</label>
    						<input type="text" id="login_user_id" name="user_id" maxlength="13" required/>
    					</div>
    					<div class="form__input-box">
    						<label for="login_user_pw">PW</label>
    						<input type="password" id="login_user_pw" name="user_pw" maxlength="13" required/>
    					</div>
    				</form>
    				<button class="login-btn">LOGIN</button>
    			</div>
    		<!-- Sign Up -->
				<div class="aside__signup">
					<div class="signup__text">SIGNUP</div>
    				<form class="aside__form" name="signupFrm" action="signup" method="post">
    					<div class="form__input-box">
    						<label for="signup_user_id">ID</label>
    						<input type="text" id="signup_user_id" name="user_id" maxlength="13" required/>
    					</div>
    					<div class="form__input-box">
    						<label for="signup_user_pw">PW</label>
    						<input type="password" id="signup_user_pw" name="user_pw" maxlength="13" required/>
    					</div>
    					<div class="form__input-box">
    						<label for="signup_user_conf">CONF</label>
    						<input type="password" id="signup_user_conf" name="user_conf" maxlength="13" required/>
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
    				</form>
    				<button class="sign-btn">SIGN UP</button>
				</div>
    			</c:otherwise>
    		</c:choose>
    	</aside>
	</section>
	<script src="/FeedBoard/scripts/index.js"></script>
</body>
</html>