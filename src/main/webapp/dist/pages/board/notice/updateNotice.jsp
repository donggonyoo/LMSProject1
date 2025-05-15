<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시물 수정</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css" />
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>
</head>
<body>
    <div class="container mt-5">
        <h2 class="text-center fs-1">게시물 수정</h2>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
            <% session.removeAttribute("error"); %>
        </c:if>
        <form action="update" method="post" enctype="multipart/form-data">
            <input type="hidden" name="noticeId" value="${notice.noticeId}">
            <table class="table">
                <tr>
                    <td>글쓴이</td>
                    <td>
                        <input type="text" name="writerId" class="form-control" value="${notice.writerId}" readonly>
                        <input type="hidden" name="writerId" value="${notice.writerId}">
                    </td>
                </tr>
                <tr>
                    <td>작성자 이름</td>
                    <td>
                        <input type="text" class="form-control" value="${writerName}" readonly>
                    </td>
                </tr>
                <tr>
                    <td>비밀번호</td>
                    <td><input type="password" name="noticePassword" class="form-control"></td>
                </tr>
                <tr>
                    <td>제목</td>
                    <td><input type="text" name="noticeTitle" class="form-control" value="${notice.noticeTitle}"></td>
                </tr>
                <tr>
                    <td>내용</td>
                    <td><textarea rows="15" name="noticeContent" class="form-control" id="summernote">${notice.noticeContent}</textarea></td>
                </tr>
                <tr>
                    <td>첨부파일</td>
                    <td>
                        <input type="file" name="noticeFile">
                        <c:if test="${not empty notice.noticeFile}">
                            <p>현재 파일: ${notice.noticeFile}</p>
                            <input type="hidden" name="noticeFile" value="${notice.noticeFile}">
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <button type="submit" class="btn btn-primary">수정 완료</button>
                        <a href="getNotices" class="btn btn-secondary">목록</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <script>
        $(document).ready(function() {
            $("#summernote").summernote({
                height: 300,
                callbacks: {
                    onImageUpload: function(files) {
                        for (let i = 0; i < files.length; i++) {
                            sendFile(files[i]);
                        }
                    }
                }
            }).summernote('code', '${notice.noticeContent}'); // 초기값 로드
        });

        function sendFile(file) {
            let data = new FormData();
            data.append("file", file);
            $.ajax({
                url: "uploadImage",
                type: "POST",
                data: data,
                processData: false,
                contentType: false,
                success: function(url) {
                    $('#summernote').summernote('insertImage', url);
                },
                error: function(e) {
                    alert("이미지 업로드 실패: " + e.status);
                }
            });
        }
    </script>
</body>
</html>