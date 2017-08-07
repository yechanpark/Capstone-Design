<%@ page import="DB.Article"%>
<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>delete_result</title>
</head>
<body>
	This is delete_result.jsp
	<br /> --deleted data--
	<br />
	<%
		Article art = (Article) request.getAttribute("article");
		out.print("primary_key :" + art.getRecord() + "<br/>");
		out.print("Beacon_no :" + art.getBeacon_no() + "<br/>");
		out.print("file_Path :" + art.getPath() + "<br/>");
		out.print("URL :" + art.getURL() + "<br/>");
		out.print("image :<input type=\"image\" src=" + art.getPath() + "/>");
		out.print("Type :" + art.getType() + "/>");
	%>

	<form action="index.jsp" method="post">
		<input type="submit" value="홈으로 이동" /> <br />
	</form>
</body>
</html>