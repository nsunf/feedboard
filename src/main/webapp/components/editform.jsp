<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/FeedBoard/stylesheets/editform.css" />
</head>
<body>
	<div class="edit-box">
		<div class="edit-box__top">
			<h2>New Post</h2>
		</div>
		<hr class="edit-box__divider"/>
		<form class="edit-box__main editform" name="editform" action="addpost" method="post" enctype="multipart/form-data">
			<label for="images-input" class="editform__image-label">Add Images | 
				<input class="editform__images" id="images-input" type="file" name="images" accept="image/png, image/jpg, image/jpeg, image/gif" multiple/>
			</label>
			<p class="editform__img-box"></p>
			<textarea class="editform__content" name="content" maxlength="1000" required></textarea>
			<button class="editform__submit-btn" type="submit">POST</button>
		</form>
	</div>
	<script src="/FeedBoard/scripts/editform.js"></script>
</body>
</html>