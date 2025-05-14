<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>답글 작성</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>
</head>
<body>
    <div class="container mt-5">
        <h2 class="text-center">답글 작성</h2>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
            <% session.removeAttribute("error"); %>
        </c:if>
        <form action="writeReply" method="post" enctype="multipart/form-data" class="mt-4">
            <input type="hidden" name="num" value="${board.postId}">
            <input type="hidden" name="grp" value="${board.postGroup}">
            <input type="hidden" name="grplevel" value="${board.postGroupLevel}">
            <input type="hidden" name="grpstep" value="${board.postGroupStep}">
            <div class="form-group">
                <label for="writer">작성자:</label>
                <input type="text" name="writer" id="writer" class="form-control" value="${userName}" readonly>
            </div>
            <div class="form-group">
                <label for="pass">비밀번호:</label>
                <input type="password" name="pass" id="pass" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="title">제목:</label>
                <input type="text" name="title" id="title" class="form-control" value="RE: ${board.postTitle}" required>
            </div>
            <div class="form-group">
                <label for="content">내용:</label>
                <textarea name="content" id="content" class="form-control"></textarea>
            </div>
            <div class="form-group">
                <label for="post_file">파일:</label>
                <input type="file" name="post_file" id="post_file" class="form-control">
            </div>
            <button type="submit" class="btn btn-primary">등록</button>
            <a href="getPosts" class="btn btn-secondary">취소</a>
        </form>
        <script>
            $(document).ready(function() {
                $('#content').summernote({ height: 300 });
            });
        </script>
    </div>
</body>
</html>