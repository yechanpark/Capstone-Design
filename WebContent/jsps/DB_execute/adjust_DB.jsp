<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>adjust_DB.jsp</title>
</head>
<body>
	DB 수정<br/>
	----포스터----
	<form action="Adjust_Poster_DB" method="post" enctype="multipart/form-data">
		바꿀 record번호:<input type="text" name="Record_No"><br/>
		비콘번호 변경 :<select name="Beacon_no">
			<option value="1">1</option>
			<option value="2">2</option>
			<option value="3">3</option>
			<option value="4">4</option>
		</select><br/>
		파일Path변경 : <input type="file" name="designated_file"/><br/>
		URL변경:<input type="text" name="URL"><br/>
		
		<input type="hidden" name="Type" value="Poster">
		<input type="submit" value="포스터 수정 실행" />
	</form>
		<br/>
		
		
		
	----설문----
		<form action="Adjust_Survey_DB" method="post">
		바꿀 record번호:<input type="text" name="Record_No"><br/>
		비콘번호 변경 :<select name="Beacon_no">
			<option value="1">1</option>
			<option value="2">2</option>
			<option value="3">3</option>
			<option value="4">4</option>
		</select><br/>

		URL변경:<input type="text" name="URL"><br/>
		작성자 변경 : <input type="text" name="Writer"><br/>
		보상타입 변경 : <input type="text" name="RewardType"><br/>
		보상 변경 : <input type="text" name="Reward"><br/>
		<input type="hidden" name="Type" value="Survey">
		<input type="submit" value="설문 수정 실행" />
	</form>
	
	
	<form action="index.jsp" method="post" >
		<input type="submit" value="홈으로 이동" /> <br />
	</form>
</body>
</html>