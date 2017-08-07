<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>del_DB.jsp</title>
</head>
<body>
	DB에서 삭제
	*저장된 파일까지 같이 삭제됨
	<form action="Delete_DB" method="post">
	삭제할 레코드번호:<input type="text" name="Record_no"><br /> 
		<input type="submit" value="DB삭제 실행" />
	</form>
	<br/>
	<form action="index.jsp" method="post" >
		<input type="submit" value="홈으로 이동" /> <br />
	</form>
</body>
</html>