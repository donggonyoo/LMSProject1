<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 수정 화면</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>
</head>
<body>
<form action="${path}/post/update" method="post" enctype="multipart/form-data" name="f">
    <input type="hidden" name="postId" value="${p.postId}">
    <input type="hidden" name="postFile" value="${p.postFile}">
    <div>
        <table class="table">
            <tr>
                <td>글쓴이</td>
                <td>
                    <input type="text" name="authorId" value="${p.authorId != null ? p.authorId : 'No Author'}" class="form-control">
                    <!-- 디버깅: b 객체가 null인지 확인 -->
                    <c:if test="${p == null}">
                        <p style="color:red;">p 객체가 null입니다.</p>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td>비밀번호</td>
                <td>
                    <input type="password" name="postPassword" class="form-control">
                </td>
            </tr>
            <tr>
                <td>제목</td>
                <td>
                    <input type="text" name="postTitle" value="${p.postTitle != null ? p.postTitle : 'No Title'}" class="form-control">
                </td>
            </tr>
            <tr>
                <td>내용</td>
                <td>
                    <textarea rows="15" name="postContent" id="summernote" class="form-control">${p.postContent != null ? p.postContent : 'No Content'}</textarea>
                </td>
            </tr>
            <tr>
                <td>첨부파일</td>
                <td style="text-align:left">
                    <c:if test="${not empty p.postFile}">
                        <div id="file_desc">
                            ${p.postFile}
                            <a href="javascript:file_delete()">[첨부파일 삭제]</a>
                        </div>
                    </c:if>
                    <input type="file" name="postFile">
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <a href="javascript:document.f.submit()" class="btn btn-primary">게시물 수정</a>
                    <a href="${path}/post/getPostDetail?post_id=${p.postId}" class="btn btn-secondary">취소</a>
                </td>
            </tr>
        </table>
    </div>
</form>
<script type="text/javascript">
    function file_delete() {
        document.f.postFile.value = "";
        document.querySelector('#file_desc').style.display = "none";
    }
</script>
<script type="text/javascript">
    $(function() {
        $("#summernote").summernote({
            height: 300,
            callbacks: {
                onImageUpload: function(files) {
                    for(let i = 0; i < files.length; i++) {
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