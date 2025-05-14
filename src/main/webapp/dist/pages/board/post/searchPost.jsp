<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>문의게시판 검색</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
    <div class="container mt-5">
        <h2 class="text-center">문의게시판 검색</h2>

        <!-- 검색 폼 -->
        <form action="${pageContext.request.contextPath}/post/searchPost" method="get" class="mb-4">
            <div class="row">
                <div class="col-md-3">
                    <select name="column" class="form-control">
                        <option value="all" ${column == 'all' ? 'selected' : ''}>전체</option>
                        <option value="author_id" ${column == 'author_id' ? 'selected' : ''}>작성자</option>
                        <option value="post_title" ${column == 'post_title' ? 'selected' : ''}>제목</option>
                        <option value="post_content" ${column == 'post_content' ? 'selected' : ''}>내용</option>
                        <option value="title_author" ${column == 'title_author' ? 'selected' : ''}>제목+작성자</option>
                        <option value="title_content" ${column == 'title_content' ? 'selected' : ''}>제목+내용</option>
                        <option value="author_content" ${column == 'author_content' ? 'selected' : ''}>작성자+내용</option>
                    </select>
                </div>
                <div class="col-md-6">
                    <input type="text" name="find" class="form-control" value="${find}" placeholder="검색어를 입력하세요">
                </div>
                <div class="col-md-3">
                    <button type="submit" class="btn btn-primary btn-block">검색</button>
                </div>
            </div>
        </form>

        <!-- 에러 메시지 -->
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <!-- 검색 결과 -->
        <c:if test="${not empty list}">
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>번호</th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>작성일</th>
                        <th>조회수</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="post" items="${list}" varStatus="status">
                        <tr>
                            <td>${boardNum - status.index}</td>
                            <td>
                                <c:if test="${post.postGroupLevel > 0}">
                                    <span style="margin-left: ${post.postGroupLevel * 20}px;">↳</span>
                                </c:if>
                                <a href="${pageContext.request.contextPath}/post/getPostDetail?post_id=${post.postId}">
                                    <c:if test="${post.postNotice}">[공지]</c:if>
                                    ${post.postTitle}
                                </a>
                            </td>
                            <td>${post.authorId}</td>
                            <td><fmt:formatDate value="${post.postCreatedAt}" pattern="yyyy-MM-dd HH:mm"/></td>
                            <td>${post.postReadCount}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <!-- 페이지네이션 -->
            <nav>
                <ul class="pagination justify-content-center">
                    <c:if test="${pageNum > 1}">
                        <li class="page-item">
                            <a class="page-link" href="?pageNum=${pageNum - 1}&column=${column}&find=${find}">이전</a>
                        </li>
                    </c:if>
                    <c:forEach begin="${startpage}" end="${endpage}" var="i">
                        <li class="page-item ${i == pageNum ? 'active' : ''}">
                            <a class="page-link" href="?pageNum=${i}&column=${column}&find=${find}">${i}</a>
                        </li>
                    </c:forEach>
                    <c:if test="${pageNum < maxpage}">
                        <li class="page-item">
                            <a class="page-link" href="?pageNum=${pageNum + 1}&column=${column}&find=${find}">다음</a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </c:if>
        <c:if test="${empty list}">
            <p class="text-center">검색 결과가 없습니다.</p>
        </c:if>

        <div class="text-center">
            <a href="${pageContext.request.contextPath}/post/getPosts" class="btn btn-secondary">문의게시판 목록</a>
        </div>
    </div>
</body>
</html>