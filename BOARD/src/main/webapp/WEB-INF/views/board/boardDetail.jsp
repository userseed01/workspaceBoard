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
			<td>첨부파일</td>
			<td>
				<c:forEach var="file" items="${board.bList }">
					<a href="../../../resources/uploadFiles/${file.fileRename }" download>${file.fileName}</a>
				</c:forEach>
				<c:if test="${empty board.bList}">
					<p>등록된 파일이 없습니다.</p>
				</c:if>
			</td>
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
	<button type="button" onclick="btnDelete()";>삭제</button><br><br>
	
	<!-- 댓글 등록 -->
	<div>
		<span>${sessionScope.emplId}</span>
		<textarea id="cContent" placeholder="댓글을 입력하세요."></textarea>
		<button id="cSubmit">등록</button><br><br>
	</div>
	<!-- 댓글 목록 -->
	<table border="1" id="ctb">
		<thead>
			<tr>
				<!-- 댓글 갯수 -->
				<td><b id="cCount"></b></td>
			</tr>
		</thead>
		<tbody>
		<!-- <tr> -->
		<!-- <td>khuser01</td> -->
		<!-- <td>댓글 내용입니다</td> -->
		<!-- <td>2022-02-08</td> -->
		<!-- <td>수정 삭제</td> -->
		<!-- </tr> -->
		</tbody>
	</table>
	
	<script>
	commentView(); // 호출
	
	// 게시글 삭제 버튼
	function btnDelete() {
		alert("게시글 삭제가 완료되었습니다.");
		location.href='${boardDelete }';
	}
	
	// 게시글 상세조회 시 댓글 조회 화면
	function commentView() {
		var boardNo = "${board.boardNo}";
		$.ajax({
			url : "/board/boardCommentView.eansoft",
			type : "get",
			data : {
				"boardNo" : boardNo 
			},
			success : function(data) {
				var $tableBody = $("#ctb tbody");
				$tableBody.html("");
				var $cEmplId;
				var $cContent;
				var $cWriteDate;
				var $btnArea;
				var $tr;
				// 댓글 갯수 표시 방법 (제이쿼리)
				$("#cCount").text("댓글 (" + data.length + ")");
				if (data.length > 0) { // 데이터가 있으면
					for (var i in data) { // var i = 0; i < data.length; i++
						$tr = $("<tr>"); //<tr></tr>
						$cEmplId = $("<td>").text(data[i].emplId);
						$cContent = $("<td>").text(data[i].commentContent);
						$cWriteDate = $("<td>").text(data[i].commentWriteDate);
						$btnArea = $("<td>")
							.append( /* 추가 */
							// (this) -> 이벤트가 발생된 태그가 선택됨
							// data[i].replyContents -> 수정누르면 입력값 자동으로 입력되어있게 -> 매개변수 넘겨주고, value 값에도("++")사용하여 넣어줌
							// 문자열은 문자열로 표시(수정(data[i].replyNo)은 숫자라서 오류가 안났음)
							"<a href='javascript:void(0);' onclick='modifyComment(this, \""+ data[i].commentContent + "\", "+data[i].commentNo+");'>수정</a>") // td 안으로 태그가 들어갈 수 있게 
							.append(
							// javascript:void(0); -> 삭제버튼 눌러도 창 맨위로 올라가지 않게
							// "+data[i].replyNo+" -> 데이터 function으로 넘겨주기 위해
							"<a href='javascript:void(0);' onclick='removeComment("+ data[i].commentNo + ");'>삭제</a>");
						$tr.append($cEmplId);
						$tr.append($cContent);
						$tr.append($cWriteDate);
						$tr.append($btnArea);
						$tableBody.append($tr);
					}
				}
			},
			error : function() {
				alert("ajax 실패")
			}
		});
	}
	
	// 게시글 상세조회 시 댓글 등록
	$("#cSubmit").on("click", function(){
		var boardNo = "${board.boardNo}";
		var cContent = $("#cContent").val(); // 버튼 서밋으로 입력된 값 갖고오기, 번호에 맞게 내용 인서트 해야하니까
		$.ajax({
			url : "/board/boardCommentAdd.eansoft",
			type : "post",
			data : {
				"boardNo" : boardNo,
				"commentContent" : cContent // "도메인" : 위에쓴것
			},
			success : function(data) {
				// 성공하면 댓글 등록 후 입력값 없어지게
				if (data == "success") {
					alert("댓글이 등록되었습니다.");
					// 작성 후 내용 초기화
					$("#cContent").val("");
					// 댓글 불러오기
					commentView();
				} else {
					alert("댓글 등록 실패");
				}
			},
			error : function() {
				alert("ajax 실패");
			}
		});
	});
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	</script>
</body>
</html>