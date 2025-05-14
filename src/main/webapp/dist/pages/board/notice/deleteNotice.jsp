<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시물 삭제</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
    <div class="container mt-5">
        <h2 class="text-center">게시물 삭제</h2>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/notice/delete" method="post" class="mt-4">
            <input type="hidden" name="noticeId" value="${notice.noticeId}">
            <div class="form-group">
                <label class="font-weight-bold">작성자:</label>
                <span class="ml-2">${notice.writerName}</span>
            </div>
            <div class="form-group">
                <label class="font-weight-bold">제목:</label>
                <span class="ml-2">${notice.noticeTitle}</span>
            </div>
            <div class="form-group">
                <label for="pass">비밀번호:</label>
                <input type="password" name="pass" id="pass" class="form-control" required>
            </div>
            <div class="text-right">
                <button type="submit" class="btn btn-danger">삭제</button>
                <a href="${pageContext.request.contextPath}/notice/getNotices" class="btn btn-secondary ml-2">취소</a>
            </div>
        </form>
    </div>
</body>
</html>