<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.1.0.min.js"></script>
</head>


<body>

	<script>
		var params = {
			"Beacon_no" : "1",
			"platform" : "mobile",
			"searching_mode" : "search_Beacon_no"
		};
		$.ajax({
			type : "POST",
			url : "/CapstoneDesign/Search_DB",
			cache : false,
			data : params,
			success : function(request) {

				$("#wrap").append(request);

			}

		});
	</script>

	<div id="wrap"></div>
</body>
</html>