<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="org.json.simple.*"%>
<%@ page import="Parser.Parse_Poster"%>
<%@ page import="Parser.Parse"%>
<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="org.json.simple.JSONArray"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="org.springframework.beans.factory.BeanFactory"%>
<%@ page import="org.springframework.beans.factory.xml.XmlBeanFactory"%>
<%@ page import="org.springframework.core.io.FileSystemResource"%>
<%@ page import="DB.ConnectionDAO"%>
<%@ page import="java.net.InetAddress"%>

<%
	/*factory 정의, xml파일의 빈네임을 참조한다*/
	BeanFactory factory = new XmlBeanFactory(
			new FileSystemResource("C:/Users/Administrator/workspace/CapstoneDesign/WebContent/db.xml"));

	/*DB커넥션 설정*/
	ConnectionDAO connMaker = factory.getBean("connMaker", ConnectionDAO.class);
	Connection con = connMaker.getConn();
	Statement st = con.createStatement();
	ResultSet rs = st.executeQuery("select Primary_key, Type from test where Type='Poster'"); //쿼리문

	InetAddress loacl = InetAddress.getLocalHost();
	String ip = loacl.getHostAddress();
	
	JSONObject jsonMain= new JSONObject();
	
	JSONArray jArray = new JSONArray();
	for (; rs.next();) {
		JSONObject jObject= new JSONObject();
		int record = rs.getInt(1);
		String Type = rs.getString(2);
		Parse poster = new Parse_Poster(record);
		jObject.put("Type", Type);
		jObject.put("Poster", poster.getJSON());
		jArray.add(jObject);
	}	
	jsonMain.put("List", jArray);
	out.print(jsonMain.toJSONString());
	
	st.close();
	con.close();
	rs.close();
%>