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
</style>
</head>

<main class="app-main">
    <div class="card">
    <h2 class="text-xl font-semibold mb-4">수강신청 현황</h2>
    <table class="table">
        <thead>
            <tr>
                <th>#</th>
                <th>교과목번호</th>
                <th>강의명</th>
                <th>분반</th>
                <th>교수</th>
                <th>학점</th>
                <th>요일/시간</th>
                <th>강의실</th>
                <th>취소신청</th> 
            </tr>
        </thead>
        <tbody id="courseBody">
        	<c:forEach var="reg" items="${registration}" varStatus="status">
        		<tr>
	        		<td>${status.count}</td>
	        		<td>${reg.courseId}</td>
	        		<td>${reg.courseName}</td>
	        		<td>1</td>
	        		<td>${reg.professorName}</td>
	        		<td>${reg.courseScore}</td>
	        		<td>${reg.timeSlot}</td>
	        		<td>${reg.courseTimeLoc}</td>
	        		<td>
	        			<button type="button" class="btn btn-primary cancel-course" 
	        					data-registration-id="${reg.registrationId}">
	        			수강신청취소
	        			</button>
	       			</td>
       			</tr>
        	</c:forEach> 
            
        </tbody>
    </table>
    <div class="mt-4">
        <p><strong>총 신청 학점:</strong> 6 학점</p>
        <button class="btn btn-primary view-courseTime">시간표 보기</button>
    </div>
    
    <table id="courseTime">
    <!-- AJAX로 시간표 구현 -->
    </table>
    
</div>
</main>

<script>
	$(document).ready(function() {

	    $(".cancel-course").click(function() {
	        var registrationId = $(this).data("registration-id");
	        if (confirm("정말 수강을 취소하시겠습니까?")) {
	            $.ajax({
	                url: "${path}/learning_support/viewCourse/deleteCourse",
	                type: "post",
	                data: { registrationId: registrationId},
	                dataType: "json",
	                success: function(response) {
	                    if (response.success) {
	                        location.reload(); // 새로고침
	                    } else {
	                        alert("취소 실패: " + response.message);
	                    }
	                },
	                error: function(xhr) {
	                    alert("서버 오류: " + xhr.status + " - " + xhr.responseText);
	                }
	            });
	        }
	    });
	    
	    $(".view-courseTime").click(function() {
	    	$.ajax({
                url: "${path}/learning_support/viewCourse/viewCourseTime",
                type: "get",
                dataType: "json",
                success: function(response) {
                    if (response.success) {
                        location.reload(); // 새로고침
                    } else {
                        alert("취소 실패: " + response.message);
                    }
                },
                error: function(xhr) {
                    alert("서버 오류: " + xhr.status + " - " + xhr.responseText);
                }
            });
	    })
	});
	


</script>
</body>
</html>