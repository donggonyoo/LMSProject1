<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시물 상세</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body>
    <h2 class="text-center">게시물 상세</h2>
    <table class="table">
        <tr>
            <th>글쓴이</th>
            <td>${post.author_id}</td>
        </tr>
        <tr>
            <th>제목</th>
            <td>${post.post_title}</td>
        </tr>
        <tr>
            <th>등록일</th>
            <td>
                <fmt:formatDate value="${post.post_created_at}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
        </tr>
        <tr>
            <th>조회수</th>
            <td>${post.post_read_count}</td>
        </tr>
        <tr>
            <th>첨부파일</th>
            <td>
                <c:if test="${!empty post.post_file}">
                    <a href="${pageContext.request.contextPath}/upload/board/${post.post_file}">${post.post_file}</a>
                </c:if>
                <c:if test="${empty post.post_file}">
                    없음
                </c:if>
            </td>
        </tr>
        <tr>
            <th>내용</th>
            <td>${post.post_content}</td>
        </tr>
        <tr>
            <td colspan="2">
                <a href="${pageContext.request.contextPath}/post/getPosts" class="btn btn-secondary">[목록]</a>
            </td>
        </tr>
    </table>
</body>
</html>