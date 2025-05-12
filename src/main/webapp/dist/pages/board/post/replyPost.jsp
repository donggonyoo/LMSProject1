<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>답변 게시글</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
</head>
<body>
<div class="container mt-5">
    <h1 class="fs-3">답변 작성</h1>
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>
    <form action="writeReply" method="post" name="f" enctype="multipart/form-data">
        <input type="hidden" name="num" value="${board.postId}">
        <input type="hidden" name="grp" value="${board.postGroup}">
        <input type="hidden" name="grplevel" value="${board.postGroupLevel}">
        <input type="hidden" name="grpstep" value="${board.postGroupStep}">
        <table class="table">
            <tr>
                <th>글쓴이</th>
                <td><input type="text" name="writer" class="form-control" value="${board.authorId}"></td>
            </tr>
            <tr>
                <th>비밀번호</th>
                <td><input type="password" name="pass" class="form-control"></td>
            </tr>
            <tr>
                <th>제목</th>
                <td><input type="text" name="title" class="form-control" value="RE: ${board.postTitle}"></td>
            </tr>
            <tr>
                <th>내용</th>
                <td><textarea name="content" rows="15" class="form-control" id="summernote"></textarea></td>
            </tr>
            <tr>
                <th>첨부파일</th>
                <td><input type="file" name="post_file" class="form-control"></td>
            </tr>
            <tr>
                <td colspan="2" class="text-center">
                    <button type="submit" class="btn btn-primary">답변글 등록</button>
                    <a href="${path}/post/getPosts" class="btn btn-secondary">목록</a>
                </td>
            </tr>
        </table>
    </form>
</div>

<script>
$(function() {
    $("#summernote").summernote({
        height: 300,
        callbacks: {
            onImageUpload: function(files) {
                for (let i = 0; i < files.length; i++) {
                    sendFile(files[i]);
                }
            }
        }
    });
});

function sendFile(file) {
    let data = new FormData();
    data.append("file", file);
    $.ajax({
        url: "${path}/post/uploadImage",
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
