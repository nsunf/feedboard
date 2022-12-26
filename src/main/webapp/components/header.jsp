<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<header class="header">
		<div class="header__wrap">
			<div class="header__logo logo">
				<h1 class="logo__text">
					<a class="logo__link" href="/FeedBoard">FeedBoard</a>
				</h1>
			</div>
			<div class="header__search search">
				<form class="search__box" action="search" method="post">
					<input class="search__input" type="search" placeholder="Search User">
					<button class="search__btn" type="submit">
						<i class="search__icon fa-solid fa-magnifying-glass"></i>
					</button>
				</form>
			</div>
		</div>
	</header>
</body>
</html>