<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>공지사항</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body>
    <h1 class="fs-1">공지사항</h1><br>
    <form action="getNotices" method="post" name="sf">
    <input type="hidden" name="pageNum" value="1">
    <select class="w3-select" name="column" style="width: 10%;">
        <option value="">선택하시오</option>
        <option value="writerId">작성자</option>
        <option value="noticeTitle">제목</option>
        <option value="noticeContent">내용</option>
        <option value="noticeTitle,writerId">제목+작성자</option>
        <option value="noticeTitle,noticeContent">제목+내용</option>
        <option value="writerId,noticeContent">작성자+내용</option>
        <option value="noticeTitle,writerId,noticeContent">제목+작성자+내용</option>
    </select>

    <script>
        document.sf.column.value='${param.column}';
    </script>

    <input class="form-control d-inline-block w-auto" type="text" placeholder="Search" name="find" value="${param.find}" style="width: 90%;">
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
            <c:forEach var="n" items="${list}">
                <tr>
                    <td>${boardnum}</td>
                    <c:set var="boardnum" value="${boardnum-1}"/>
                    <td style="text-align:left">        
                        <c:if test="${!empty n.noticeFile}">
                            <a href="${pageContext.request.contextPath}/upload/board/${n.noticeFile}">📌</a>
                        </c:if>
                        <a href="${pageContext.request.contextPath}/notice/getNoticeDetail?notice_id=${n.noticeId}">
                            ${n.noticeTitle}
                        </a>
                    </td>
                    <td>${n.writerId}</td>
                    <fmt:formatDate value="${n.noticeCreatedAt}" pattern="yyyy-MM-dd" var="rdate"/>    
                    <fmt:formatDate value="${today}" pattern="yyyy-MM-dd" var="tdate"/>                     
                    <td>
                        <c:if test="${rdate == tdate}">
                            <fmt:formatDate value="${n.noticeCreatedAt}" pattern="HH:mm:ss"/>
                        </c:if>
                        <c:if test="${rdate != tdate}">
                            <fmt:formatDate value="${n.noticeCreatedAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </c:if>
                    </td>    
                    <td>${n.noticeReadCount}</td>    
                </tr>
            </c:forEach>
        </c:if>
    </table>
  <table class="table">
        <tr>
            <td colspan="5" style="text-align:right">
                <p align="right"><a href="${pageContext.request.contextPath}/notice/createNotice">[글쓰기]</a></p>
            </td>
        </tr>
    </table>
    <c:if test="${boardcount > 0}">
        <div class="d-flex justify-content-center mt-4">
            <ul class="pagination">
                <li class="page-item ${pageNum <= 1 ? 'disabled' : ''}">
                    <c:if test="${pageNum <= 1}">
                        <span class="page-link">[이전]</span>
                    </c:if>
                    <c:if test="${pageNum > 1}">
                        <a class="page-link" href="javascript:listsubmit(${pageNum-1})">[이전]</a>
                    </c:if>
                </li>

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