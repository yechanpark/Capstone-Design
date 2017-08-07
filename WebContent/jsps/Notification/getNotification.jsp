<%@ page import="Notification.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="org.json.simple.*"%>	
<%
/*우촌 : 1
미래 : 2
창의 : 3
버스 : 4*/
	JSONObject main = new JSONObject();
	String Beacon = request.getParameter("Beacon");
	int Beacon_num = Integer.parseInt(Beacon);
	Notification n=null;
	
	//우촌, 포스터(url)
	if(Beacon_num==1) n = new PosterNotification();
	
	//미래관, 층별 빈자리 갯수
	else if(Beacon_num==2) n = new LibraryNotification(); 
		
	//창의관, 식단 (오늘거만)
	else if(Beacon_num==3) n = new ParteNotification();
	
	//버스, 얼마나 남았는지 메시지
	else if(Beacon_num==4) n = new BusNotification();
	
	//빈강의실
	else if(Beacon_num==5) n = new EmptyClassNotification();
	
	//설문
	else if(Beacon_num==6) n = new SurveyNotification();
	
	
	main.put("List", n.getJSON());
	out.print(main);
	
%>