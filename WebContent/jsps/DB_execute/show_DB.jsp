<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="org.springframework.beans.factory.BeanFactory"%>
<%@ page import="org.springframework.beans.factory.xml.XmlBeanFactory"%>
<%@ page import="org.springframework.core.io.FileSystemResource"%>
<%@ page import="java.net.InetAddress"%>
<%@ page import="DB.*"%>
<%@ page import="Parser.*"%>
<%@ page import="java.util.ArrayList"%>

<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
<%
	/*factory ����, xml������ ������� �����Ѵ�*/
	BeanFactory factory = new XmlBeanFactory(
			new FileSystemResource("C:/Users/Administrator/workspace/CapstoneDesign/WebContent/db.xml"));

	/*DBĿ�ؼ� ����*/
	ConnectionDAO connMaker = factory.getBean("connMaker", ConnectionDAO.class);
	Connection con = connMaker.getConn();
	Statement st = con.createStatement();
	ResultSet rs = st.executeQuery("select Primary_Key, Beacon, URL, Filename, Type from test"); //������
	%>


	DB ��ȸ ���<br/>
	<table border="1">
	<tr>
		<th>Record<th/>
		<th>Beacon_no<th/> 
		<th>Source<th/>
		<th>URL<th/>
		<th>Filename<th/>
		<th>Type<th/>
	</tr>
	
	<%
	ArrayList<Article> articles = new ArrayList<Article>();
	
	//��ü��ȸ
	for (; rs.next();) {
		
		/*art�� for�� �������� DB���� ������ Article������ ����*/
		Article art = new Article();
		art.setRecord(rs.getInt(1));
		art.setBeacon_no(rs.getString(2));
		art.setURL(rs.getString(3).toString());
		art.setFilename(rs.getString(4).toString());
		art.setType(rs.getString(5).toString());
		
		articles.add(art);
	}
	
	

	for (Article art : articles) {	
		out.print("<tr>");
		out.print("<td>"+art.getRecord()+"<td/>");
		out.print("<td>"+art.getBeacon_no()+"<td/>");
		out.print("<td><img src=\"/CapstoneDesign/Images/"+art.getFilename()+"\" width=\"50\" hieght=\"50\"/><td/>");
		out.print("<td>"+art.getURL()+"<td/>");
		out.print("<td>"+art.getFilename()+"<td/>");
		out.print("<td>"+art.getType()+"<td/>");
		out.print("<tr/>");
	}
	%>
	<table/>
	<form action="show_DB.jsp" method="post" >
		<input type="submit" value="���ΰ�ħ" /> <br />
	</form>
</body>
</html>