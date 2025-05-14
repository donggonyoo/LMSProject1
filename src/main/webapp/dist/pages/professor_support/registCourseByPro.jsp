<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="path" value="${pageContext.request.contextPath}" scope="application" />


    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>강의등록(교수지원)</title>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<style>
    body {
        font-family: 'Noto Sans KR', sans-serif;
        background-color: #f8f9fa;
    }
    .content {
        padding: 20px;
    }
    .card {
        border: none;
        box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
        margin-bottom: 20px;
    }
    .btn-custom {
        background-color: #dc3545;
        color: #ffffff;
        border: none;
    }
    .btn-custom:hover {
        background-color: #c82333;
    }
    .table th {
        background-color: #f1f3f5;
    }
    /* 추가 스타일 */
    .form-label-custom {
        font-weight: bold;
        margin-bottom: 0.3rem;
    }
    .input-group-text-custom {
        background-color: #e9ecef;
        border: 1px solid #ced4da;
    }
    .form-select-sm, .form-control-sm {
        padding: 0.375rem 0.75rem;
        font-size: 0.875rem;
        border-radius: 0.2rem;
        flex: 1;
        min-width: 0;
    }
    #courseTime .time-input-group {
        display: flex;
        align-items: center;
        flex-wrap: wrap;
    }
    #courseTime .time-input-group > * {
        margin-right: 0.5rem;
    }
    #courseTime .time-input-group > select {
        flex-basis: 30%;
    }
    #courseTime .time-input-group > .input-group {
        flex-basis: 30%;
    }
    #courseTime .time-input-group > .mx-2 {
        flex-basis: auto;
    }
    #courseTime .time-input-group > *:last-child {
        margin-right: 0;
    }
    .input-group-sm > .form-control,
    .input-group-sm > .form-select,
    .input-group-sm > .input-group-text {
        height: calc(1.5em + 0.5rem + 2px);
        padding: 0.25rem 0.5rem;
        font-size: 0.875rem;
        border-radius: 0.2rem;
    }
    .time-instruction {
        font-size: 0.875rem;
        color: #6c757d; /* 기본 텍스트 색상 */
    }
    .mb-3.row .col-sm-10 .time-instruction.error { 
    	color: red !important;
	}
    .invalid-feedback { /* 이제 display: none; 은 JS에서 처리 */
        color: red;
        font-size: 0.875rem;
        margin-top: 0.25rem;
    }
    /* 작은 화면에서 아래로 배열 */
    @media (max-width: 576px) {
        #courseTime .time-input-group {
            flex-direction: column;
            align-items: stretch;
        }
        #courseTime .time-input-group > * {
            margin-right: 0;
            margin-bottom: 0.5rem;
        }
    }
</style>
</head>
<body>
    <div class="content">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2><i class="bi bi-pencil-square mr-2"></i> 강의 등록</h2>
        </div>

        <div class="card p-4">
            <h5 class="mb-3">강의 정보 입력</h5>
            <div class="mb-3 row">
                <label for="majorName" class="col-sm-2 col-form-label-custom">전공명</label>
                <div class="col-sm-10">
                    <select class="form-select form-select-sm" id="majorName">
                    	<option value="" selected>전공을 선택하세요</option>
                    	<c:forEach var="department" items="${departments}" varStatus="status">
                        <option value="${department.deptId}">${department.deptName}</option>
                        </c:forEach>
                        </select>
                </div>
            </div>
            <div class="mb-3 row">
                <label for="professorName" class="col-sm-2 col-form-label-custom">교수명</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control form-control-sm" 
                    	id="professorName" value="" placeholder="피카츄(예)">
                </div>
            </div>
            <div class="mb-3 row">
                <label class="col-sm-2 col-form-label-custom">이수구분</label>
                <div class="col-sm-10">
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="courseType" id="majorRequired" value="MajorRequired" checked>
                        <label class="form-check-label" for="majorRequired">전공필수</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="courseType" id="MajorElective" value="MajorElective">
                        <label class="form-check-label" for="majorSelective">전공선택</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="courseType" id="liberalArts" value="liberalArts">
                        <label class="form-check-label" for="liberalArts">교양</label>
                    </div>
                </div>
            </div>
            <div class="mb-3 row align-items-center" id="courseTime">
                <label for="courseDay" class="col-sm-2 col-form-label-custom">강의시간</label>
                <div class="col-sm-10">
                    <div class="d-flex align-items-center time-input-group">
                        <select class="form-select form-select-sm" id="courseDay">
                            <option selected>요일 선택</option>
                            <option value="월">월요일</option>
                            <option value="화">화요일</option>
                            <option value="수">수요일</option>
                            <option value="목">목요일</option>
                            <option value="금">금요일</option>
                        </select>
                        <div class="input-group input-group-sm">
                            <input type="number" class="form-control form-control-sm start-time-hour" 
                         			id="startTimeHour" min="0" max="23" placeholder="시작시간 입력">
                            <div class="input-group-text">:00</div>
                        </div>
                        <span class="mx-2">~</span>
                        <div class="input-group input-group-sm">
                            <input type="number" class="form-control form-control-sm end-time-hour" 
                            		id="endTimeHour" min="0" max="23" placeholder="종료시간 입력">
                            <div class="input-group-text">:50</div>
                        </div>
                    </div>
                    <small class="form-text text-muted time-instruction">요일과 시작/종료 시간을 선택하세요 (0~23시).</small>
                    <div class="invalid-feedback time-error" style="display: none;">정수를 0~23 사이로 입력하세요.</div>
                    <div id="courseTimeList" class="mt-2">
                        </div>
                </div>
            </div>
            <div class="mb-3 row">
                <label for="credits" class="col-sm-2 col-form-label-custom">학점</label>
                <div class="col-sm-10">
                    <input type="number" class="form-control form-control-sm" id="credits" min="1" max="6" value="3">
                    <small class="form-text text-muted">1점에서 6점 사이의 학점을 입력하세요.</small>
                </div>
            </div>
        </div>

        <div class="card p-4">
            <h5 class="mb-3">강의 설명</h5>
            <div class="mb-3">
                <textarea class="form-control form-control-sm" rows="5"></textarea>
                <small class="form-text text-muted">강의 내용을 상세하게 설명해주세요.</small>
            </div>
            <div class="d-flex justify-content-end">
                <button class="btn btn-secondary btn-sm mr-2">리셋 <i class="bi bi-arrow-counterclockwise"></i></button>
                <button class="btn btn-custom btn-sm">등록 <i class="bi bi-check-circle"></i></button>
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        $(document).ready(function() {
            $('#courseTimeList').empty();

            // 시간 입력 필드에 이벤트 리스너 추가 (focusout 시 검증)
            $('.start-time-hour, .end-time-hour').on('focusout', function() {
                const inputField = $(this);
                const instructionText = $(this).closest('.mb-3.row.align-items-center').find('.time-instruction');

                if (!/^\d+$/.test(inputField.val()) || parseInt(inputField.val()) < 0 || parseInt(inputField.val()) > 23) {
                    instructionText.text('정수를 0~23 사이로 입력하세요.');
                    instructionText.addClass('error');
                    inputField.addClass('is-invalid');
                } else {
                    instructionText.text('요일과 시작/종료 시간을 선택하세요 (0~23시).');
                    instructionText.removeClass('error');
                    inputField.removeClass('is-invalid');
                }
            });
////////////////////////////////////////////////////////////
//기능 구현해야됨. 현재 html에 form태그 없음.
            // (선택 사항) 폼 제출 시 최종 검증
            $('form').on('submit', function(event) {
                let isValid = true;
                $('.start-time-hour, .end-time-hour').each(function() {
                    const inputField = $(this);
                    const instructionText = $(this).closest('.mb-3.row.align-items-center').find('.time-instruction');
                    if (!/^\d+$/.test(inputField.val()) || parseInt(inputField.val()) < 0 || parseInt(inputField.val()) > 23) {
                        instructionText.text('정수를 0~23 사이로 입력하세요.');
                        instructionText.addClass('error');
                        inputField.addClass('is-invalid');
                        isValid = false;
                    }
                });

                if (!isValid) {
                    event.preventDefault();
                    alert('강의 시간을 올바르게 입력해주세요.');
                    
                    var majorName = $("#majorName").val();
                    var professorName = $("#professorName").val();
                    var creditCategory = $("input[name='courseType']:checked").val();
                    // 적다 말았음 세팅하고 html에 form태그 만들어야됨
                    var params = {
						majorName: majorName,
						professorName: professorName,
						creditCategory: creditCategory,
						
                    }
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
            });
        });
    </script>
</body>
</html>