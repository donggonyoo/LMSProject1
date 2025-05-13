<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
$(document).ready(function() {
    $(".cancel-course").click(function() {
        var registrationId = $(this).data("registration-id");
        var courseId = $(this).data("course-id");
        
        if (confirm("정말 수강을 취소하시겠습니까?")) {
            $.ajax({
                url: "${path}/learning_support/viewCourse/deleteCourse",
                type: "post",
                data: { 
                    registrationId: registrationId,
                    courseId: courseId    
                },
                dataType: "json",
                success: function(response) {
                    console.log('Cancel response:', response);
                    if (response.success) {
                        $(`button[data-registration-id="${registrationId}"]`).closest("tr").remove();
                        var totalCredits = parseInt($("p:contains('총 신청 학점')").text().match(/\d+/)[0]) - 3;
                        $("p:contains('총 신청 학점')").text(`총 신청 학점: ${totalCredits} 학점`);

                        if ($("#timetable-container").is(":visible")) {
                            $("#timetable-body td:not(.time-slot)").html("");
                            $.ajax({
                                url: "${path}/learning_support/viewCourse/viewCourseTime",
                                type: "get",
                                dataType: "json",
                                success: function(timetableResponse) {
                                    console.log('Timetable reload response:', timetableResponse);
                                    if (timetableResponse.success) {
                                        var timetable = timetableResponse.timetable;
                                        $("#timetable-body td:not(.time-slot)").html("");
                                        if (timetable && timetable.length > 0) {
                                            $.each(timetable, function(idx, item) {
                                                var dayClass = "";
                                                switch (item.courseTimeYoil) {
                                                    case "월": dayClass = "monday"; break;
                                                    case "화": dayClass = "tuesday"; break;
                                                    case "수": dayClass = "wednesday"; break;
                                                    case "목": dayClass = "thursday"; break;
                                                    case "금": dayClass = "friday"; break;
                                                    case "토": dayClass = "saturday"; break;
                                                    case "일": dayClass = "sunday"; break;
                                                    default: console.log('Unknown day:', item.courseTimeYoil); dayClass = ""; break;
                                                }
                                                console.log('dayClass:', dayClass, 'type:', typeof dayClass);
                                                var startTime = item.courseTimeStartFormatted ? item.courseTimeStartFormatted.replace(":", "") : "0900";
                                                console.log('startTime:', startTime, 'type:', typeof startTime);
                                                var cellClass = "." + dayClass + "-" + startTime;
                                                console.log('Generated cellClass:', cellClass);

                                                if ($(cellClass).length > 0) {
                                                    var courseInfo = $("<div>").addClass("course-info").append(
                                                        $("<span>").text(item.courseName || "No Name"),
                                                        $("<span>").text(item.courseTimeLoc || "No Location"),
                                                        $("<span>").text(item.professorName || "No Professor")
                                                    );
                                                    $(cellClass).append(courseInfo);
                                                    console.log('Appended to:', cellClass);
                                                } else {
                                                    console.log('Cell not found in DOM for:', cellClass);
                                                }
                                            });
                                        } else {
                                            $("#timetable-body").append(
                                                $("<tr>").append(
                                                    $("<td colspan='8' class='text-center text-gray-500'>").text("등록된 강의가 없습니다.")
                                                )
                                            );
                                        }
                                    } else {
                                        alert("시간표 로드 실패: " + timetableResponse.message);
                                    }
                                },
                                error: function(xhr) {
                                    alert("서버 오류: " + xhr.status + " - " + xhr.responseText);
                                }
                            });
                        } else {
                            location.reload();
                        }
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
        $("#timetable-container").show();
        console.log('Triggering view-courseTime');
        $.ajax({
            url: "${path}/learning_support/viewCourse/viewCourseTime",
            type: "get",
            dataType: "json",
            beforeSend: function() {
                console.log('Before send: Initializing timetable');
                $("#timetable-body td:not(.time-slot)").html("");
                $("#timetable-body").append(
                    $("<tr>").append(
                        $("<td colspan='8' class='text-center text-gray-500'>").text("로딩 중...")
                    )
                );
            },
            success: function(response) {
                console.log('AJAX success, response:', response);
                $("#timetable-body tr:last").remove();
                if (response.success) {
                    var timetable = response.timetable;
                    $("#timetable-body td:not(.time-slot)").html("");
                    console.log('Timetable data:', timetable);

                    if (timetable && timetable.length > 0) {
                        $.each(timetable, function(idx, item) {
                            var dayClass = "";
                            switch (item.courseTimeYoil) {
                                case "월": dayClass = "monday"; break;
                                case "화": dayClass = "tuesday"; break;
                                case "수": dayClass = "wednesday"; break;
                                case "목": dayClass = "thursday"; break;
                                case "금": dayClass = "friday"; break;
                                case "토": dayClass = "saturday"; break;
                                case "일": dayClass = "sunday"; break;
                                default: console.log('Unknown day:', item.courseTimeYoil); dayClass = ""; break;
                            }
                            console.log('dayClass:', dayClass, 'type:', typeof dayClass);
                            var startTime = item.courseTimeStartFormatted ? item.courseTimeStartFormatted.replace(":", "") : "0900";
                            console.log('startTime:', startTime, 'type:', typeof startTime);
                            var cellClass = "." + dayClass + "-" + startTime;
                            console.log('Generated cellClass:', cellClass);

                            if ($(cellClass).length > 0) {
                                var courseInfo = $("<div>").addClass("course-info").append(
                                    $("<span>").text(item.courseName || "No Name"),
                                    $("<span>").text(item.courseTimeLoc || "No Location"),
                                    $("<span>").text(item.professorName || "No Professor")
                                );
                                $(cellClass).append(courseInfo);
                                console.log('Appended to:', cellClass);
                            } else {
                                console.log('Cell not found in DOM for:', cellClass);
                            }
                        });
                    } else {
                        console.log('No timetable data or empty');
                        $("#timetable-body").append(
                            $("<tr>").append(
                                $("<td colspan='8' class='text-center text-gray-500'>").text("등록된 강의가 없습니다.")
                            )
                        );
                    }
                } else {
                    console.log('Response success false, message:', response.message);
                    alert("시간표 로드 실패: " + response.message);
                }
            },
            error: function(xhr) {
                console.log('AJAX error, xhr:', xhr);
                $("#timetable-body tr:last").remove();
                $("#timetable-body").append(
                    $("<tr>").append(
                        $("<td colspan='8' class='text-center text-gray-500'>").text("오류 발생")
                    )
                );
                alert("서버 오류: " + xhr.status + " - " + xhr.responseText);
            }
        });
    });
});
</script>