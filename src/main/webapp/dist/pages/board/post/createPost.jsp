<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시물 작성</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>
</head>
<body>
    <div class="container mt-5">
        <h2 class="text-center">게시물 작성</h2>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        <form action="${pageContext.request.contextPath}/post/write" method="post" enctype="multipart/form-data" class="mt-4">
            <input type="hidden" name="parent_post_id" value="${parent_post_id}">
            <div class="form-group">
                <label for="authorId">작성자:</label>
                <input type="text" name="authorId" id="authorId" class="form-control" value="${userName}" readonly>
            </div>
            <div class="form-group">
                <label for="pass">비밀번호:</label>
                <input type="password" name="pass" id="pass" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="post_title">제목:</label>
                <input type="text" name="post_title" id="post_title" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="post_content">내용:</label>
                <textarea name="post_content" id="post_content" class="form-control"></textarea>
            </div>
            <div class="form-group">
                <label for="post_file">파일:</label>
                <input type="file" name="post_file" id="post_file" class="form-control">
            </div>
            <div class="form-group form-check">
                <input type="checkbox" name="post_notice" id="post_notice" class="form-check-input" value="1">
                <label class="form-check-label" for="post_notice">공지사항</label>
            </div>
            <button type="submit" class="btn btn-primary">등록</button>
            <a href="${pageContext.request.contextPath}/post/getPosts" class="btn btn-secondary">취소</a>
        </form>
        <script>
            $(document).ready(function() {
                $('#post_content').summernote({ height: 300 });
            });
        </script>
    </div>
</body>
</html>