<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>API 통계 화면</title>
</head>
<!-- 제이쿼리 -->
<script src="http://code.jquery.com/jquery-3.5.1.min.js"></script>
<body>
	<h3>API 통계</h3>
	<input type="date" id="startDate" value="2022-01-01">
	<input type="date" id="endDate">
	<table board="1">
		<thead>
			<tr>
				<td>아이디</td>
				<td>연차 개수</td>
			</tr>
		</thead>
		<tbody>
			<!-- <td>아이디</td> -->
			<!-- <td>연차 개수</td> -->
		</tbody>
	</table>

	<script>
		document.getElementById("endDate").value = new Date().toISOString().substring(0, 10);
		function prev() {
			history.back();
		}
		// 일반 조회
		$(function() {
			var startDate = $("#startDate").val();
			var endDate = $("#endDate").val();
			console.log(startDate);
			console.log(endDate);
			$.ajax({
				url : "http://127.0.0.1:9999/time/vacationApi.hirp",
				// url : "http://127.0.0.1:8888/statistics/count.hirp",
				// url : "http://192.168.0.18:8888/board/statistic.hirp",
				type : "get",
				date : {
					"startDate" : startDate,
					"endDate" : endDate
				},
				dataType : "jsonp", // 다른ip에서 가져오기위해
				jsonp : "callback",
				success : function(data) {
					var count = data.length;
					var $tableBody = $("#vTable tbody");
					$tableBody.html("");
					var $trCount = $("<tr>");
					var $vTable = $("#vTable tbody");
					var $tr = $("<tr>");
					for (var i = 0; i < data.length; i++) {
						var emplId = data[i].emplId;
						var vacationCount = data[i].vacationCount;
						var statisticData = "<tr>" + "<td>" + emplId + "</td>"
										  + "<td>" + vacationCount + "</td>" + "</tr>"
						$vTable.append(statisticData);
					}
					$tr.append($vacationManager);
					$tr.append($vacationCount);
					$tableBody.append($tr);
				},
				error : function() {
					alert("ajax 실패");
				}
			});
		});

		// 날짜 선택 후 조회
		function selectDate() {
			var startDate = $("#startDate").val();
			var endDate = $("#endDate").val();
			$.ajax({
				url : "http://127.0.0.1:9999/time/vacationApi.hirp",
				// url : "http://127.0.0.1:8888/statistics/count.hirp",
				// url : "http://192.168.0.18:8888/board/statistic.hirp",
				type : "get",
				date : {
					"startDate" : startDate,
					"endDate" : endDate
				},
				dataType : "jsonp", // 다른ip에서 가져오기위해
				jsonp : "callback",
				success : function(data) {
					var $vTable = $("#vTable tbody");
					$vTable.html("");
					var $tr = $("<tr>");
					for (var i = 0; i < data.length; i++) {
						var emplId = data[i].emplId;
						var vacationCount = data[i].vacationCount;
						var statisticData = "<tr>" + "<td>" + emplId + "</td>"
										  + "<td>" + statisticCount + "</td>" + "</tr>"
						$vTable.append(statisticData);
					}
				},
				error : function() {
					alert("ajax 실패");
				}
			});
		}
	</script>
</body>
</html>