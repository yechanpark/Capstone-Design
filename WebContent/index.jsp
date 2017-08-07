<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>This is index.jsp</title>
</head>

<body>
	----DB----
	<br/>
	DB 추가
	<form action="Add_Link_DB" method="post">
		<input type="submit" value="insert하러가기" />
	</form>
	<br/>
	DB 삭제(파일 포함)
	<form action="Delete_Link_DB" method="post">
		<input type="submit" value="delete하러가기" />
	</form>
	<br/>
	DB 조회
	<form action="Search_Link_DB" method="post">
		<input type="submit" value="select하러가기" />
	</form>
	<br/>	
	DB 수정
	<form action="Adjust_Link_DB" method="post">
		<input type="submit" value="adjust하러가기" />
	</form>
	----기타----
	<br/>빈강의실 DB갱신
	<form action="GetSiganpyo" method="post">
		<input type="submit" value="빈강의실 DB 갱신" />
	</form>

</body>

</html>