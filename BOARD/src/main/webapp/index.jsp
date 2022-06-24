<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 페이지</title>
</head>
<body>
	<h2>로그인</h2>
	<form action="/employee/login.eansoft" method="post" enctype="multipart/form-data">
		<input name="emplId" type="text" placeholder="아이디를 입력해 주세요." autocomplete="off"><br>
		<input name="emplPw" type="password" placeholder="비밀번호를 입력해 주세요." autocomplete="off"><br>

		<!-- 서브밋이면 폼 action에 url 적어줌, 온클릭은 submit 버튼 이외의 기능(예/팝업 열릴 때)만 사용 -->
		<button type="submit">로그인</button>

		<div>
			<!-- id는 같은 게 한 페이지 안에 하나만 있어야 함 -->
			<a id="myLink1" href="/employee/registerView.eansoft">회원가입</a>&nbsp;&nbsp;
			<a id="myLink2" href="/">비밀번호 찾기</a>
		</div>
	</form>

	<p>
		문의사항은 운영 담당자에게 연락하여 주시기 바랍니다.<br>
		이안소프트 개발팀 ☎ 010-XXXX-XXXX
	</p>
</body>
</html>