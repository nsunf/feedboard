<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/FeedBoard/stylesheets/post.css" />
</head>
<body>
	<c:if test="${empty post}">
	<c:if test="${empty post && posts.size() == 0}">
		<%-- no post --%>
		<div class="post no-post">
			<div class="no-post__wrap">
				<p class="no-post__text">No Post</p>
				<i class="no-post__icon fa-sharp fa-solid fa-circle-info"></i>
			</div>
		</div>
	</c:if>

	<c:forEach var="post" items="${posts}">
		<%-- post --%>
		<div class="post" data-post_id="${post.uuid}">
			<div class="post__header">
				<div class="post__author author">
					<a class="author__link" href="/">
						<img class="author__img" src="https://img.freepik.com/free-photo/portrait-young-beautiful-woman-gesticulating_273609-40467.jpg?w=1380&t=st=1671887930~exp=1671888530~hmac=ddcce059c475a821d7063bfe96cff64c87c20a8696f307333e4d92f905dca9a0" alt="profile-img" loading="lazy"/>
						<span class="author__id">${post.member_id}</span>
					</a>
				</div>
				<div class="post__etc">
					<span class="post__date">${post.regdate}</span>
					<c:choose>
						<c:when test="${post.member_uuid eq cookie['user_uuid'].getValue()}">
						<script>alert("${cookie['user_uuid'].getValue()}");</script>
						<div class="post__setting">
							<i class="setting__icon fa-solid fa-ellipsis"></i>
							<div class="setting__pop-up">
								<div class="setting__button setting__button--edit" onclick="requestPost('editpost', '${post.uuid}')">
									<span>edit</span><i class="fa-solid fa-pen-to-square"></i>
								</div>
								<div class="setting__button setting__button-delete" onclick="requestPost('deletepost', '${post.uuid}')">
									<span>delete</span><i class="fa-solid fa-trash"></i>
								</div>
							</div>
						</div>
						</c:when>
						<c:otherwise>
						<div class="post__setting--empty"></div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>

			<hr class="post__divider" />

			<div class="post__main">
				<a class="post__link" href="">
					<div class="image-grid">
						<c:forEach var="img" items="${post.images}">
							<img class="post__image" src="/images/${img}"
								alt="" loading="lazy">
						</c:forEach>
					</div>
					<div class="post__content">
						<p>${post.content}</p>
					</div>
				</a>
			</div>

			<div class="post__footer">
				<div class="post__likes">
					<i class="likes__btn likes__btn--none fa-regular fa-heart"></i> <i
						class="likes__btn likes__btn--selected fa-solid fa-heart"></i> <span
						class="likes__count">${post.likes}</span>
				</div>
				<div class="post__comments">
					<i class="comments__icon fa-solid fa-comment"></i> <span
						class="comments__count">${post.comments}</span>
				</div>
			</div>
		</div>
	</c:forEach>
	</c:if>

	<c:if test="${not empty post}">
		<%-- post detail --%>
		<div class="post post-detail" data-post_id="${post.uuid}">
			<div class="post__header">
				<div class="post__author author">
					<a class="author__link" href="/">
						<img class="author__img" src="https://img.freepik.com/free-photo/portrait-young-beautiful-woman-gesticulating_273609-40467.jpg?w=1380&t=st=1671887930~exp=1671888530~hmac=ddcce059c475a821d7063bfe96cff64c87c20a8696f307333e4d92f905dca9a0" alt="profile-img" loading="lazy"/>
						<span class="author__id">${post.member_id}</span>
					</a>
				</div>
				<div class="post__etc">
					<span class="post__date">${post.regdate}</span>
					<c:choose>
						<c:when test="${post.member_uuid eq user_uuid}">
						<div class="post__setting">
							<i class="setting__icon fa-solid fa-ellipsis"></i>
							<div class="setting__pop-up">
								<div class="setting__button setting__button--edit">
									<span>edit</span><i class="fa-solid fa-pen-to-square"></i>
								</div>
								<div class="setting__button setting__button-delete">
									<span>delete</span><i class="fa-solid fa-trash"></i>
								</div>
							</div>
						</div>
						</c:when>
						<c:otherwise>
						<div class="post__setting--empty"></div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>

			<hr class="post__divider" />

			<div class="post__main">
				<div class="image-grid">
					<c:forEach var="img" items="${post.images}">
						<img class="post__image" src="/images/${img}" alt="" loading="lazy">
					</c:forEach>
				</div>
				<div class="post__content">
					<p>${post.content}</p>
				</div>
			</div>

			<div class="post__footer">
				<div class="post__likes">
					<i class="likes__btn likes__btn--none fa-regular fa-heart"></i> <i
						class="likes__btn likes__btn--selected fa-solid fa-heart"></i> <span
						class="likes__count">${post.likes}</span>
				</div>
				<div class="post__comments">
					<i class="comments__icon fa-solid fa-comment"></i> <span
						class="comments__count">${post.comments}</span>
				</div>
			</div>
		</div>
	</c:if>	
	<script src="/FeedBoard/scripts/post.js"></script>
</body>
</html>