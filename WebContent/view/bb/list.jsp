<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<table border="">
	<tr>
		<td>글번호</td>
		<td>제목</td>
		<td>글쓴이</td>
		<td>작성일</td>
	</tr>
	<c:choose>
	<c:when test="${data.size()==0 }">
	<tr>
		<td colspan="4" align="center">데이터가없당</td>
	</tr>
	</c:when>
	<c:otherwise>
		<c:forEach var="ee" items="${data }" varStatus="no">
		<tr>
			<td>${no.index+start }</td>
			<td>
			<c:if test="${ee.lev>0 }">
				<c:forEach begin="1" end="${ee.lev }">
				&nbsp;&nbsp;
				</c:forEach>
				└
			</c:if>
			<a href="Detail?id=${ee.id }&page=${page}">${ee.title }</a>
			</td>
			<td>${ee.pname }</td>
			<td><fmt:formatDate value="${ee.reg_date }" pattern="yyyy-MM-dd" /></td>
		</tr>
		</c:forEach>
	</c:otherwise>
	</c:choose>

<!-- 페이지번호  -->
	<tr>
		<td colspan="4" align="center">
		<c:if test="${startPage>1 }">
			<a href="List?page=1">[처음]</a>
			<a href="List?page=${startpage-1}">이전</a>
		</c:if>
		
			<c:forEach  var="i" begin="${startPage }" end="${endPage }">
				<c:choose>
					<c:when test="${i==page }">
					[${i }]
					</c:when>
					<c:otherwise>
					<a href="List?page=${i }">${i }</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		
		<c:if test="${endPage<totalPage }">
			<a href="List?page=${endPage+1}">다음</a>
			<a href="List?page=${totalPage }">[마지막]</a>
		</c:if>
		</td>
	</tr>
	
<!-- 글쓰기 -->
	<tr>
		<td colspan="4" align="right">
			<a href="InsertForm?page=${page }">글쓰기</a>
		</td>
	</tr>
</table>
</body>
</html>