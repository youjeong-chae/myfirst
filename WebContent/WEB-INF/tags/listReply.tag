<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="mt-3 mb-5">
  <c:forEach items="${replyList }" var="reply">
  <div class="my-2 text-center">
    <input type="text" value="${reply.body }" readonly class="w-75"/>
    <span>${reply.memberId }</span>
  </div>
  </c:forEach>
</div>