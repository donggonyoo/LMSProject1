<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MAIN</title>
</head>
<body>

<!-- session에 login속성에 값이있다면 로그인되어있다고본다. -->
<h3>${sessionScope.login}님이 로그인 했어요</h3>
<h3><a href="logout">로그아웃</a></h3>
<h3><a href="info?id=${sessionScope.login}">회원정보 보기</a></h3>

<c:if test="${sessionScope.login=='admin'}">
<h3><a href="list">회원목록보기</a></h3>
</c:if>

</body>
</html>