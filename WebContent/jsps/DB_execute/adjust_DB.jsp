<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>adjust_DB.jsp</title>
</head>
<body>
	DB ����<br/>
	----������----
	<form action="Adjust_Poster_DB" method="post" enctype="multipart/form-data">
		�ٲ� record��ȣ:<input type="text" name="Record_No"><br/>
		���ܹ�ȣ ���� :<select name="Beacon_no">
			<option value="1">1</option>
			<option value="2">2</option>
			<option value="3">3</option>
			<option value="4">4</option>
		</select><br/>
		����Path���� : <input type="file" name="designated_file"/><br/>
		URL����:<input type="text" name="URL"><br/>
		
		<input type="hidden" name="Type" value="Poster">
		<input type="submit" value="������ ���� ����" />
	</form>
		<br/>
		
		
		
	----����----
		<form action="Adjust_Survey_DB" method="post">
		�ٲ� record��ȣ:<input type="text" name="Record_No"><br/>
		���ܹ�ȣ ���� :<select name="Beacon_no">
			<option value="1">1</option>
			<option value="2">2</option>
			<option value="3">3</option>
			<option value="4">4</option>
		</select><br/>

		URL����:<input type="text" name="URL"><br/>
		�ۼ��� ���� : <input type="text" name="Writer"><br/>
		����Ÿ�� ���� : <input type="text" name="RewardType"><br/>
		���� ���� : <input type="text" name="Reward"><br/>
		<input type="hidden" name="Type" value="Survey">
		<input type="submit" value="���� ���� ����" />
	</form>
	
	
	<form action="index.jsp" method="post" >
		<input type="submit" value="Ȩ���� �̵�" /> <br />
	</form>
</body>
</html>