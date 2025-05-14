<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시물 수정</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>
</head>
<body>
    <div class="container mt-5">
        <h2 class="text-center">게시물 수정</h2>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
            <% session.removeAttribute("error"); %>
        </c:if>
        <form action="update" method="post" enctype="multipart/form-data" class="mt-4">
            <input type="hidden" name="postId" value="${p.postId}">
            <div class="form-group">
                <label for="authorId">작성자:</label>
                <input type="text" name="authorId" id="authorId" class="form-control" value="${p.authorName}" readonly>
            </div>
            <div class="form-group">
                <label for="postPassword">비밀번호:</label>
                <input type="password" name="postPassword" id="postPassword" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="postTitle">제목:</label>
                <input type="text" name="postTitle" id="postTitle" class="form-control" value="${p.postTitle}" required>
            </div>
            <div class="form-group">
                <label for="postContent">내용:</label>
                <textarea name="postContent" id="postContent" class="form-control">${p.postContent}</textarea>
            </div>
            <div class="form-group">
                <label for="postFile">파일:</label>
                <input type="file" name="postFile" id="postFile" class="form-control">
                <c:if test="${not empty p.postFile}">
                    <p>현재 파일: ${p.postFile} <a href="/upload/board/${p.postFile}" target="_blank">보기</a></p>
                    <input type="hidden" name="postFile" value="${p.postFile}">
                </c:if>
            </div>
            <div class="form-group form-check">
                <input type="checkbox" name="post_notice" id="post_notice" class="form-check-input" value="1" ${p.postNotice ? 'checked' : ''}>
                <label class="form-check-label" for="post_notice">공지사항</label>
            </div>
            <button type="submit" class="btn btn-primary">수정</button>
            <a href="getPosts" class="btn btn-secondary">취소</a>
        </form>
        <script>
            $(document).ready(function() {
                $('#postContent').summernote({ height: 300 });
                $('#postContent').summernote('code', '${p.postContent}');
            });
        </script>
    </div>
</body>
</html>