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
	<c:choose>
		<c:when test="${not empty post}">
			<%-- post detail --%>
			<div class="post-detail-wrap">
				<%@ include file="./post.jsp"%>
			</div>
		</c:when>
		<c:when test="${posts.size() != 0}">
			<%-- post list --%>
			<c:forEach var="post" items="${posts}">
				<%@ include file="./post.jsp"%>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<%-- no post --%>
			<div class="post no-post">
				<div class="no-post__wrap">
					<p class="no-post__text">No Post</p>
					<i class="no-post__icon fa-sharp fa-solid fa-circle-info"></i>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
	<script src="/FeedBoard/scripts/postList.js"></script>
</body>
</html>