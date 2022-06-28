<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상세보기</title>
<script src="http://code.jquery.com/jquery-3.5.1.min.js"></script>
</head>
<body>
	<h3>${board.boardNo }번게시글 상세보기</h3>
	<table border="1">
		<tr>
			<td>번호</td>
			<td>${board.boardNo }</td>
		</tr>
		<tr>
			<td>종류</td>
			<td>${board.boardType }</td>
		</tr>
		<tr>
			<td>제목</td>
			<td>${board.boardTitle }</td>
		</tr>
		<tr>
			<td>내용</td>
			<td>${board.boardContent }</td>
		</tr>
		<tr>
			<td>작성자</td>
			<td>${board.emplId }</td>
		</tr>
		<tr>
			<td>작성일</td>
			<td>${board.boardDate }</td>
		</tr>
		<tr>
			<td>조회수</td>
			<td>${board.boardHits }</td>
		</tr>
	</table>
	
	<!-- no를 꼭 넘겨줘야함 -->
	<button onclick="history.back();">이전</button>
	<c:url var="boardModify" value="/board/boardModifyView.eansoft"> <!-- form의 action같은것 -->
		<c:param name="boardNo" value="${board.boardNo }"></c:param> <!-- url로 보내겠다 -->
	</c:url>
	<button type="button" onclick="location.href='${boardModify }';">수정</button>
	
	<!-- no를 꼭 넘겨줘야함 -->
	<c:url var="boardDelete" value="/board/boardDeleteView.eansoft">
		<c:param name="boardNo" value="${board.boardNo }"></c:param>
	</c:url>
	<button type="button" onclick="btnDelete()";>삭제</button>
	
	<script>
	// 게시글 삭제 버튼
	function btnDelete() {
		alert("게시글 삭제가 완료되었습니다.");
		location.href='${boardDelete }';
	}
	</script>
	
</body>
</html>