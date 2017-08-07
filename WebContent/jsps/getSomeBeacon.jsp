<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="org.springframework.beans.factory.BeanFactory"%>
<%@ page import="org.springframework.beans.factory.xml.XmlBeanFactory"%>
<%@ page import="org.springframework.core.io.FileSystemResource"%>
<%@ page import="DB.Article"%>
<%@ page import="DB.Path"%>
<%@ page import="DB.ConnectionDAO"%>
<%@ page import="Parser.Parse"%>
<%@ page import="Parser.Parse_Poster"%>
<%@ page import="Parser.Parse_Parte"%>
<%@ page import="Parser.Parse_Library"%>
<%@ page import="Parser.Parse_isEmptyClass"%>
<%@ page import="Parser.Parse_isEmptyClass_inClass"%>
<%@ page import="Parser.Parse_SchoolBus"%>
<%@ page import="Parser.Parse_Survey"%>
<%@ page import="Parser.Parse_Bus"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="org.json.simple.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="org.json.simple.*"%>
<%
	/*factory 정의, xml파일의 빈네임을 참조한다*/
	BeanFactory factory = new XmlBeanFactory(
			new FileSystemResource("C:/Users/Administrator/workspace/CapstoneDesign/WebContent/db.xml"));

	/*DB커넥션 설정*/
	ConnectionDAO connMaker = factory.getBean("connMaker", ConnectionDAO.class);
	Connection con = connMaker.getConn();
	Statement st = con.createStatement();
	ResultSet rs = st.executeQuery("select Beacon, Type, primary_key, URL, Writer, Dates from test"); //쿼리문

	JSONObject jsonMain = new JSONObject();
	JSONArray jArray = new JSONArray();

	//전체순회
	int size = 0;
	for (; rs.next();) {

		/*art에 for가 돌때마다 DB에서 가져온 Article정보를 저장*/
		Article art = new Article();
		art.setBeacon_no(rs.getString(1));
		art.setType(rs.getString(2));
		art.setRecord(rs.getInt(3));
		art.setURL(rs.getString(4));
		art.setWriter(rs.getString(5));
		art.setDates(rs.getString(6));

		JSONObject jObject = new JSONObject();

		//포스터에 대한 DB면
		if (art.getType().equals("Poster")) {
			Parse poster = new Parse_Poster(art.getRecord());
			jObject.put("Poster", poster.getJSON());
		}

		//식단표에 대한 DB면
		if (art.getType().equals("Parte")) {
			Parse parte = new Parse_Parte(art.getURL());
			jObject.put("Parte", parte.getJSON());
		}
		/*
		미래관 열람실에 대한 DB면
		if (art.getType().equals("Library")) {
			Parse lib = new Parse_Library(art.getURL());
			jObject.put("Empty_Library", lib.getJSON());
		}*/

		/*빈강의실 테스트중
		if (art.getType().equals("Siganpyo")) {
			Parse classes = new Parse_isEmptyClass_inClass();
			jObject.put("Empty_Siganpyo", classes.getJSON());
		}*/

		//02번 버스시간표
		if (art.getType().equals("Bus")) {
			Parse bus = new Parse_Bus();
			jObject.put("BusInfos", bus.getJSON());
		}

		//설문조사
		if (art.getType().equals("Survey")) {
			Parse survey = new Parse_Survey(art.getURL(), art.getWriter());
			jObject.put("Survey", survey.getJSON());
			jObject.put("Dates", art.getDates());
		}

		jObject.put("Type", art.getType());
		jArray.add(jObject);
		size++;
	}

	jsonMain.put("List", jArray);
	jsonMain.put("Size", size);
	out.print(jsonMain.toJSONString());

	rs.close();
	st.close();
	con.close();
%>