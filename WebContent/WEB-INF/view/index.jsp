<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags"%> 

<c:set var="page" value="${empty param.page ? 1 : param.page }" />
<c:set var="endIndex" value="${page * 6 }" />
<c:set var="beginIndex" value="${endIndex - 5 }" />
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
<style>
@import url('https://fonts.googleapis.com/css2?family=Do+Hyeon&family=Yeon+Sung&display=swap');
body {
    font-family: 'Yeon Sung', cursive;
    font-size: 25px;
}
</style>
<title>야옹야옹</title>
</head>
<body>
  <u:navbar />
  <div class="container">
  
    <div class="jumbotron">
      <h1 class="display-4">고양이 최고야!</h1>
      <p class="lead">귀여운 고양이</p>
      <hr class="my-4">
      
      <u:notLogin>
      <p>귀여운 고양이 보고가</p>
      <a class="btn btn-primary btn-lg" href="${root }/join.do" role="button">회원 가입</a>
      </u:notLogin>
     
      <u:isLogin>
      <p>${authUser.name }님 반가워요. 야옹야옹</p>
      <a class="btn btn-primary btn-lg" href="${root }/article/write.do" role="button">글 쓰기</a>
      </u:isLogin>
      
    <hr class="my-4">
      
<div class="container">
<div class="row row-cols-1 row-cols-md-3">

    <c:forEach items="${articleList }" var="article" begin="${beginIndex }" end="${endIndex }">
    <!--forEach를 통해 list 출력 tomcat서버를 통해 사진추적경로설정 가능  -->
     <div class="col mb-4">
    <a href="${root }/article/read.do?no=${article.number }&pageNo=${articlePage.currentPage }">
        <div class="card h-100">
        <div class="card-body">
         <img src="/static/${article.number }/${article.fileName1}" class="card-img-top" alt="...">
         <!--static/게시글번호/게시글이름/게시글내용  -->
            <h5 class="card-title" >${article.title }</h5>
            <p class="card-text">${article.content  }</p>
      </div>
    </div>
        </a>
  </div>
    </c:forEach>
    
</div>
</div>
    <div class="d-flex justify-content-center">
          <c:forEach var="pNo" begin="${1}" 
                     end="${articleList.size() / 6 + 1 }">
            <a href="main.do?page=${pNo }">[${pNo }]</a>
          </c:forEach>  
    </div>
</div>
</div>
</body>
</html>
