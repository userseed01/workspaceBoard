<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
</head>
<body>
	<h2>회원가입</h2>
	<!-- form이 있어야 controller로 넘어감 -> db -->
	<form action="/employee/register.eansoft" method="post" enctype="multipart/form-data">

		<div>
			<!-- name은 컨트롤러 저장위해 필요. 컨트롤러와 같아야함 -->
			<label>아이디</label>
			<input name="emplId" type="text" placeholder="아이디를 입력하세요." autocomplete="off">
		</div>

		<div>
			<label>비밀번호</label>
			<input name="emplPw" type="password" placeholder="비밀번호를 입력하세요.">
		</div>

		<div>
			<label>이름</label>
			<input name="emplName" type="text" placeholder="이름을 입력하세요." autocomplete="off">
		</div>

		<div>
			<label>생년월일</label>
			<input name="emplBirthday" type="date">
		</div>

		<div>
			<label>연락처</label>
			<input name="emplPhone" type="text" placeholder="010-1234-5678" autocomplete="off">
		</div>

		<div>
			<label>성별</label>
			<!-- 라디오는 name같게. id와 for 같게 -->
			<!-- name값 도메인과 같게 -->
			<input id="female" name="emplGender" type="radio" value="여성" checked>
			<label for="female">여성</label>
			<input id="male" name="emplGender" type="radio" value="남성">
			<label for="male">남성</label>
			<input id="etc" name="emplGender" type="radio" value="기타">
			<label for="etc">기타</label>
		</div>

		<button type="submit" onclick="btnRegister()";>회원가입</button>

	</form>

	<script>
		function btnRegister() {
			alert("회원가입이 완료되었습니다.");
		}
	</script>

</body>
</html>