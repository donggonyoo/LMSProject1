<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="path" value="${pageContext.request.contextPath}"
	scope="application" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>성적조회</title>
<script src="https://cdn.tailwindcss.com"></script>
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
<body>
	<!-- Grade Inquiry -->
	<div class="card">
		<h2 class="text-xl font-semibold mb-4">성적 확인</h2>
		<div class="flex items-center mb-4">
			<select class="border rounded p-2 mr-2">
				<option>전체</option>
				<option>2024 가을</option>
				<option>2025 봄</option>
			</select>
			<button class="btn btn-primary">조회</button>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th>#</th>
					<th>과목번호(courseId)</th>
					<th>과목명(courseName)</th>
					<th>담당교수(professorName)</th>
					<!-- id와 조인해서 name뽑아 -->
					<th>학점(courseScore)</th>
					<th>중간점수(scoreMid)</th>
					<th>기말점수(scoreFinal)</th>
					<th>취득 점수(scoreTotal)</th>
					<th>평가(scoreGrade)</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${score}" var="sc" varStatus="a">
			<tr>
					<td>${a.count}</td>
					<td>${sc.courseId}</td>
					<td>${sc.courseName}</td>
					<td>${sc.professorName}</td>
					<td>${sc.courseScore}</td>
					<td>${sc.scoreMid}</td>
					<td>${sc.scoreFinal}</td>
					<td>${sc.scoreTotal}</td>
					<td>${sc.scoreGrade}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<div class="mt-4">
			<p>
				<strong>해당 학기 총 이수 학점:</strong> 6 학점
			</p>
			<p>
				<strong>해당 학기 평균 학점 (GPA):</strong> 3.75
			</p>
			<p>
				<strong>총 이수 학점:</strong> 60 학점
			</p>
			<p>
				<strong>전체 평균 학점 (GPA):</strong> 3.50
			</p>
		</div>
	</div>

</body>
</html>