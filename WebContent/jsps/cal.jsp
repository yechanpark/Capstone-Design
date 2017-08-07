<link rel="stylesheet" href="http://code.jquery.com/ui/1.9.2/themes/base/jquery-ui.css" />
<script type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
<script type="text/javascript" src="jquery.ui.datepicker-ko.js"></script>

<script type="text/javascript">
	var year, month, day;
	$(document).ready(function() {
		$("#date_text").datepicker({
			onSelect : function(dateText, inst) {
				var dateArr = dateText.split("/");
				month = dateArr[0];
				day = dateArr[1];
				year = dateArr[2];
			}
		});
		showOn: "button";
	});
</script>


<head>
<title>this is cal.jsp</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>

<body>
	<form action="" method="post">
		<BR> <BR> <BR> <BR> <BR> <BR>
		<table align=center>
			<tr>
				<td>Date</td>
				<td><input type="text" id="date_text" size="6" maxlength="8" name="dates"></td>
			</tr>
		</table>
	</form>
</body>