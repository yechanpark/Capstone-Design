<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="org.json.simple.*"%>
<%@ page import="Parser.Parse"%>
<%@ page import="Parser.Parse_isEmptyClass"%>
<%@ page import="Parser.Parse_isEmptyClass_inClass"%>
<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="org.json.simple.JSONArray"%>

<%
	Parse classes = new Parse_isEmptyClass_inClass();
	JSONObject jsonMain = new JSONObject();
	JSONObject jObject = new JSONObject();
	JSONArray jArray = new JSONArray();

	jObject.put("Empty_Siganpyo", classes.getJSON());
	jObject.put("Type", "Siganpyo");
	jArray.add(jObject);
	jsonMain.put("List", jArray);
	out.print(jsonMain.toJSONString());
%>