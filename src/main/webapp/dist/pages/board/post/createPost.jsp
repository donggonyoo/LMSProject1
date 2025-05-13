<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시물 등록</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css" />
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>
</head>
<body>
    <h2 class="text-center">문의게시판 글쓰기</h2>
    <form action="write" method="post" enctype="multipart/form-data" name="f">
        <table class="table">
            <tr>
                <td>글쓴이</td>
                <td><input type="text" name="author_id" class="form-control" value="${param.author_id}"></td>
            </tr>
            <tr>
                <td>비밀번호</td>
                <td><input type="password" name="pass" class="form-control"></td>
            </tr>
              <tr>
                <td>공지 설정</td>
                <td>
                    <input type="checkbox" name="post_notice" value="1" id="post_notice">
                    <label for="post_notice">공지로 설정</label>
                </td>
            </tr>
            <tr>
                <td>제목</td>
                <td><input type="text" name="post_title" class="form-control" value="${param.post_title}"></td>
            </tr>
            <tr>
                <td>내용</td>
                <td><textarea rows="15" name="post_content" class="form-control" id="summernote">${param.post_content}</textarea></td>
            </tr>
            <tr>
                <td>첨부파일</td>
                <td><input type="file" name="post_file"></td>
            </tr>
          
            <tr>
                <td colspan="2">
                    <a href="javascript:inputcheck()" class="btn btn-primary">[게시물 등록]</a>                       
                </td>
            </tr>
        </table>
    </form>
    <script>
        $(document).ready(function() {
            if (typeof $.fn.summernote === 'undefined') {
                console.error("Summernote is not loaded!");
                return;
            }
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
            <c:if test="${not empty param.post_content}">
                $('#summernote').summernote('code', "${fn:escapeXml(param.post_content)}");
            </c:if>
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

        function inputcheck() {
            let f = document.f;
            if (f.author_id.value == "") {
                alert("글쓴이를 입력하세요");
                f.author_id.focus();
                return;
            }
            if (f.pass.value == "") {
                alert("비밀번호를 입력하세요");
                f.pass.focus();
                return;
            }
            if (f.post_title.value == "") {
                alert("제목을 입력하세요");
                f.post_title.focus();
                return;
            }
            f.submit();
        }
    </script>
</body>
</html>