<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="path" value="${pageContext.request.contextPath}" scope="application" />

<!doctype html>
<html lang="en">
<head>
<title>메인화면</title>

<style>
ul.timeline::before {
    content: none !important;
}
/* 기본 배경 스타일 */
body {
    background-image: url('${path}/dist/assets/picture/backWon2.jpg');
    background-size: contain; /* 이미지 비율 유지하며 요소 내부에 맞춤 */
    background-position: center 25%; /* 필요에 따라 위치 조정 */
    background-repeat: no-repeat;
    background-attachment: fixed;
    min-height: 100vh;
    margin: 0;
    background-color: #f0f0f0;
}

/* 반응형 설정 (필요에 따라 조정) */
@media (max-width: 768px) {
    body {
        background-size: contain;
        background-position: center 15%;
    }
}

@media (min-width: 769px) and (max-width: 1200px) {
    body {
        background-size: contain;
        background-position: center 20%;
    }
}

@media (min-width: 1201px) {
    body {
        background-size: contain;
        background-position: center 25%;
    }
}

.app-content {
    margin-top: 350px; /* 이미지가 커짐에 따라 콘텐츠를 아래로 조정 */
}
</style>
</head>
<body class="layout-fixed sidebar-expand-lg bg-body-tertiary">
    <div class="app-wrapper">
        <div class="app-content">
            <div class="container">
                <div class="row">
                    <div class="col-md-6">
                        <div class="card card-primary card-outline">
                            <div class="card-header d-flex justify-content-between align-items-center">
                                <h5 class="card-title m-0">
                                    환영합니다,
                                    <c:if test="${not empty sessionScope.m}">
                                        <c:set var="user" value="${sessionScope.m}" />
                                        <strong>
                                            <c:choose>
                                                <c:when test="${user['class'].simpleName eq 'Student' and not empty user.studentName}">
                                                    ${user.studentName}님
                                                </c:when>
                                                <c:when test="${user['class'].simpleName eq 'Professor' and not empty user.professorName}">
                                                    ${user.professorName}님
                                                </c:when>
                                            </c:choose>
                                        </strong>
                                    </c:if>
                                </h5>
                            </div>
                            <div class="card-body">
                                <p>
                                    <strong>역할:</strong>
                                    <c:if test="${not empty sessionScope.m}">
                                        <c:choose>
                                            <c:when test="${user['class'].simpleName eq 'Student' and not empty user.studentName}">
                                                학생
                                            </c:when>
                                            <c:when test="${user['class'].simpleName eq 'Professor' and not empty user.professorName}">
                                                교수
                                            </c:when>
                                        </c:choose>
                                    </c:if>
                                </p>
                                <p><strong>현재 학기:</strong> 2025년 1학기</p>
                                <p>LDB 학사관리시스템에서 수강신청, 성적확인, 공지사항 등을 편리하게 이용하세요.</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="card card-info card-outline">
                            <div class="card-header">
                                <h5 class="card-title">빠른 액세스</h5>
                            </div>
                            <div class="card-body">
                                <div class="d-flex flex-wrap">
                                    <a href="${path}/learning_support/registerCourse" class="btn btn-primary m-1">수강신청</a>
                                    <a href="${path}/mypage/getCourseScores" class="btn btn-success m-1">성적확인</a>
                                    <a href="${path}/mypage/getCourseTimetable" class="btn btn-warning m-1">시간표조회</a>
                                    <a href="${path}/notice/getNotices" class="btn btn-info m-1">공지사항</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="card card-warning card-outline">
                            <div class="card-header">
                                <h5 class="card-title">최신 공지사항</h5>
                                <div class="card-tools">
                                    <a href="${path}/notice/getNotices" class="btn btn-tool">전체보기</a>
                                </div>
                            </div>
                            <div class="card-body">
                                <ul class="list-group">
                                    <c:forEach var="notice" items="${recentNotices}" begin="0" end="2">
                                        <li class="list-group-item">
                                            <a href="${path}/notice/getNoticeDetail?noticeId=${notice.noticeId}">${fn:escapeXml(notice.noticeTitle)}</a>
                                            <span class="float-end text-muted">
                                                <fmt:formatDate value="${notice.noticeCreatedAt}" pattern="yyyy-MM-dd" />
                                            </span>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="card card-success card-outline">
                            <div class="card-header">
                                <h5 class="card-title">학사 일정</h5>
                            </div>
                            <div class="card-body">
                                <ul class="timeline timeline-inverse">
                                    <li>
                                        <i class="bi bi-calendar-event bg-primary"></i>
                                        <div class="timeline-item">
                                            <span class="time"><i class="bi bi-clock"></i> 2025-03-01</span>
                                            <h3 class="timeline-header">2025년 1학기 개강</h3>
                                        </div>
                                    </li>
                                    <li>
                                        <i class="bi bi-calendar-event bg-warning"></i>
                                        <div class="timeline-item">
                                            <span class="time"><i class="bi bi-clock"></i> 2025-03-10 ~ 2025-03-15</span>
                                            <h3 class="timeline-header">수강정정 기간</h3>
                                        </div>
                                    </li>
                                    <li>
                                        <i class="bi bi-calendar-event bg-danger"></i>
                                        <div class="timeline-item">
                                            <span class="time"><i class="bi bi-clock"></i> 2025-06-20 ~ 2025-06-30</span>
                                            <h3 class="timeline-header">기말고사</h3>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-12">
                        <div class="card card-secondary card-outline">
                            <div class="card-header">
                                <h5 class="card-title">최근 활동</h5>
                            </div>
                            <div class="card-body">
                                <table class="table table-bordered">
                                    <thead>
                                        <tr>
                                            <th>활동</th>
                                            <th>날짜</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="activity" items="${recentActivities}" begin="0" end="4">
                                            <tr>
                                                <td>${fn:escapeXml(activity.description)}</td>
                                                <td><fmt:formatDate value="${activity.date}" pattern="yyyy-MM-dd HH:mm" /></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>