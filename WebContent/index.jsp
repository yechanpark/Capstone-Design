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
	DB �߰�
	<form action="Add_Link_DB" method="post">
		<input type="submit" value="insert�Ϸ�����" />
	</form>
	<br/>
	DB ����(���� ����)
	<form action="Delete_Link_DB" method="post">
		<input type="submit" value="delete�Ϸ�����" />
	</form>
	<br/>
	DB ��ȸ
	<form action="Search_Link_DB" method="post">
		<input type="submit" value="select�Ϸ�����" />
	</form>
	<br/>	
	DB ����
	<form action="Adjust_Link_DB" method="post">
		<input type="submit" value="adjust�Ϸ�����" />
	</form>
	----��Ÿ----
	<br/>���ǽ� DB����
	<form action="GetSiganpyo" method="post">
		<input type="submit" value="���ǽ� DB ����" />
	</form>

</body>

</html>