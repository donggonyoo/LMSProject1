<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
	1. 첨부파일이 존재하는 게시물의 제목 앞에 @ 표시하기
	2. 게시글번호를 보여주기 위한 번호로 변경하기
--%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시물 목록</title>
    <!-- 최소한의 스타일링을 위한 Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <!-- W3CSS for select styling -->
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body>
    <h2>문의게시판</h2>
    <form action="${pageContext.request.contextPath}/post/getPosts" method="post" name="sf">
        <input type="hidden" name="pageNum" value="1">
        <select class="w3-select" name="column">
            <option value="">선택하시오</option>
            <option value="author_id">작성자</option>
            <option value="post_title">제목</option>
            <option value="post_content">내용</option>
            <option value="post_title,author_id">제목+작성자</option>
            <option value="post_title,post_content">제목+내용</option>
            <option value="author_id,post_content">작성자+내용</option>
            <option value="post_title,author_id,post_content">제목+작성자+내용</option>
        </select>
        
        <script>
            document.sf.column.value='${param.column}';
        </script>
        
        <input class="form-control d-inline-block w-auto" type="text" placeholder="Search" name="find" value="${param.find}">
        <button class="btn btn-primary" type="submit">Search</button>
    </form>

    <table class="table">
        <c:if test="${boardcount == 0}">
            <tr>
                <td colspan="5">등록된 게시글이 없습니다.</td>
            </tr>
        </c:if>
        <c:if test="${boardcount > 0}">
            <tr>
                <td colspan="5" style="text-align:right">글개수: ${boardcount}</td>
            </tr>
            
            <tr>
                <th width="8%">번호</th>
                <th width="50%">제목</th>
                <th width="14%">작성자</th>
                <th width="17%">등록일</th>
                <th width="11%">조회수</th>
            </tr>
            <c:set var="boardnum" value="${boardNum}"/>
            <c:forEach var="b" items="${list}">
                <tr>
                    <td>${boardnum}</td>
                    <c:set var="boardnum" value="${boardnum-1}"/>
                    <td style="text-align:left">        
                        <c:if test="${!empty b.post_file}">
                            <a href="../upload/board/${b.post_file}">@</a>
                        </c:if>
                        <c:if test="${empty b.post_file}">
                               
                        </c:if>
                        <%-- 답글인 경우 level 만큼 공백주기 --%>
                        <c:if test="${b.post_group_level > 0}">
                            <c:forEach var="i" begin="1" end="${b.post_group_level}">
                                 
                            </c:forEach>└
                        </c:if>
                        <a href="${pageContext.request.contextPath}/post/info?post_id=${b.post_id}">
                            ${b.post_title}
                        </a>
                    </td>
                    <td>${b.author_id}</td>
                    <fmt:formatDate value="${b.post_created_at}" pattern="yyyy-MM-dd" var="rdate"/>    
                    <fmt:formatDate value="${today}" pattern="yyyy-MM-dd" var="tdate"/>                     
                    <td>
                        <c:if test="${rdate == tdate}">
                            <fmt:formatDate value="${b.post_created_at}" pattern="HH:mm:ss"/>
                        </c:if>
                        <c:if test="${rdate != tdate}">
                            <fmt:formatDate value="${b.post_created_at}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </c:if>
                    </td>    
                    <td>${b.post_read_count}</td>    
                </tr>
            </c:forEach>
            <%-- 페이지 처리하기 --%>
            <tr>
                <td colspan="5" align="center">
                    <c:if test="${pageNum <= 1}">[이전]</c:if>
                    <c:if test="${pageNum > 1}">
                        <a href="javascript:listsubmit(${pageNum-1})">[이전]</a>
                    </c:if>
                    <c:forEach var="a" begin="${startpage}" end="${endpage}">
                        <c:if test="${a == pageNum}"><a href="#">[${a}]</a></c:if>
                        <c:if test="${a != pageNum}">
                            <a href="javascript:listsubmit(${a})">[${a}]</a>
                        </c:if>
                    </c:forEach>
                    <c:if test="${pageNum >= maxpage}">[다음]</c:if>
                    <c:if test="${pageNum < maxpage}">
                        <a href="javascript:listsubmit(${pageNum+1})">[다음]</a>
                    </c:if>
                </td>
            </tr>
        </c:if>
        <tr>
            <td colspan="5" style="text-align:right">
                <p align="right"><a href="${pageContext.request.contextPath}/post/createPost">[글쓰기]</a></p>
            </td>
        </tr>
    </table>
    <script type="text/javascript">
        function listsubmit(page) {
            let f = document.sf;
            f.pageNum.value = page;
            f.submit();
        }
    </script>
</body>
</html>