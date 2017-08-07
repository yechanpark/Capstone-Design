<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="org.json.simple.*"%>
<%@ page import="Parser.Parse"%>
<%@ page import="Parser.Parse_Bus"%>
<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="org.json.simple.JSONArray"%>

<%
	Parse bus = new Parse_Bus();
	JSONObject jsonMain= new JSONObject();
	JSONObject jObject= new JSONObject();
	JSONArray jArray = new JSONArray();
	
	jObject.put("BusInfos",bus.getJSON());
	jObject.put("Type","Bus");
	jArray.add(jObject);
	jsonMain.put("List", jArray);
	out.print(jsonMain.toJSONString());
%>