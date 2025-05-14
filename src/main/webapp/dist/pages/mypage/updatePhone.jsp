<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>핸드폰번호변경</title>
<style>
body {
    font-family: 'Noto Sans KR', sans-serif;
    padding: 20px;
}
input[type="email"] {
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
    width: 250px;
    margin-right: 10px;
}
button {
    padding: 8px 16px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}
button:first-of-type {
    background: #3182ce;
    color: white;
}
button:first-of-type:hover {
    background: #2b6cb0;
}
button:last-of-type {
    background: #e2e8f0;
    color: #4a5568;
}
button:last-of-type:hover {
    background: #cbd5e0;
}
</style>
</head>
<body>
    <h2>변경할 핸드폰번호를 입력</h2>
    <input type="text" id="phone">
    <button onclick="update()" type="button">변경</button>
    <button onclick="closeWindow()" type="button">취소</button>

    <script type="text/javascript">
    function update() {
  
            // 부모 창의 #email 요소 찾기
            const parentEmailInput = window.opener.document.querySelector("#phone");
            const newEmail = document.querySelector("#phone").value;

            // 부모 창의 이메일 값 변경
            parentEmailInput.value = newEmail;
            window.close();
    }

    function closeWindow() {
        window.close();
    }
    </script>
</body>
</html>