<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setCharacterEncoding("UTF-8"); %>
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
			<%@ include file="../components/postList.jsp" %>
			<%@ include file="../components/comment.jsp" %>
    	</main>
    	<aside class="aside">
    		<%@ include file="../components/aside.jsp" %>
    	</aside>
	</section>
</body>
</html>