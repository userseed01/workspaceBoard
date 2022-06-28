<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 작성</title>
</head>
<body>
	<h3>게시글 작성</h3>
	<form action="/board/boardWrite.eansoft" method="post" enctype="multipart/form-data">
		<table border="1">
			<tr>
				<td>제목</td>
				<td><input type="text" name="boardTitle" autocomplete="off"></td>
			</tr>
			<tr>
				<td>종류</td>
				<td>
					<select name="boardType">
						<option value="공지">공지</option>
						<option value="뉴스">뉴스</option>
						<option value="자유">자유</option>
						<option value="기타">기타</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>내용</td>
				<td><input type="text" name="boardContent" autocomplete="off"></td>
			</tr>
			<!-- 			<tr> -->
			<!-- 				<td>첨부파일</td> -->
			<!-- 				<td><input type="text" name=""></td> -->
			<!-- 			</tr> -->
			<tr>
				<td>작성자</td>
				<td>${sessionScope.emplId }</td> <!-- 눈에만 보이고 db로는 안들어감 -->
				<input type="hidden" name="emplId" value="${sessionScope.emplId }"> <!-- db로 들어가게 -->
			</tr>
			<tr>
				<td>작성일</td>
				<td><div id="current_date"></div></td>
			</tr>
		</table>
		<button type="button" onclick="history.back();">이전</button>
		<button type="submit" onclick="btnWrite()";>등록</button>
	</form>
	<script>
		// 게시글 등록 버튼
		function btnWrite() {
			alert("게시글 등록이 완료되었습니다.");
		}

		// 작성일 자동 입력
		var today = new Date();
		// 년도 getFullYear()
		var year = today.getFullYear();
		// 월 getMonth() (0~11로 1월이 0으로 표현되기 때문에 + 1을 해주어야 원하는 월을 구할 수 있다.)
		var month = today.getMonth() + 1
		// 일 getDate()
		var date = today.getDate(); // 일
		today = year + '-' + month + '-' + date;
		document.getElementById("current_date").innerHTML = today;
	</script>
</body>
</html>