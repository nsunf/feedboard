<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="post" data-post_id="${post.uuid}">
		<div class="post__header">
			<div class="post__author author">
				<span class="author__id">${post.member_id}</span>
			</div>
			<div class="post__etc">
				<span class="post__date">${post.regdate}</span>
				<c:choose>
					<c:when test="${post.member_uuid eq user_uuid}">
						<div class="post__setting">
							<i class="setting__icon fa-solid fa-ellipsis"></i>
							<div class="setting__pop-up">
								<div class="setting__button setting__button--edit"
									onclick="requestPost('editpost', '${post.uuid}')">
									<span>edit</span><i class="fa-solid fa-pen-to-square"></i>
								</div>
								<div class="setting__button setting__button-delete"
									onclick="requestPost('deletepost', '${post.uuid}')">
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
			<c:choose>
				<c:when test="${empty user_uuid}">
					<div class="post__likes">
						<i class="likes__btn likes__btn--none fa-regular fa-heart"></i> <span
							class="likes__count">${post.likes}</span>
					</div>
				</c:when>
				<c:when test="${post.liked}">
					<div class="post__likes"
						onclick="toggleLike('${post.uuid}', false);">
						<i class="likes__btn likes__btn--selected fa-solid fa-heart"></i>
						<span class="likes__count">${post.likes}</span>
					</div>
				</c:when>
				<c:otherwise>
					<div class="post__likes"
						onclick="toggleLike('${post.uuid}', true);">
						<i class="likes__btn likes__btn--none fa-regular fa-heart"></i> <span
							class="likes__count">${post.likes}</span>
					</div>
				</c:otherwise>
			</c:choose>
			<div class="post__comments">
				<i class="comments__icon fa-solid fa-comment"></i> <span
					class="comments__count">${post.comments}</span>
			</div>
		</div>
	</div>
</body>
</html>