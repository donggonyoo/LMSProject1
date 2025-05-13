<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Password Alert</title>
</head>
<body>
    <form action="pwUpdate" name="f" method="post">
        <input type="hidden" name="id" value="${requestScope.id}">
        <input type="hidden" name="pw" value="${requestScope.pw}">
    </form>
    <!-- 디버깅 정보 -->
    <div style="display: none;">
        <p>msg: ${requestScope.msg}</p>
        <p>id: ${requestScope.id}</p>
        <p>pw: ${requestScope.pw}</p>
    </div>
    <script>
        // 메시지 표시
        var msg = "${requestScope.msg}";
        if (msg) {
            alert(msg);
        }
        // 폼 제출
        console.log('Submitting form with id:', '${requestScope.id}', 'pw:', '${requestScope.pw}');
        document.f.submit();
    </script>
</body>
</html>