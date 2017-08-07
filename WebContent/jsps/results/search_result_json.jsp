<%@ page import="java.net.InetAddress"%>
<%@ page import="DB.Article"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="org.json.simple.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
ArrayList<Article> articles = (ArrayList<Article>) request.getAttribute("articles");
InetAddress local = InetAddress.getLocalHost();
String ip = local.getHostAddress();
String image_URL = "";


JSONArray ar = new JSONArray();
JSONObject j = new JSONObject();
for (Article art : articles) {	
	j.put("Beacon", art.getBeacon_no());
	j.put("URL", art.getURL());
	image_URL="http://" + ip + "/hansung/jsps/show_image.jsp?&filename=" + art.getFilename();
	j.put("image_URL", image_URL);
	out.println(j);
}

%>