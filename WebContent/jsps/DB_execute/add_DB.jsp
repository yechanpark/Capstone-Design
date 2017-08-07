<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>add_DB.jsp</title>
</head>
<body>
	<link rel="stylesheet"
		href="http://code.jquery.com/ui/1.9.2/themes/base/jquery-ui.css" />
	<script type="text/javascript"
		src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
	<script type="text/javascript"
		src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
	<script type="text/javascript" src="jquery.ui.datepicker-ko.js"></script>
	<script type="text/javascript">
		var year, month, day;
		$(document).ready(function() {
			$("#date_text").datepicker({
				onSelect : function(dateText, inst) {
					var dateArr = dateText.split("/");
					month = dateArr[0];
					day = dateArr[1];
					year = dateArr[2];
				}
			});
			//showOn: "button";
		});
	</script>
	<script type="text/javascript">
		var year, month, day;
		$(document).ready(function() {
			$("#date_text2").datepicker({
				onSelect : function(dateText, inst) {
					var dateArr = dateText.split("/");
					month = dateArr[0];
					day = dateArr[1];
					year = dateArr[2];
				}
			});
			//showOn: "button";
		});
	</script>
	<p>포스터 추가</p>
	<form action="Add_Poster_DB" method="post"
		enctype="multipart/form-data">
		Beacon번호: <select name="Beacon_no">
			<option value="1">1</option>
			<option value="2">2</option>
			<option value="3">3</option>
			<option value="4">4</option>
		</select> <br />
		이미지파일 지정<input type="file" name="designated_file" /> <br />
		URL설정(http://제외하고 입력) <input type="text" name="URL" /> <br />
		날짜 <input type="text" id="date_text" size="6" maxlength="10" name="dates">
		<input type="hidden" name="Type" value="Poster" /> <br />
		<input type="submit" value="포스터 추가 실행" />
		<br />
	</form>
	----------------

	<p>설문추가
	<p>
	<form action="Add_Survey_DB" method="post">
		Beacon번호: <select name="Beacon_no">
			<option value="1">1</option>
			<option value="2">2</option>
			<option value="3">3</option>
			<option value="4">4</option>
		</select> <br />
		구글DOCS설문 URL입력 <input type="text" name="URL" /> <br />
		작성자 입력<input type="text" name="Writer" /> <br />
		보상타입 입력<input type="text" name="RewardType" /> <br />
		보상 입력<input type="text" name="Reward" /><br />
		날짜 <input type="text" id="date_text2" size="6" maxlength="10" name="dates">
		<input type="hidden" name="Type" value="Survey" />
		<input type="submit" value="설문 추가 실행" /> <br />
	</form>

	<br />
	<form action="index.jsp" method="post">
		<input type="submit" value="홈으로 이동" /> <br />
	</form>
</body>
</html>