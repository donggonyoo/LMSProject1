<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>문의게시판</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <style>
        .notice-label {
            color: red;
            font-weight: bold;
        }
        .notice-row {
            background-color: #f8f9fa;
            border-left: 3px solid red;
        }
    </style>
</head>
<body>
    <h1 class="fs-1">문의게시판</h1><br>
    <form action="getPosts" method="post" name="sf">
    <input type="hidden" name="pageNum" value="1">
    <select class="w3-select" name="column" style="width: 10%;">
        <option value="">선택하시오</option>
        <option value="authorId">작성자</option>
        <option value="postTitle">제목</option>
        <option value="postContent">내용</option>
        <option value="postTitle,authorId">제목+작성자</option>
        <option value="postTitle,postContent">제목+내용</option>
        <option value="authorId,postContent">작성자+내용</option>
        <option value="postTitle,authorId,postContent">제목+작성자+내용</option>
    </select>

    <script>
        document.sf.column.value='${param.column}';
    </script>

    <input class="form-control d-inline-block w-auto" type="text" placeholder="Search" name="find" value="${param.find}" style="width: 90%;">
    <button class="btn btn-primary" type="submit">Search</button>
</form>

    <table class="table">
        <c:if test="${boardcount == 0 && empty notices}">
            <tr>
                <td colspan="5">등록된 게시글이 없습니다.</td>
            </tr>
        </c:if>
        <c:if test="${boardcount > 0 || !empty notices}">
            <tr>
                <td colspan="5" style="text-align:right">글개수: ${boardcount + notices.size()}</td>
            </tr>
            
            <tr>
                <th width="8%">번호</th>
                <th width="50%">제목</th>
                <th width="14%">작성자</th>
                <th width="17%">등록일</th>
                <th width="11%">조회수</th>
            </tr>

            <%-- 공지 게시물 표시 --%>
            <c:if test="${!empty notices}">
                <c:forEach var="b" items="${notices}">
                    <tr class="notice-row">
                        <td><span class="notice-label">공지</span></td>
                        <td style="text-align:left">        
                            <c:if test="${!empty b.postFile}">
                                <a href="${pageContext.request.contextPath}/upload/board/${b.postFile}">📌</a>
                            </c:if>
                            <c:if test="${empty b.postFile}">
                                   
                            </c:if>
                            <span class="notice-label">[공지]</span>
                            <c:if test="${b.postGroupLevel > 0}">
                                <c:forEach var="i" begin="1" end="${b.postGroupLevel}">
                                     
                                </c:forEach>↳
                            </c:if>
                            <a href="${pageContext.request.contextPath}/post/getPostDetail?post_id=${b.postId}">
                                ${b.postTitle}
                            </a>
                        </td>
                        <td>${b.authorId}</td>
                        <fmt:formatDate value="${b.postCreatedAt}" pattern="yyyy-MM-dd" var="rdate"/>    
                        <fmt:formatDate value="${today}" pattern="yyyy-MM-dd" var="tdate"/>                     
                        <td>
                            <c:if test="${rdate == tdate}">
                                <fmt:formatDate value="${b.postCreatedAt}" pattern="HH:mm:ss"/>
                            </c:if>
                            <c:if test="${rdate != tdate}">
                                <fmt:formatDate value="${b.postCreatedAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
                            </c:if>
                        </td>    
                        <td>${b.postReadCount}</td>    
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="5"><hr></td>
                </tr>
            </c:if>

            <%-- 일반 게시물 표시 --%>
            <c:set var="boardnum" value="${boardNum}"/>
            <c:forEach var="b" items="${list}">
                <tr>
                    <td>${boardnum}</td>
                    <c:set var="boardnum" value="${boardnum-1}"/>
                    <td style="text-align:left">        
                        <c:if test="${!empty b.postFile}">
                            <a href="${pageContext.request.contextPath}/upload/board/${b.postFile}">📌</a>
                        </c:if>
                        <c:if test="${empty b.postFile}">
                               
                        </c:if>
                        <c:if test="${b.postGroupLevel > 0}">
                            <c:forEach var="i" begin="1" end="${b.postGroupLevel}">
                                 
                            </c:forEach>↳
                        </c:if>
                        <a href="${pageContext.request.contextPath}/post/getPostDetail?post_id=${b.postId}">
                            ${b.postTitle}
                        </a>
                    </td>
                    <td>${b.authorId}</td>
                    <fmt:formatDate value="${b.postCreatedAt}" pattern="yyyy-MM-dd" var="rdate"/>    
                    <fmt:formatDate value="${today}" pattern="yyyy-MM-dd" var="tdate"/>                     
                    <td>
                        <c:if test="${rdate == tdate}">
                            <fmt:formatDate value="${b.postCreatedAt}" pattern="HH:mm:ss"/>
                        </c:if>
                        <c:if test="${rdate != tdate}">
                            <fmt:formatDate value="${b.postCreatedAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </c:if>
                    </td>    
                    <td>${b.postReadCount}</td>    
                </tr>
            </c:forEach>
        </c:if>
    </table>

    <table class="table">
        <tr>
            <td colspan="5" style="text-align:right">
                <p align="right"><a href="${pageContext.request.contextPath}/post/createPost">[글쓰기]</a></p>
            </td>
        </tr>
    </table>
    <%-- 페이지 처리하기 --%>
    <c:if test="${boardcount > 0}">
        <div class="d-flex justify-content-center mt-4">
            <ul class="pagination">
                <%-- 이전 버튼 --%>
                <li class="page-item ${pageNum <= 1 ? 'disabled' : ''}">
                    <c:if test="${pageNum <= 1}">
                        <span class="page-link">[이전]</span>
                    </c:if>
                    <c:if test="${pageNum > 1}">
                        <a class="page-link" href="javascript:listsubmit(${pageNum-1})">[이전]</a>
                    </c:if>
                </li>

                <%-- 페이지 번호 --%>
                <c:forEach var="a" begin="${startpage}" end="${endpage}">
                    <li class="page-item ${a == pageNum ? 'active' : ''}">
                        <c:if test="${a == pageNum}">
                            <span class="page-link">[${a}]</span>
                        </c:if>
                        <c:if test="${a != pageNum}">
                            <a class="page-link" href="javascript:listsubmit(${a})">[${a}]</a>
                        </c:if>
                    </li>
                </c:forEach>

                <%-- 다음 버튼 --%>
                <li class="page-item ${pageNum >= maxpage ? 'disabled' : ''}">
                    <c:if test="${pageNum >= maxpage}">
                        <span class="page-link">[다음]</span>
                    </c:if>
                    <c:if test="${pageNum < maxpage}">
                        <a class="page-link" href="javascript:listsubmit(${pageNum+1})">[다음]</a>
                    </c:if>
                </li>
            </ul>
        </div>
    </c:if>



    <script type="text/javascript">
        function listsubmit(page) {
            let f = document.sf;
            f.pageNum.value = page;
            f.submit();
        }
    </script>
</body>
</html>