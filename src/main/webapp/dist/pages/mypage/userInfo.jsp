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
    <title>Academic Management System</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Noto Sans KR', sans-serif;
            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
            margin: 0;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: flex-start;
        }

        .container {
            max-width: 900px;
            margin: 40px auto;
            padding: 0 20px;
        }

        .card {
            background: #ffffff;
            border-radius: 16px;
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
            padding: 32px;
            margin-bottom: 40px;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }

        .card:hover {
            transform: translateY(-4px);
            box-shadow: 0 12px 32px rgba(0, 0, 0, 0.15);
        }

        .card h2 {
            font-size: 1.75rem;
            font-weight: 700;
            color: #2d3748;
            margin-bottom: 24px;
            border-bottom: 2px solid #e2e8f0;
            padding-bottom: 8px;
        }

        .profile-section {
            display: flex;
            align-items: center;
            gap: 24px;
            margin-bottom: 32px;
            background: #f7fafc;
            padding: 16px;
            border-radius: 12px;
        }

        .profile-img {
            width: 120px;
            height: 120px;
            object-fit: cover;
            border-radius: 50%;
            border: 4px solid #e2e8f0;
            transition: border-color 0.3s ease;
        }

        .profile-img:hover {
            border-color: #3182ce;
        }

        .form-section {
            display: grid;
            gap: 16px;
        }

        .form-section div {
            display: flex;
            align-items: center;
            gap: 12px;
            background: #edf2f7;
            padding: 12px;
            border-radius: 8px;
            transition: background 0.2s ease;
        }

        .form-section div:hover {
            background: #e2e8f0;
        }

        .form-section strong {
            width: 100px;
            text-align: right;
            font-weight: 600;
            color: #4a5568;
        }

        .form-section input {
            padding: 10px;
            border: 1px solid #cbd5e0;
            border-radius: 6px;
            width: 250px;
            background: #ffffff;
            font-size: 0.95rem;
            color: #2d3748;
            transition: border-color 0.3s ease;
        }

        .form-section input:focus {
            outline: none;
            border-color: #3182ce;
            box-shadow: 0 0 0 3px rgba(49, 130, 206, 0.1);
        }

        .form-section input[readonly] {
            background: #f7fafc;
            cursor: not-allowed;
        }

        .btn {
            padding: 10px 20px;
            border-radius: 6px;
            font-weight: 500;
            font-size: 0.95rem;
            transition: all 0.3s ease;
            cursor: pointer;
        }

        .btn-primary {
            background: #3182ce;
            color: white;
            border: none;
        }

        .btn-primary:hover {
            background: #2b6cb0;
            transform: translateY(-2px);
        }

        .btn-secondary {
            background: #e2e8f0;
            color: #4a5568;
            border: none;
        }

        .btn-secondary:hover {
            background: #cbd5e0;
            transform: translateY(-2px);
        }

        .btn-danger {
            background: #e53e3e;
            color: white;
            border: none;
        }

        .btn-danger:hover {
            background: #c53030;
            transform: translateY(-2px);
        }

        .action-buttons {
            display: flex;
            gap: 16px;
            margin-top: 24px;
            padding-left: 112px;
        }

        @media (max-width: 768px) {
            .container {
                margin: 20px auto;
                padding: 0 16px;
            }

            .card {
                padding: 24px;
            }

            .profile-section {
                flex-direction: column;
                align-items: flex-start;
            }

            .form-section div {
                flex-direction: column;
                align-items: flex-start;
            }

            .form-section strong {
                width: auto;
                text-align: left;
            }

            .form-section input {
                width: 100%;
            }

            .action-buttons {
                padding-left: 0;
                flex-direction: column;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- Personal Information -->
        <div class="card">
            <h2>개인 정보</h2>

            <form action="userUpdate" class="form-section" name="f" method="post">
                <div class="profile-section">
                    <%--c:set var="img" value="${fn:contains(sessionScope.login, 's') ? m.studentImg : m.professorImg}" /> --%>
                    <img src="${path}/dist/assets/picture/${m.img}" id="pic" class="profile-img" alt="Profile Image">
                    <input type="hidden" name="picture" value="${m.img}">
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
                            <button class="btn btn-secondary" type="button" onclick="updatePhone()">수정</button>
                        </c:when>
                        <c:when test="${fn:contains(sessionScope.login, 'p')}">
                            <strong>연락처:</strong>
                            <input type="text" readonly="readonly" value="${m.professorPhone}" name="phone" id="phone">
                            <button class="btn btn-secondary" type="button" onclick="updatePhone()">수정</button>
                        </c:when>
                    </c:choose>
                </div>
                <div>
                    <c:choose>
                        <c:when test="${fn:contains(sessionScope.login, 's')}">
                            <strong>이메일:</strong>
                            <input type="email" readonly="readonly" value="${m.studentEmail}" name="email" id="email">
                            <button class="btn btn-secondary" type="button" onclick="updateEmail()">수정</button>
                        </c:when>
                        <c:when test="${fn:contains(sessionScope.login, 'p')}">
                            <strong>이메일:</strong>
                            <input type="email" readonly="readonly" value="${m.professorEmail}" name="email" id="email">
                            <button class="btn btn-secondary" type="button" onclick="updateEmail()">수정</button>
                        </c:when>
                    </c:choose>
                </div>
                <div class="action-buttons">
                    <button class="btn btn-primary" type="submit">수정완료</button>
                </div>
            </form>

            <div class="action-buttons">
                <button class="btn btn-primary" onclick="updatePw()">비밀번호 변경</button>
                <button class="btn btn-danger" onclick="deleteUser()">퇴학</button>
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

        function updatePw() {
            let id = "${sessionScope.login}";
            console.log(id);
            let op = "width=500,height=500,top=50,left=150";
            window.open("pwUpdate?id=" + id, "", op);
        }

        function deleteUser() {
            if (confirm("정말 퇴학하시겠습니까? 이 작업은 되돌릴 수 없습니다.")) {
                window.location.href = "deleteUser";
            }
        }
    </script>
</body>
</html>