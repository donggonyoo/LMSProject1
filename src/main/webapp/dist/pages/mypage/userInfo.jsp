<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="path" value="${pageContext.request.contextPath}" scope="application" />
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Academic Management System UI Mockup</title>
<script src="https://cdn.tailwindcss.com"></script>
<style>
body {
    font-family: 'Noto Sans KR', sans-serif;
    /* 대체 URL: 서버에 campus-bg.jpg가 없으면 아래 주석 해제 */
    /* background-image: url('https://images.unsplash.com/photo-1506748686214-e9df14d4d9d0'); */
    background-size: cover;
    background-position: center;
    background-attachment: fixed;
    margin: 0;
}

body::before {
    content: '';
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(255, 255, 255, 0.75); /* 반투명 화이트 오버레이로 가독성 유지 */
    z-index: -1;
}

.container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 20px;
}

.card {
    background: white;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    padding: 20px;
    margin-bottom: 20px;
    max-width: 100%;
    overflow: hidden;
}

.table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 10px;
}

.table th, .table td {
    border: 1px solid #e2e8f0;
    padding: 10px;
    text-align: left;
}

.table th {
    background: #edf2f7;
    font-weight: 600;
}

.btn {
    padding: 8px 16px;
    border-radius: 4px;
    transition: background 0.2s;
}

.btn-primary {
    background: #3182ce;
    color: white;
}

.btn-primary:hover {
    background: #2b6cb0;
}

.btn-secondary {
    background: #e2e8f0;
    color: #4a5568;
}

.btn-secondary:hover {
    background: #cbd5e0;
}

.profile-section {
    display: flex;
    align-items: center;
    gap: 16px;
    margin-bottom: 20px;
}

.profile-img {
    width: 100px;
    height: 100px;
    object-fit: cover;
    border-radius: 50%;
}

.form-section {
    display: flex;
    flex-direction: column;
    gap: 12px;
}

.form-section div {
    display: flex;
    align-items: center;
    gap: 8px;
}

.form-section strong {
    width: 80px;
    text-align: right;
    font-weight: 600;
}

.form-section input {
    padding: 8px;
    border: 1px solid #e2e8f0;
    border-radius: 4px;
    width: 200px;
}

.action-buttons {
    display: flex;
    gap: 12px;
    margin-top: 20px;
    padding-left: 88px;
}

@media (max-width: 640px) {
    .form-section strong {
        width: auto;
        text-align: left;
    }

    .form-section input {
        width: 100%;
    }

    .form-section div {
        flex-direction: column;
        align-items: flex-start;
    }

    .action-buttons {
        padding-left: 0;
    }
}
</style>
</head>
<body>
    <div class="container">
        <!-- Personal Information -->
        <div class="card">
            <h2 class="text-xl font-semibold mb-4">개인 정보</h2>

            <form action="userUpdate" class="form-section" name="f" method="post">
                <div class="profile-section">
                    <c:set var="img" value="${fn:contains(sessionScope.login, 's') ? m.studentImg : m.professorImg}" />
                    
                    <img src="${path}/dist/assets/picture/${img}" id="pic" class="profile-img">
                    <input type="hidden" name="picture" value="${img}">
                    <button type="button" class="btn btn-secondary" onclick="win_upload()">이미지 변경</button>
                </div>
                <c:choose>
                    <c:when test="${fn:contains(sessionScope.login, 's')}">
                        <!-- Student Information -->
                        <div>
                            <strong>이름:</strong>
                            <input type="text" readonly="readonly" value="${m.studentName}" name="name">
                        </div>
                        <div>
                            <strong>학번:</strong>
                            <input type="text" readonly="readonly" value="${m.studentNum}" name="studentNum">
                        </div>
                        <div>
                            <strong>생년월일:</strong>
                            <input type="text" readonly="readonly" value="<fmt:formatDate value='${m.studentBirthday}' pattern='yyyy-MM-dd'/>" name="birthday">
                        </div>
                        <div>
                            <strong>학과:</strong>
                            <input type="text" readonly="readonly" value="${deptName}" name="deptName">
                        </div>
                    </c:when>
                    <c:when test="${fn:contains(sessionScope.login, 'p')}">
                        <!-- Professor Information -->
                        <div>
                            <strong>이름:</strong>
                            <input type="text" readonly="readonly" value="${m.professorName}" name="name">
                        </div>
                        <div>
                            <strong>생년월일:</strong>
                            <input type="text" readonly="readonly" value="<fmt:formatDate value='${m.professorBirthday}' pattern='yyyy-MM-dd'/>" name="birthday">
                        </div>
                        <div>
                            <strong>학과:</strong>
                            <input type="text" readonly="readonly" value="${deptName}" name="deptName">
                        </div>
                    </c:when>
                </c:choose>

                <div>
                    <c:choose>
                        <c:when test="${fn:contains(sessionScope.login, 's')}">
                            <strong>연락처:</strong>
                            <input type="text" readonly="readonly" value="${m.studentPhone}" name="phone" id="phone">
                            <button class="btn btn-secondary" type="button" onclick="javascript:updatePhone()">수정</button>
                        </c:when>
                        <c:when test="${fn:contains(sessionScope.login, 'p')}">
                            <strong>연락처:</strong>
                            <input type="text" readonly="readonly" value="${m.professorPhone}" name="phone" id="phone">
                            <button class="btn btn-secondary" type="button" onclick="javascript:updatePhone()">수정</button>
                        </c:when>
                    </c:choose>
                </div>
                <div>
                    <c:choose>
                        <c:when test="${fn:contains(sessionScope.login, 's')}">
                            <strong>이메일:</strong>
                            <input type="email" readonly="readonly" value="${m.studentEmail}" name="email" id="email">
                            <button class="btn btn-secondary" type="button" onclick="javascript:updateEmail()">수정</button>
                        </c:when>
                        <c:when test="${fn:contains(sessionScope.login, 'p')}">
                            <strong>이메일:</strong>
                            <input type="email" readonly="readonly" value="${m.professorEmail}" name="email" id="email">
                            <button class="btn btn-secondary" type="button" onclick="javascript:updateEmail()">수정</button>
                        </c:when>
                    </c:choose>
                </div>
                <div class="action-buttons">
                    <button class="btn btn-primary">수정완료</button>
                </div>
            </form>

			
            <div class="action-buttons">
                <button class="btn btn-primary" onclick="javascript:updatePw()">비밀번호 변경</button>
                <button class="btn btn-secondary text-red-600" onclick="javascript:deleteUser()">퇴학</button>
            </div>
        </div>
    </div>

    <script type="text/javascript">
    function updateEmail() {
        let op = "width=500,height=500,top=50,left=150";
        window.open("updateEmail", "", op);
    }

    function updatePhone() {
        let op = "width=500,height=500,top=50,left=150";
        window.open("updatePhone", "", op);
    }

    function win_upload() {
        let op = "width=500,height=500,top=50,left=150";
        window.open("registerImg", "", op);
    }
    function updatePw(){
		let id = "${sessionScope.login}";
		console.log(id);
    	let op = "width=500,height=500,top=50,left=150";
        window.open("pwUpdate?id="+id, "", op);
    }
    
    </script>
</body>
</html>