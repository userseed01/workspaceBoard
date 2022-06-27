<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
</head>
<body>
	<h2>게시판</h2>
	<a href="/">게시글 작성</a><br><br>
	<table border="1">
		<tr>
			<td>번호</td>
			<td>종류</td>
			<td>제목</td>
			<td>작성자</td>
			<td>작성일</td>
			<td>조회수</td>
		</tr>
		
		<!-- forEach는 for문과 같음 -->
		<!-- 테이블 헤드, td긴 한데 글씨가 더 진해짐 -->
		<c:forEach items="${bList }" var="board">
			<tr>
				<td>${board.boardNo }</td>
				<c:url var="bList" value="/board/boardDetailView.eansoft"> <!-- form의 action같은것 -->
					<c:param name="boardNo" value="${board.boardNo }"></c:param> <!-- url로 보내겠다 -->
				</c:url>
				<td>${board.boardType }</td>
				<th><a href="${bList }">${board.boardTitle }</a></th> <!-- var와 href맞춰주기 -->
<%-- 				<c:url var="bList" value="/board/boardDetailView.eansoft">  --%>
<%-- 					<c:param name="boardTitle" value="${board.boardTitle }"></c:param>  --%>
<%-- 				</c:url> --%>
				<!-- <td></td>   첨부파일 -->
				<td>${board.emplId }</td>
				<td>${board.boardDate }</td>
				<td>${board.boardHits }</td>
			</tr>
		</c:forEach>
		
	</table>
</body>
</html>