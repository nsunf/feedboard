<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/FeedBoard/stylesheets/editform.css" />
</head>
<body>
	<c:choose>
		<c:when test="${not empty post}">
		<div class="edit-box">
			<div class="edit-box__top">
				<h2>Edit Post</h2>
			</div>
			<hr class="edit-box__divider"/>
			<form class="edit-box__main editform" name="editform" action="updatepost" method="post" enctype="multipart/form-data">
				<input type="hidden" name="post_uuid" value="${post.uuid}"/>
				<textarea class="editform__content" name="post_content" maxlength="1000" required>${post.content}</textarea>
				<button class="editform__submit-btn" type="submit">EDIT</button>
			</form>
		</div>
		</c:when>

		<c:otherwise>
		<div class="edit-box">
			<div class="edit-box__top">
				<h2>New Post</h2>
			</div>
			<hr class="edit-box__divider"/>
			<form class="edit-box__main editform" name="editform" action="addpost" method="post" enctype="multipart/form-data">
				<label for="images-input" class="editform__image-label">Add Images | 
					<input class="editform__images" id="images-input" type="file" name="images" accept="image/png, image/jpg, image/jpeg, image/gif" multiple/>
				</label>
				<textarea class="editform__content" name="post_content" maxlength="1000" required></textarea>
				<button class="editform__submit-btn" type="submit">POST</button>
			</form>
		</div>
		</c:otherwise>
	</c:choose>
</body>
</html>