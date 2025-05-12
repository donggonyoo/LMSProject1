<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시물 상세</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2>게시물 상세</h2>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        <table class="table">
            <tr>
                <th>제목</th>
                <td>${post.postTitle}</td>
            </tr>
            <tr>
                <th>작성자</th>
                <td>${post.authorId}</td>
            </tr>
            <tr>
                <th>작성일</th>
                <td><fmt:formatDate value="${post.postCreatedAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            </tr>
            <tr>
                <th>조회수</th>
                <td>${post.postReadCount}</td>
            </tr>
            <tr>
                <th>내용</th>
                <td>${post.postContent}</td>
            </tr>
            <tr>
                <th>첨부파일</th>
                <td>
                    <c:if test="${not empty post.postFile}">
                        <a href="${pageContext.request.contextPath}/upload/board/${post.postFile}" download>${post.postFile}</a>
                    </c:if>
                </td>
            </tr>
        </table>

        <div class="text-end mb-5">
            <a href="${pageContext.request.contextPath}/post/getPosts" class="btn btn-secondary">목록</a>
            <a href="${pageContext.request.contextPath}/post/updatePost?postId=${post.postId}" class="btn btn-secondary">수정</a>
            <a href="${pageContext.request.contextPath}/post/createPost?parent_postId=${post.postId}" class="btn btn-primary">답글 작성</a>
            <a href="${pageContext.request.contextPath}/post/deletePost?postId=${post.postId}" class="btn btn-danger">삭제</a>
        </div>

        <h3>댓글</h3>

        <c:forEach var="comment" items="${commentList}">
            <c:if test="${empty comment.parentCommentId}">
                <div class="card my-2">
                    <div class="card-body">
                        <p><strong>${comment.writerId}</strong>
                        <small class="text-muted">
                            <fmt:formatDate value="${comment.createdAt}" pattern="yyyy-MM-dd HH:mm"/>
                        </small></p>
                        <p>${comment.commentContent}</p>

                        <c:forEach var="child" items="${commentList}">
                            <c:if test="${child.parentCommentId eq comment.commentId}">
                                <div class="ms-4 border-start ps-3 mt-2">
                                    <p><strong>${child.writerId}</strong>
                                    <small class="text-muted">
                                        <fmt:formatDate value="${child.createdAt}" pattern="yyyy-MM-dd HH:mm"/>
                                    </small></p>
                                    <p>${child.commentContent}</p>
                                </div>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </c:if>
        </c:forEach>

        <form action="${pageContext.request.contextPath}/post/writeComment" method="post" class="mt-4">
            <input type="hidden" name="postId" value="${post.postId}">
            <div class="mb-3">
                <label for="writerId" class="form-label">작성자</label>
                <input type="text" class="form-control" id="writerId" name="writerId">
            </div>
            <div class="mb-3">
                <label for="commentContent" class="form-label">댓글 내용</label>
                <textarea class="form-control" id="commentContent" name="commentContent" rows="3"></textarea>
            </div>
            <button type="submit" class="btn btn-primary">댓글 작성</button>
        </form>
    </div>
</body>
</html>