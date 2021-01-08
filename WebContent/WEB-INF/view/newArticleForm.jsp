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
<script src="https://kit.fontawesome.com/a076d05399.js"></script>
<title>Insert title here</title>
</head>
<body>
 <u:navbar />
 <div class="row">
      <div class="col-5"></div>
      <div class="col-6">
        <h1>게시글 쓰기</h1>
        <form action="write.do" method="post" enctype="multipart/form-data">
          <p>
            제목 : <br />
            <input type="text" name="title" value="${param.title }" />
            <c:if test="${errors.title }">제목을 입력하세요.</c:if>
          </p>
          
          <p>
            내용 : <br />
            <textarea name="content" id="" cols="30" rows="5">${param.content }</textarea>
          </p>
          
          <p>
          file1 : <input type="file" name="uploadFile1" /> <br />
          file2 : <input type="file" name="uploadFile2" />
          </p>
          
          <input type="submit" class="btn btn-primary" value="새 글 등록" />
        </form>
        </div> 
   </div> 
</body>
</html>