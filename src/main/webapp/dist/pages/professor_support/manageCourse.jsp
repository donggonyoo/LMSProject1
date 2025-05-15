<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="path" value="${pageContext.request.contextPath}" scope="application"/>

<div class="container">
    <!-- 제목 및 검색 영역 -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2><i class="bi bi-book me-2"></i> 강의관리</h2>
        <div class="d-flex align-items-center gap-2">
            <!-- 검색 -->
            <div class="search-bar">
                <input type="text" class="form-control form-control-sm" id="searchInput" placeholder="강의명 검색..." style="width: 200px;">
            </div>
            <button class="btn btn-primary btn-sm" id="searchBtn"><i class="bi bi-search"></i></button>
        </div>
    </div>

    <div class="card">
        <div class="mb-3 text-end">
            <a href="${path}/professor_support/registCourse" class="btn btn-primary">새 강의 등록</a>
        </div>
        <table class="table table-hover">
            <thead>
                <tr>
                    <th>
                        강의명
                        <a href="#" class="sort-link" data-sort="courseName">
                            <i class="bi bi-arrow-down-up ms-1"></i>
                        </a>
                    </th>
                    <th>강의기간</th>
                    <th>전공여부</th>
                    <th>강의시간</th>
                    <th>수강인원</th>
                    <th>
                        학점
                        <a href="#" class="sort-link" data-sort="courseScore">
                            <i class="bi bi-arrow-down-up ms-1"></i>
                        </a>
                    </th>
                    <th>상태</th>
                    <th>조회 및 수정</th>
                    <th>삭제</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="course" items="${courses}" varStatus="status">
                    <tr>
                        <td>
                            <span data-bs-toggle="tooltip" title="${course.coursePlan}">
                                <c:out value="${course.courseName}"/>
                            </span>
                        </td>
                        <td>${course.coursePeriod}</td>
                        <td>
                            <c:choose>
                                <c:when test="${course.creditCategory == 'MajorRequired' || course.creditCategory == 'MajorElective'}">
                                    예
                                </c:when>
                                <c:otherwise>
                                    아니오
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${course.courseTimeYoil} ${course.courseTimeStart} - ${course.courseTimeEnd}</td>
                        <td>${course.currentEnrollment} / ${course.courseMaxCnt}</td>
                        <td>${course.courseScore}</td>
                        <td>
                            <span class="badge ${course.courseStatus == 'OPEN' ? 'text-bg-success' : 'text-bg-danger'}">
                                ${course.courseStatus == 'OPEN' ? '개설됨' : '종료됨'}
                            </span>
                        </td>
                        <td>
                            <button class="btn btn-primary btn-sm view-details" data-course-id="${course.courseId}"
                                    data-bs-toggle="modal" data-bs-target="#courseModal">
                                <i class="bi bi-eye"></i> 조회
                            </button>
                            <a href="${path}/courseEdit?courseId=${course.courseId}" class="btn btn-primary btn-sm ms-1">
                                <i class="bi bi-pencil"></i> 수정
                            </a>
                        </td>
                        <td>
                            <a href="${path}/courseDelete?courseId=${course.courseId}" class="btn btn-secondary btn-sm" 
                               onclick="return confirm('정말 삭제하시겠습니까?');">
                                <i class="bi bi-trash"></i> 삭제
                            </a>
                            <c:if test="${course.courseStatus == 'OPEN'}">
                                <button class="btn btn-danger btn-sm ms-1 close-course" data-course-id="${course.courseId}">
                                    <i class="bi bi-x-circle"></i> 종료
                                </button>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty courses}">
                    <tr>
                        <td colspan="9" class="text-center">등록된 강의가 없습니다.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>

        <!-- Pagination -->
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <c:if test="${currentPage > 1}">
                    <li class="page-item">
                        <a class="page-link" href="${path}/professor_support/manage/manageCourse?page=${currentPage - 1}&search=${param.search}">이전</a>
                    </li>
                </c:if>
                <c:forEach begin="1" end="${totalPages}" var="page">
                    <li class="page-item ${currentPage == page ? 'active' : ''}">
                        <a class="page-link" href="${path}/professor_support/manage/manageCourse?page=${page}&search=${param.search}">${page}</a>
                    </li>
                </c:forEach>
                <c:if test="${currentPage < totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="${path}/professor_support/manage/manageCourse?page=${currentPage + 1}&search=${param.search}">다음</a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</div>

<!-- 상세 보기 모달 -->
<div class="modal fade" id="courseModal" tabindex="-1" aria-labelledby="courseModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="courseModalLabel">강의 상세 정보</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p><strong>강의명:</strong> <span id="modalCourseName"></span></p>
                <p><strong>강의기간:</strong> <span id="modalCoursePeriod"></span></p>
                <p><strong>전공여부:</strong> <span id="modalCreditCategory"></span></p>
                <p><strong>강의시간:</strong> <span id="modalCourseTime"></span></p>
                <p><strong>수강인원:</strong> <span id="modalEnrollment"></span></p>
                <p><strong>학점:</strong> <span id="modalCourseScore"></span></p>
                <p><strong>상태:</strong> <span id="modalCourseStatus"></span></p>
                <p><strong>강의 계획:</strong> <span id="modalCoursePlan"></span></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
            </div>
        </div>
    </div>
</div>

<script>
    // 검색 기능
    $("#searchBtn").click(function() {
        var keyword = $("#searchInput").val();
        if (keyword) {
            window.location.href = '${path}/professor_support/manage/manageCourse?search=' + encodeURIComponent(keyword);
        }
    });

    // Enter 키로 검색
    $("#searchInput").keypress(function(e) {
        if (e.which === 13) { // Enter 키 코드
            $("#searchBtn").click();
        }
    });

    // 정렬 기능
    $(".sort-link").click(function(e) {
        e.preventDefault();
        var sortField = $(this).attr("data-sort");// courseName or courseScore
        var currentUrl = new URL(window.location.href);
        var currentSort = currentUrl.searchParams.get("sort") || "";
        var newSort = currentSort === sortField ? sortField + "-desc" : sortField;
        currentUrl.searchParams.set("sort", newSort);
        window.location.href = currentUrl.toString();
    });

    // 모달 데이터 채우기
    $(".view-details").click(function() {
        var courseId = $(this).attr("data-course-id");
        var row = $(this).closest("tr");
        var courseName = row.find("td:eq(0)").text().trim();
        var coursePeriod = row.find("td:eq(1)").text().trim();
        var creditCategory = row.find("td:eq(2)").text().trim();
        var courseTime = row.find("td:eq(3)").text().trim();
        var enrollment = row.find("td:eq(4)").text().trim();
        var courseScore = row.find("td:eq(5)").text().trim();
        var courseStatus = row.find("td:eq(6)").text().trim();
        var coursePlan = row.find("td:eq(0) span").attr("title") || "없음";

        $("#modalCourseName").text(courseName);
        $("#modalCoursePeriod").text(coursePeriod);
        $("#modalCreditCategory").text(creditCategory);
        $("#modalCourseTime").text(courseTime);
        $("#modalEnrollment").text(enrollment);
        $("#modalCourseScore").text(courseScore);
        $("#modalCourseStatus").text(courseStatus);
        $("#modalCoursePlan").text(coursePlan);
    });

    // 강의 종료 처리
    $(".close-course").click(function() {
        var courseId = $(this).attr("data-course-id");
        if (confirm("이 강의를 종료하시겠습니까?")) {
            $.ajax({
                url: '${path}/courseClose?courseId=' + courseId,
                type: 'POST',
                success: function(response) {
                    if (response === "success") {
                        window.location.reload();
                    } else {
                        alert("강의 종료에 실패했습니다.");
                    }
                },
                error: function(xhr, status, error) {
                    console.error("Error:", error);
                    alert("강의 종료 중 오류가 발생했습니다.");
                }
            });
        }
    });

    // 툴팁 활성화
    $(document).ready(function() {
        $("[data-bs-toggle='tooltip']").tooltip();
    });
</script>