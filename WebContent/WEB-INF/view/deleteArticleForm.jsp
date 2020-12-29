<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<title>Insert title here</title>
</head>
<body>
 <u:navbar />
<div class="container">
  <h1>게시물 삭제</h1>
  <form action="delete.do" method="post">
    <input type="text" name="no" id="" value="${param.no }" hidden/>
    <p>
    암호 : <input type="password" name="password" /> <br />
    <c:if test="${errors.invalidePassword }">암호가 일치하지 않습니다.</c:if>
    </p>
    <input type="submit" value="게시물 삭제" />
  </form>
</div>
</body>
</html>