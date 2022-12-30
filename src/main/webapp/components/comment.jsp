<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/FeedBoard/stylesheets/comment.css" />
</head>
<body>
	<c:forEach var="c" items="${comments}">
		<div class="comment" data-post_id="${post.uuid}">
			<div class="comment__header">
				<div class="comment__author author">
					<span class="author__id">${c.comm_author_id}</span>
<%-- 					<a class="author__link" href="/">
						<img class="author__img" src="https://img.freepik.com/free-photo/portrait-young-beautiful-woman-gesticulating_273609-40467.jpg?w=1380&t=st=1671887930~exp=1671888530~hmac=ddcce059c475a821d7063bfe96cff64c87c20a8696f307333e4d92f905dca9a0" alt="profile-img" loading="lazy"/>
						<span class="author__id">${c.comm_author_id}</span>
					</a>
 --%>				</div>
				<div class="comment__etc">
					<span class="comment__date">${c.regDate}</span>
					<c:if test="${c.comm_author_uuid eq user_uuid}">
					<div class="comment__setting">
						<i class="setting__icon fa-solid fa-ellipsis"></i>
						<div class="setting__pop-up">
							<%--- <div class="setting__button setting__button--edit">
								<span>edit</span><i class="fa-solid fa-pen-to-square"></i>
							</div> ---%>
							<div class="setting__button setting__button-delete" onclick="deleteComment('${c.post_id}', '${c.uuid}');">
								<span>delete</span><i class="fa-solid fa-trash"></i>
							</div>
						</div>
					</div>
					</c:if>
				</div>
			</div>

			<div class="comment__main">
				<div class="comment__content">
					<p>${c.content}</p>
				</div>
			</div>
		</div>
	</c:forEach>
	<c:if test="${comments != null && not empty user_uuid}">
	<div class="comment__form-box">
		<form name="commentForm" action="addcomment" method="post">
			<input type="hidden" name="post_id" value="${post.uuid}"/>
			<textarea maxlength="500" name="comment_text" placeholder="Enter comments"></textarea>
			<button type="submit">COMMENT</button>
		</form>
	</div>
	</c:if>
	<script src="/FeedBoard/scripts/comment.js"></script>
</body>
</html>