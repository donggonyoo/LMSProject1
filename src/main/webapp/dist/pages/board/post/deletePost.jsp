<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 삭제</title>
</head>
<body>
<h2>게시글 삭제</h2>
<form action="delete" method="post">
	<input type="hidden" name="postId" value="${param.post_id}">
	<label>Password : </label>
	<input type="password" class="form-control" name="pass">
	<div class="text-right">
		<button type="submit" class="btn btn-danger">게시글 삭제</button>
	</div>
</form>
</body>
</html>