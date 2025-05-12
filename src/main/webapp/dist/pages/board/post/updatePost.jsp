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
    <h2 class="text-center">게시물 수정</h2>
    <form action="update" method="post" enctype="multipart/form-data">
        <input type="hidden" name="postId" value="${p.postId}">
        <table class="table">
            <tr>
                <td>글쓴이</td>
                <td><input type="text" name="authorId" class="form-control" value="${p.authorId}"></td>
            </tr>
            <tr>
                <td>비밀번호</td>
                <td><input type="password" name="postPassword" class="form-control"></td>
            </tr>
            <tr>
                <td>제목</td>
                <td><input type="text" name="postTitle" class="form-control" value="${p.postTitle}"></td>
            </tr>
            <tr>
                <td>내용</td>
                <td><textarea rows="15" name="postContent" class="form-control" id="summernote">${p.postContent}</textarea></td>
            </tr>
            <tr>
                <td>첨부파일</td>
                <td>
                    <input type="file" name="postFile">
                    <c:if test="${not empty p.postFile}">
                        <p>현재 파일: ${p.postFile}</p>
                        <input type="hidden" name="postFile" value="${p.postFile}">
                    </c:if>
                </td>
            </tr>
            <tr>
                <td>공지 설정</td>
                <td>
                    <input type="checkbox" name="post_notice" value="1" id="post_notice" ${p.postNotice ? 'checked' : ''}>
                    <label for="post_notice">공지로 설정</label>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <button type="submit" class="btn btn-primary">수정 완료</button>
                    <a href="${pageContext.request.contextPath}/post/getPosts" class="btn btn-secondary">목록</a>
                </td>
            </tr>
        </table>
    </form>
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
            });
        });

        function sendFile(file) {
            let data = new FormData();
            data.append("file", file);
            $.ajax({
                url: "${pageContext.request.contextPath}/post/uploadImage",
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