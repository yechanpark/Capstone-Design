<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>search_DB.jsp</title>
</head>
<body>
	DB ��ȸ
	<br />
	-��ü����-
	<form action="Search_DB" method="post">
		<input type="submit" value="���� ��ȸ ����" />
		<input type="hidden" value="search_All" name="searching_mode" />
		<input type="hidden" value="pc" name="platform"/>
		
	</form>
	<br />
	<br />
	<br />

	-���� ��ȣ�� ã��-
	<form action="Search_DB" method="post">
		<input type="submit" value="���ܹ�ȣ�� ��ȸ ����" />
		<select name="Beacon_no">
			<option value="1">1</option>
			<option value="2">2</option>
			<option value="3">3</option>
			<option value="4">4</option>
		</select> 
		<input type="hidden" value="search_Beacon_no" name="searching_mode"/>
		<input type="hidden" value="pc" name="platform"/>
	</form>

		<br/>
	<form action="./index.jsp" method="post">
		<input type="submit" value="Ȩ���� �̵�" />
	</form>
</body>
</html>