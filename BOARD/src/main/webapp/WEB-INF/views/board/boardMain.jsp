<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
</head>
<body>
	<h2>게시판</h2>
	<a href="/board/boardWriteView.eansoft">게시글 작성</a><br>
	<a href="/board/boardDownload.eansoft">게시글 다운로드(POI)</a><br>
	<!-- 컨트롤러에서 ()안에 search를 적어줬기 때문에 mapper로 가서 검색을 하는데 tostring 오류나서
		?searchCondition=${search.searchCondition }&searchValue=${search.searchValue } 추가해줌 -->
	<a href="/board/boardSearchDownload.eansoft?searchCondition=${search.searchCondition }&searchValue=${search.searchValue }" >게시글 검색 다운로드(POI)</a><br><br>

	<form action="/board/boardSearchView.eansoft" method="get">
		<select name="searchCondition">
			<option value="boardTitle">제목</option>
			<option value="boardContent">내용</option>
			<option value="emplId">작성자</option>
		</select>
		<input type="text" name="searchValue">
		<button type="submit">검색</button>
	</form>

	<table border="1">
		<tr>
			<td>번호</td>
			<td>종류</td>
			<td>제목</td>
			<td>첨부파일 개수</td>
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
				<th><a href="${bList }">${board.boardTitle }</a></th>
				<!-- var와 href맞춰주기 -->
				<%-- 				<c:url var="bList" value="/board/boardDetailView.eansoft">  --%>
				<%-- 					<c:param name="boardTitle" value="${board.boardTitle }"></c:param>  --%>
				<%-- 				</c:url> --%>
				<td>${board.fileCount }</td>
				<td>${board.emplId }</td>
				<td>${board.boardDate }</td>
				<td>${board.boardHits }</td>
			</tr>
		</c:forEach>

		<c:if test="${listType eq 'basicList'}">
			<tr>
				<td colspan="2">
					<!-- 첫페이지면 이전 안보임 -->
					<c:if test="${pc.currentPage > '1' }">
						<button onclick="location.href='/board/boardMainView.eansoft?page=${pc.currentPage-1 }'">이전</button>
					</c:if>
					<c:forEach var="p" begin="${pc.startNavi }" end="${pc.endNavi }">
						<c:url var="pagenation" value="/board/boardMainView.eansoft">
							<c:param name="page" value="${p }"></c:param>
						</c:url>
						<a href="${pagenation }">${p }</a>&nbsp;
					</c:forEach>
					<!-- 마지막페이지면 다음 안보임 -->
					<c:if test="${pc.currentPage < pc.endNavi }">
						<button onclick="location.href='/board/boardMainView.eansoft?page=${pc.currentPage+1 }'">다음</button>
					</c:if>
				</td>
			</tr>
		</c:if>

		<c:if test="${listType eq 'searchList' && not empty bList}"> <!--  controller에서 가져옴, l이s면 ,and 비지않았으면, 결과가 있으면 -->
			<tr>
				<td colspan="2">
					<!-- 첫페이지면 이전 안보임 -->
					<c:if test="${pc.currentPage > '1' }">
						<button onclick="location.href='/board/boardSearchView.eansoft?page=${pc.currentPage-1 }&searchCondition=${search.searchCondition }&searchValue=${search.searchValue }'">이전</button>
					</c:if>
					<c:forEach var="p" begin="${pc.startNavi }" end="${pc.endNavi }">
						<c:url var="pagenation" value="/board/boardSearchView.eansoft?&searchCondition=${search.searchCondition }&searchValue=${search.searchValue }">
							<c:param name="page" value="${p }"></c:param>
						</c:url>
						<a href="${pagenation }">${p }</a>&nbsp;
					</c:forEach>
					<!-- 마지막페이지면 다음 안보임 -->
					<c:if test="${pc.currentPage < pc.endNavi }">
						<button onclick="location.href='/board/boardSearchView.eansoft?page=${pc.currentPage+1 }&searchCondition=${search.searchCondition }&searchValue=${search.searchValue }'">다음</button>
					</c:if>
				</td>
			</tr>
		</c:if>
	</table>
</body>
</html>