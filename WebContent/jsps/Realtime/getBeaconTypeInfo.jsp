<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="org.json.simple.*"%>
<%@ page import="Parser.Parse"%>
<%@ page import="Parser.Parse_Survey"%>
<%@ page import="DB.ConnectionDAO"%>
<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="org.json.simple.JSONArray"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="org.springframework.beans.factory.BeanFactory"%>
<%@ page import="org.springframework.beans.factory.xml.XmlBeanFactory"%>
<%@ page import="org.springframework.core.io.FileSystemResource"%>

<%
	String Beacon = request.getParameter("Beacon");

	JSONArray jArray = new JSONArray();
	JSONObject jsonMain = new JSONObject();

	/*factory 정의, xml파일의 빈네임을 참조한다*/
	BeanFactory factory = new XmlBeanFactory(
			new FileSystemResource("C:/Users/Administrator/workspace/CapstoneDesign/WebContent/db.xml"));

	/*DB커넥션 설정*/
	ConnectionDAO connMaker = factory.getBean("connMaker", ConnectionDAO.class);
	Connection con = connMaker.getConn();
	Statement st = con.createStatement();
	ResultSet rs = st.executeQuery("select Type from test where Beacon='" + Beacon + "'"); //쿼리문

	for (; rs.next();) {
		JSONObject jObject = new JSONObject();
		jObject.put("Type", rs.getString(1));
		jArray.add(jObject);
	}

	jsonMain.put("List", jArray);
	out.print(jsonMain.toJSONString());

	con.close();
	st.close();
	rs.close();
%>