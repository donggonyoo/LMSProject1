<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<script>
alert("${msg}")
//requestScope.msg
opener.document.f.password.value = "${pw}"
//requestScope.id
self.close();
</script>