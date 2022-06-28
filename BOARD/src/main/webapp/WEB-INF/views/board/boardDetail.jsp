<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상세보기</title>
<script src="http://code.jquery.com/jquery-3.5.1.min.js"></script>
</head>
<body>
	<h3>${board.boardNo }번 게시글 상세보기</h3>
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
	<button onclick="history.back();">이전</button >
	<button type="button" onclick="location.href="boardModify";>수정하기</button>
</body>
</html>