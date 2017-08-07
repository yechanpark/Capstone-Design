<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="org.json.simple.*"%>
<%@ page import="Parser.Parse"%>
<%@ page import="Parser.Parse_Parte"%>
<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="org.json.simple.JSONArray"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="DB.ConnectionDAO"%>
<%@ page import="org.springframework.beans.factory.BeanFactory"%>
<%@ page import="org.springframework.beans.factory.xml.XmlBeanFactory"%>
<%@ page import="org.springframework.core.io.FileSystemResource"%>
<%
	/*factory 정의, xml파일의 빈네임을 참조한다*/
	BeanFactory factory = new XmlBeanFactory(
			new FileSystemResource("C:/Users/Administrator/workspace/CapstoneDesign/WebContent/db.xml"));

	/*DB커넥션 설정*/
	ConnectionDAO connMaker = factory.getBean("connMaker", ConnectionDAO.class);
	Connection con = connMaker.getConn();
	Statement st = con.createStatement();
	ResultSet rs = st.executeQuery("select URL from test where Type='Parte'"); //쿼리문
	rs.next();
	String url = rs.getString(1);
	
	Parse parte = new Parse_Parte(url);
	JSONObject jsonMain = new JSONObject();
	JSONObject jObject = new JSONObject();
	JSONArray jArray = new JSONArray();

	jObject.put("Parte", parte.getJSON());
	jObject.put("Type", "Parte");
	jArray.add(jObject);
	jsonMain.put("List", jArray);
	out.print(jsonMain.toJSONString());
%>