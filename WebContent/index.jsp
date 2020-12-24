<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
  href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script
  src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
  src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
  src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://kit.fontawesome.com/a076d05399.js"></script>

<title>유정</title>
</head>
<body>
  <u:navbar />
  <div class="container">
    <div class="jumbotron">
      <h1 class="display-4">첫 번째 프로젝트!</h1>
      <p class="lead">안녕하세요. 저의 첫 번째 프로젝트 입니다. 회원제 게시판을 만들었습니다.</p>
      <hr class="my-4">
      
      <u:notLogin>
      <p>회원 가입부터 시작해보세요.</p>
      <a class="btn btn-primary btn-lg" href="${root }/join.do" role="button">회원 가입</a>
      </u:notLogin>
      
      <u:isLogin>
      <p>${authUser.name }님 새 글을 작성해보세요.</p>
      <a class="btn btn-primary btn-lg" href="${root }/article/write.do" role="button">글 쓰기</a>
      
      </u:isLogin>
    </div>
  </div>
</body>
</html>
