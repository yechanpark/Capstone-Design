<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="DB.Article"%>
<%@ page import="java.util.ArrayList"%>

	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>search_result.jsp</title>
</head>
<body>
	DB 조회 결과<br/>
	<table border="1">
	<tr>
		<th>Primary_Key<th/>
		<th>Beacon_no<th/>
		<th>Path<th/>
		<th>Source<th/>
		<th>URL<th/>
		<th>Filename<th/>
		<th>Type<th/>
	</tr>
	<%
	ArrayList<Article> articles = (ArrayList<Article>) request.getAttribute("articles");

	for (Article art : articles) {	
		out.print("<tr>");
		out.print("<td>"+art.getRecord()+"<td/>");
		out.print("<td>"+art.getBeacon_no()+"<td/>");
		out.print("<td>"+art.getPath()+"<td/>");
		//out.print("<td><input type=\"image\" src=\""+art.getPath()+"\"width=\"150\" height=\"150\"><td/>");
		out.print("<td><img src=\"/CapstoneDesign/Images/"+art.getFilename()+"\" width=\"50\" hieght=\"50\"/><td/>");
		out.print("<td>"+art.getURL()+"<td/>");
		out.print("<td>"+art.getFilename()+"<td/>");
		out.print("<td>"+art.getType()+"<td/>");
		out.print("<tr/>");
	}
	%>
	<table/>
	<form action="index.jsp" method="post" >
		<input type="submit" value="홈으로 이동" /> <br />
	</form>
</body>
</html>