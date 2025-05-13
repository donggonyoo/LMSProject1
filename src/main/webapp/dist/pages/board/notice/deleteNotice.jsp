<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 삭제</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
    .label-inline {
        display: inline;
        margin-right: 0.5rem;
    }
</style>
</head>
<body>
<h1 class="fs-1">게시글 삭제</h1>
<c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
</c:if>
<form action="${pageContext.request.contextPath}/notice/delete" method="post">
    <input type="hidden" name="noticeId" value="${notice.noticeId}">
    <div class="mb-3">
        <label class="form-label label-inline fs-3">작성자:</label>
        <span class="form-control-plaintext fs-3 d-inline">${notice.writerId}</span>
    </div>
    <div class="mb-3">
    	<label class="form-label label-inline fs-3">제목:</label>
    	<span class="fs-3 ms-2">${notice.noticeTitle}</span> 
    </div>
    <div class="mb-3">
        <label for="pass" class="form-label fs-3">Password:</label>
        <input type="password" class="form-control " id="pass" name="pass" required>
    </div>
    <div class="text-end">
        <button type="submit" class="btn btn-danger">게시글 삭제</button>
    </div>
</form>
</body>
</html>