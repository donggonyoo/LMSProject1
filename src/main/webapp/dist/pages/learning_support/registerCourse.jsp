<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="path" value="${pageContext.request.contextPath}" scope="application" />

<!doctype html>
<html lang="en">
<head>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="https://cdn.tailwindcss.com"></script>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>개인정보</title>
<style>
body {
    font-family: 'Noto Sans KR', sans-serif;
    background-color: #f7fafc;
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
.pagination button {
    padding: 8px 12px;
    margin: 0 4px;
    border: 1px solid #e2e8f0;
    border-radius: 4px;
    background: #fff;
    color: #3182ce;
    cursor: pointer;
}
.pagination button.active {
    background: #3182ce;
    color: white;
    border-color: #3182ce;
}
.pagination button:disabled {
    color: #a0aec0;
    cursor: not-allowed;
}
</style>
</head>
<body>
<main class="app-main">
    <div class="card">
        <h2 class="text-xl font-semibold mb-4">수강신청</h2>

        <!-- 검색 폼 -->
        <form id="searchForm" class="grid grid-cols-2 gap-4 mb-4">
            <div>
                <label>대학구분</label>
                <select name="college" id="collegeSelect" class="border rounded p-2 w-full" onchange="updateDepartments()">
                    <option value="">전체</option>
                </select>
            </div>
            <div>
                <label>학과</label>
                <select name="deptId" id="deptSelect" class="border rounded p-2 w-full">
                    <option value="">소속대학을 먼저 선택해주세요.</option>

                </select>
            </div>
            <div>
                <label>교과목번호</label>
                <input type="text" name="courseId" id="courseId" class="border rounded p-2 w-full" placeholder="교과목번호 입력">
            </div>
            <div>
                <label>교과목명</label>
                <input type="text" name="courseName" id="courseName" class="border rounded p-2 w-full" placeholder="교과목명 입력">
            </div>
            <div>
                <button type="button" id="searchButton" class="btn btn-primary">검색</button>
            </div>
        </form>

        <!-- 개설 과목 목록 -->
        <p>
            <strong>전체 개설과목:</strong> <span id="courseCount">0</span>건
        </p>
        <table class="table" id="courseTable">
            <thead>
                <tr>
                    <th>상태</th>
                    <th>이수구분</th>
                    <th>교과목번호</th>
                    <th>교과목명</th>
                    <th>교수명</th>
                    <th>학점</th>
                    <th>시간</th>
                    <th>정원</th>
                    <th>강의계획서</th>
                </tr>
            </thead>
            <tbody id="courseBody">
                <!-- AJAX로 동적으로 채움 -->
            </tbody>
        </table>
        
		<!-- 추가: 페이징 네비게이션 영역 -->
        <div class="pagination flex justify-center mt-4" id="pagination">
            <!-- 동적으로 페이지 버튼 생성 -->
        </div>
        
        <!-- 신청 내역 -->
        <h3 class="text-lg font-semibold mt-4">신청내역</h3>
        <table class="table" id="registrationTable">
            <thead>
                <tr>
                    <th>이수구분</th>
                    <th>교과목번호</th>
                    <th>교과목명</th>
                    <th>학점</th>
                    <th>담당교수</th>
                    <th>시간</th>
                    <th>삭제</th>
                </tr>
            </thead>
            <tbody id="registrationBody">
                <!-- AJAX로 동적으로 채움 -->
            </tbody>
        </table>
    </div>
</main>

<script>
	//페이징 처리 준비
	var currentPage = 1;
	var pageSize = 10;
	
	$(document).ready(function() {
		
		
	    // 대학 목록 로드
	    $.ajax({
	        url: "${path}/learning_support/colleges",
	        type: "get",
	        dataType: "json",
	        success: function(data) {
	            var $collegeSelect = $("#collegeSelect");
	            $collegeSelect.empty();
	            $collegeSelect.append('<option value="">전체</option>');
	            $.each(data, function(i, item) {
	                $collegeSelect.append($("<option>").val(item).text(item));
	            });
	        },
	        error: function(e) {
	            alert("SERVER_ERROR: " + e.status);
	        }
	    });
	
	    // 초기 강의 목록 및 신청 내역 로드
	    loadCourses();
	    loadRegistrations();
	
	    // 검색 버튼 클릭 이벤트
	    $("#searchButton").on("click", function() {
	    	currentPage = 1;
	        loadCourses();
	    });
	    
	    $("#courseName").on("keydown", function(e) {
			if (e.keyCode == 13) {
				$("#searchButton").click();
				e.preventDefault();
			}
	    });
	
	    // 추가 버튼 클릭 이벤트 (AJAX로 처리)
	    $(document).on("click", ".add-course", function() {
	        var courseId = $(this).closest("form").find("input[name='courseId']").val();
	        var professorId = $(this).closest("form").find("input[name='professorId']").val();
	        addCourse(courseId, professorId);
	    });
	
	    // 삭제 버튼 클릭 이벤트 (AJAX로 처리)
	    $(document).on("click", ".delete-registration", function() {
	        var registrationId = $(this).closest("form").find("input[name='registrationId']").val();
	     	// 버튼이 속한 tr에서 courseId가 있는 td(두 번째 td) 가져오기
	        var courseId = $(this).closest("tr").find("td:eq(1)").text();
	     	
	        deleteCourse(registrationId, courseId);
	    });
	});
	
	// 학과 동적 업데이트 함수
	function updateDepartments() {
		var college = $('#collegeSelect').val();
	    
	    $.ajax({
	        url: '${path}/learning_support/departments',
	        method: 'GET',
	        data: { college: college },
	        dataType: "json",
	        success: function(data) {
	            var $deptSelect = $('#deptSelect');
	            $deptSelect.empty();
	            $deptSelect.append('<option value="">전체</option>');
	            $.each(data, function(idx, dept) {
	                $deptSelect.append($("<option>").val(dept.deptId).text(dept.deptName));
	            });
	        },
	        error: function(xhr) {
	            alert('학과 목록을 불러오지 못했습니다: ' + xhr.responseText);
	        }
	    });
	}
	
	// 강의 목록 로드
	function loadCourses() {
	    var params = {
	    	college: $('#collegeSelect').val(),
	        deptId: $("#deptSelect").val(),
	        courseId: $("#courseId").val(),
	        courseName: $("#courseName").val(),
	        currentPage: currentPage,
	        itemsPerPage: pageSize,
	    };
	    $.ajax({
	        url: "${path}/learning_support/searchCourse",
	        type: "get",
	        data: params,
	        dataType: "json",
	        success: function(data) {
				var courses = data.courses;
				var pagination = data.pagination;
				console.log('data', data);
	            var $body = $("#courseBody");
	            $body.empty();
	            $.each(courses, function(i, course) {
	                var row = $("<tr>").append(
	                    $("<td>").append(
	                        $("<form>").append(
	                            $("<input>").attr({type: "hidden", name: "courseId", value: course.courseId}),
	                            $("<input>").attr({type: "hidden", name: "professorId", value: course.professorId}),
	                            $("<button>").attr({type: "button"}).addClass("btn btn-primary add-course").text("추가")
	                       	)
	                    ),
	                    $("<td>").text(course.creditCategory),
	                    $("<td>").text(course.courseId),
	                    $("<td>").text(course.courseName),
	                    $("<td>").text(course.professorName),
	                    $("<td>").text(course.courseScore),
	                    $("<td>").text(course.timeSlot),
	                    $("<td>").text(course.courseCurrentEnrollment + ' / ' + course.courseMaxCnt),
	                    $("<td>").append(
	                        course.coursePlan ? $("<a>").attr({href: course.coursePlan, target: "_blank"}).addClass("btn btn-secondary").text("미리보기") : $("<span>").text("-")
	                    )
	                );
	                $body.append(row);
	                
	                // 페이징 처리
	                renderPagination(pagination.currentPage, pagination.totalPages);
	            });
	            $("#courseCount").text(courses.length);
	        },
	        error: function(xhr) {
	            alert("강의 목록을 불러오지 못했습니다: " + xhr.responseText);
	        }
	    });
	}
	
	//페이징 처리
	function renderPagination(current, total) {
		var paging = $("#pagination");
		paging.empty();
		
		// 이전페이지btn
		paging.append(
			$('<button>').text('이전').prop('disabled', current == 1)	.on('click', function() {
			if (current > 1) {
				currentPage = current - 1;
				loadCourses();
			}
			})	
		);
		
		// 페이지번호btn
		for (var i=1; i<=total; i++) {
			(function(page) {
				paging.append(
					$('<button>').text(page).toggleClass('active', page == current).on('click', function() {
						currentPage = page;
						loadCourses();
					})		
				)
			
			})(i);
		}
	
		// 다음페이지btn
		paging.append(
			$('<button>').text('다음').prop('disabled', current == total).on('click', function() {
				if (current < total) {
					currentPage = current + 1;
					loadCourses();
				}
			
			})		
		)
		
	}
	
	// 과목 추가
	function addCourse(courseId, professorId) {
	    $.ajax({
	        url: "${path}/learning_support/addCourse",
	        type: "get",
	        data: { courseId: courseId, professorId: professorId },
	        dataType: "json",
	        success: function(data) {
				if (data.errorMsg && data.errorMsg.indexOf('full') !== -1) {
					alert('해당강의는 정원이 초과하였습니다.');
				}
	        	loadRegistrations(); // 신청 내역 갱신
	            loadCourses(); // 강의 목록 갱신 (추가된 항목 제외)
	        },
	        error: function(xhr) {
	            alert("과목 추가에 실패했습니다: " + xhr.responseText);
	        }
	    });
	}
	
	
	
	// 신청 내역 로드
	function loadRegistrations() {
	    $.ajax({
	        url: "${path}/learning_support/searchRegistrationCourses",
	        type: "get",
	        dataType: "json",
	        success: function(data) {
	            var $body = $("#registrationBody");
	            $body.empty();
	            $.each(data, function(i, reg) {
	                var row = $("<tr>").append(
	                    $("<td>").text(reg.creditCategory),
	                    $("<td>").text(reg.courseId),
	                    $("<td>").text(reg.courseName),
	                    $("<td>").text(reg.courseScore),
	                    $("<td>").text(reg.professorName),
	                    $("<td>").text(reg.timeSlot),
	                    $("<td>").append(
	                        $("<form>").append(
	                            $("<input>").attr({type: "hidden", name: "registrationId", value: reg.registrationId}),
	                            $("<button>").attr({type: "button"}).addClass("btn btn-secondary delete-registration text-red-600").text("삭제")
	                        )
	                    )
	                );
	                $body.append(row);
	            });
	        },
	        error: function(xhr) {
	            alert("신청 내역을 불러오지 못했습니다: " + xhr.responseText);
	        }
	    });
	}
	
	// 신청 삭제
	function deleteCourse(registrationId, courseId) {
	    $.ajax({
	        url: "${path}/learning_support/deleteCourse",
	        type: "get",
	        data: { registrationId: registrationId, courseId: courseId },
	        dataType: "json",
	        success: function(data) {
	        	loadRegistrations(); // 신청 내역 갱신
	            loadCourses(); // 강의 목록 갱신
	        },
	        error: function(xhr) {
	            alert("과목 삭제에 실패했습니다: " + xhr.responseText);
	        }
	    });
	}
</script>
</body>
</html>