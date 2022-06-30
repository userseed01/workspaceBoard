<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정</title>
</head>
<body>
	<h3>게시글 수정</h3>
	<button onclick="history.back();">이전</button>
	<form action="/board/boardModify.eansoft" method="post" enctype="multipart/form-data">
		<!-- form안에만 있으면 됨, 안보여도 되니까 hidden,
		value를 가져오려면 name필요함, no도 가져와야 수정가능 -->
		<input type="hidden" name="boardNo" value="${board.boardNo }">
		<table border="1">
			<tr>
				<td>제목</td>
				<td>
					<input type="text" name="boardTitle" value="${board.boardTitle }" autocomplete="off">
				</td>
			</tr>
			<tr>
				<td>종류</td>
				<td>
					<input type="text" name="boardType" value="${board.boardType }" readonly>
				</td>
			</tr>
			<tr>
				<td>내용</td>
				<td>
					<input type="text" name="boardContent" value="${board.boardContent }" autocomplete="off">
				</td>
			</tr>
			<!-- 			<tr> -->
			<!-- 				<td>첨부파일</td> -->
			<!-- 				<td></td> -->
			<!-- 			</tr> -->
			<tr>
				<td>작성자</td>
				<td>
					<input type="text" name="emplId" value="${board.emplId }" readonly>
				</td>
			</tr>
			<tr>
				<td>작성일</td>
				<td>
					<input type="text" name="boardDate" value="${board.boardDate }" readonly>
				</td>
			</tr>
		</table>
		<button type="submit" onclick="btnModify()";>완료</button>
	</form>
	<script>
		// 게시글 등록 버튼
		function btnModify() {
			alert("게시글 수정이 완료되었습니다.");
		}
	</script>
</body>
</html>