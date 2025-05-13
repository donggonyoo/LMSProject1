<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>공지사항 게시물 상세</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <div class="container mt-5">
        <h1 class="fs-1">게시물 상세</h1> <br>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        <c:if test="${not empty notice}">
            <table class="table">
                <tr>
                    <th>제목</th>
                    <td>${notice.noticeTitle}</td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td>${notice.writerId}</td>
                </tr>
                <tr>
                    <th>작성일</th>
                    <td><fmt:formatDate value="${notice.noticeCreatedAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                </tr>
                <tr>
                    <th>조회수</th>
                    <td>${notice.noticeReadCount}</td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td>${notice.noticeContent}</td>
                </tr>
                <tr>
                    <th>첨부파일</th>
                    <td>
                        <c:if test="${not empty notice.noticeFile}">
                            <a href="${pageContext.request.contextPath}/upload/board/${notice.noticeFile}" download>${notice.noticeFile}</a>
                        </c:if>
                        <c:if test="${empty notice.noticeFile}">
                            없음
                        </c:if>
                    </td>
                </tr>
            </table>

            <div class="text-end mb-5">
                <a href="${pageContext.request.contextPath}/notice/getNotices" class="btn btn-secondary">목록</a>
                <a href="${pageContext.request.contextPath}/notice/updateNotice?noticeId=${notice.noticeId}" class="btn btn-secondary">수정</a>
                <a href="${pageContext.request.contextPath}/notice/deleteNotice?noticeId=${notice.noticeId}" class="btn btn-danger">삭제</a>
            </div>
        </c:if>
        <c:if test="${empty notice && empty error}">
            <div class="alert alert-warning">게시물이 존재하지 않습니다.</div>
        </c:if>
    </div>
</body>
</html>