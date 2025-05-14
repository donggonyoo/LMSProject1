<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>문의게시판 게시물 상세</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <div class="container mt-5">
        <h1 class="fs-1">게시물 상세</h1> <br>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
            <% session.removeAttribute("error"); %>
        </c:if>
        <table class="table">
            <tr>
                <th>제목</th>
                <td>${post.postTitle}</td>
            </tr>
            <tr>
                <th>작성자</th>
                <td>${post.authorName}</td>
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
                        <a href="/upload/board/${post.postFile}" download>${post.postFile}</a>
                    </c:if>
                </td>
            </tr>
        </table>

        <div class="text-end mb-5">
            <a href="getPosts" class="btn btn-secondary">목록</a>
            <c:if test="${isLoggedIn and post.authorId == sessionScope.login}">
                <a href="updatePost?postId=${post.postId}" class="btn btn-secondary">수정</a>
                <a href="replyPost?postId=${post.postId}" class="btn btn-primary">답글 작성</a>
                <a href="deletePost?postId=${post.postId}" class="btn btn-danger">삭제</a>
            </c:if>
        </div>

        <h3>댓글</h3>

        <c:forEach var="comment" items="${commentList}">
            <c:if test="${empty comment.parentCommentId}">
                <div class="card my-2">
                    <div class="card-body">
                        <p><strong>${comment.commentAuthorName}</strong>
                        <small class="text-muted">
                            <fmt:formatDate value="${comment.createdAt}" pattern="yyyy-MM-dd HH:mm"/>
                        </small></p>
                        <p>${comment.commentContent}</p>
                        <c:if test="${isLoggedIn}">
                            <div class="d-flex gap-2">
                                <a href="javascript:void(0)" onclick="showReplyForm('${comment.commentId}')" class="btn btn-sm btn-secondary">댓글</a>
                                <c:if test="${comment.writerId == sessionScope.login}">
                                    <a href="javascript:void(0)" onclick="showEditForm('${comment.commentId}')" class="btn btn-sm btn-warning">수정</a>
                                    <a href="javascript:void(0)" onclick="confirmDelete('${comment.commentId}')" class="btn btn-sm btn-danger">삭제</a>
                                </c:if>
                            </div>
                        </c:if>

                        <c:forEach var="child" items="${commentList}">
                            <c:if test="${child.parentCommentId eq comment.commentId}">
                                <div class="ms-4 border-start ps-3 mt-2">
                                    <p><strong>${child.commentAuthorName}</strong>
                                    <small class="text-muted">
                                        <fmt:formatDate value="${child.createdAt}" pattern="yyyy-MM-dd HH:mm"/>
                                    </small></p>
                                    <p>${child.commentContent}</p>
                                    <c:if test="${isLoggedIn}">
                                        <div class="d-flex gap-2">
                                            <c:if test="${child.writerId == sessionScope.login}">
                                                <a href="javascript:void(0)" onclick="showEditForm('${child.commentId}')" class="btn btn-sm btn-warning">수정</a>
                                                <a href="javascript:void(0)" onclick="confirmDelete('${child.commentId}')" class="btn btn-sm btn-danger">삭제</a>
                                            </c:if>
                                        </div>

                                        <form id="editForm-${child.commentId}" action="updateComment" method="post" class="mt-2" style="display:none;">
                                            <input type="hidden" name="commentId" value="${child.commentId}">
                                            <input type="hidden" name="postId" value="${post.postId}">
                                            <div class="mb-3">
                                                <label for="editWriterId-${child.commentId}" class="form-label">작성자</label>
                                                <input type="text" class="form-control" id="editWriterId-${child.commentId}" name="writerId" value="${child.commentAuthorName}" readonly>
                                            </div>
                                            <div class="mb-3">
                                                <label for="editCommentContent-${child.commentId}" class="form-label">댓글 내용</label>
                                                <textarea class="form-control" id="editCommentContent-${child.commentId}" name="commentContent" rows="2">${child.commentContent}</textarea>
                                            </div>
                                            <button type="submit" class="btn btn-primary btn-sm">수정 완료</button>
                                            <button type="button" onclick="hideEditForm('${child.commentId}')" class="btn btn-secondary btn-sm">취소</button>
                                        </form>
                                    </c:if>
                                </div>
                            </c:if>
                        </c:forEach>

                        <c:if test="${isLoggedIn}">
                            <form id="replyForm-${comment.commentId}" action="writeComment" method="post" class="mt-2" style="display:none;">
                                <input type="hidden" name="postId" value="${post.postId}">
                                <input type="hidden" name="parentCommentId" value="${comment.commentId}">
                                <div class="mb-3">
                                    <label for="writerId-${comment.commentId}" class="form-label">작성자</label>
                                    <input type="text" class="form-control" id="writerId-${comment.commentId}" name="writerId" value="${authorName}" readonly>
                                </div>
                                <div class="mb-3">
                                    <label for="commentContent-${comment.commentId}" class="form-label">댓글 내용</label>
                                    <textarea class="form-control" id="commentContent-${comment.commentId}" name="commentContent" rows="2"></textarea>
                                </div>
                                <button type="submit" class="btn btn-primary btn-sm">대댓글 작성</button>
                            </form>
                        </c:if>
                    </div>
                </div>
            </c:if>
        </c:forEach>

        <c:if test="${isLoggedIn}">
            <form action="writeComment" method="post" class="mt-4">
                <input type="hidden" name="postId" value="${post.postId}">
                <div class="mb-3">
                    <label for="writerId" class="form-label">작성자</label>
                    <input type="text" class="form-control" id="writerId" name="writerId" value="${authorName}" readonly>
                </div>
                <div class="mb-3">
                    <label for="commentContent" class="form-label">댓글 내용</label>
                    <textarea class="form-control" id="commentContent" name="commentContent" rows="3"></textarea>
                </div>
                <button type="submit" class="btn btn-primary">댓글 작성</button>
            </form>
        </c:if>
    </div>

    <script>
        function showReplyForm(commentId) {
            document.getElementById('replyForm-' + commentId).style.display = 'block';
        }

        function showEditForm(commentId) {
            document.getElementById('editForm-' + commentId).style.display = 'block';
        }

        function hideEditForm(commentId) {
            document.getElementById('editForm-' + commentId).style.display = 'none';
        }

        function confirmDelete(commentId) {
            if (confirm('댓글을 삭제하시겠습니까?')) {
                $.ajax({
                    url: 'deleteComment',
                    type: 'POST',
                    data: { commentId: commentId, postId: '${post.postId}' },
                    success: function(response) {
                        if (response === 'success') {
                            alert('댓글이 삭제되었습니다.');
                            window.location.href = 'getPostDetail?post_id=${post.postId}';
                        } else {
                            alert('삭제 실패: ' + response);
                        }
                    },
                    error: function(xhr, status, error) {
                        alert('삭제 중 오류가 발생했습니다: ' + error);
                    }
                });
            }
        }
    </script>
</body>
</html>