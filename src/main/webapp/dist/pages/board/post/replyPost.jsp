<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="path" value="${pageContext.request.contextPath}"/>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>답변 게시글</title>
</head>
<body>
<form action="reply" method="post" name="f">
	<input type="hidden" name="num" value="${post.postId}">
	<input type="hidden" name="grp" value="${board.grp}">
	<input type="hidden" name="grplevel" value="${board.grplevel}">
	<input type="hidden" name="grpstep" value="${board.grpstep}">
	<input type="hidden" name="boardid" value="${board.boardid}">
	<div class="container">
		<table class="table">
			<tr>
				<th>글쓴이</th>
				<td>
					<input type="text" name="writer" class="form-control">
				</td>
			</tr>
			
			<tr>
				<th>비밀번호</th>
				<td>
					<input type="password" name="pass" class="form-control">
				</td>
			</tr>
			
			<tr>
				<th>제목</th>
				<td>
					<input type="text" name="title" class="form-control" value="RE:${board.title}">
				</td>
			</tr>
			
			<tr>
				<th>내용</th>
				<td>
					<textarea name="content" rows="15" class="form-control" id="summernote"></textarea>
				</td>
			</tr>
			
			<tr>
				<td colspan="2" align="center">
					<a href="javascript:document.f.submit()">[답변글 등록]</a>
				</td>
			</tr>
		</table>
	</div>
</form>
<script type="text/javascript">
	$(function() {
		$("#summernote").summernote({
			height : 300,
			callbacks : {
				// onImageUpload : 이미지 업로드 이벤트 발생
				// files : 한개이상의 이미지가 업로드 가능 
				onImageUpload : function(files) {
					for(let i=0;i < files.length;i++) {
						sendFile(files[i]); // 하나씩 ajax 이용하여 서버로 파일 전송
					}
				}
			}
		})
	})
	function sendFile(file) {
		let data = new FormData(); // 폼데이터를 수집하고 전송 가능한 객체. 파일 업로드에 사용됨.
		data.append("file",file); // file : 이미지 파일 내용
		$.ajax({
			url : "${path}/board/uploadImage",
			type : "POST",
			data : data,
			processData : false,
			contentType : false,
			success : function(url) {
				// url : 업로드된 이미지의 접근 url 정보 
				$('#summernote').summernote('insertImage', url);
			// <img src ="url"> 변경
			},
			error : function(e) {
				alert("이미지 업로드 실패 : " +e.status);
			}
		})
	}
</script>

</body>
</html>