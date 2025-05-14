<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="path" value="${pageContext.request.contextPath}" scope="application" />

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>오류 발생</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .error-container {
            text-align: center;
            padding: 40px;
            border-radius: 12px;
            background-color: #ffffff;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }
        .error-code {
            font-size: 72px;
            font-weight: bold;
            color: #dc3545;
        }
        .error-message {
            font-size: 24px;
            margin-bottom: 20px;
        }
        .home-btn {
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="error-code">오류!</div>
        <div class="error-message">죄송합니다. 문제가 발생했습니다.</div>
        <p>잠시 후 다시 시도하거나, 메인 페이지로 돌아가주세요.</p>
        <a href="/LMSProject1/mypage/index" class="btn btn-danger home-btn">홈으로 이동</a>
    </div>
</body>
</html>
