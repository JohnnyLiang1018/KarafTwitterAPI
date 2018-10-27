<%@ page  language="java"  import="twitter.ApiMain"  contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>TrendClosestLocation</title>
</head>
<body>

<%! String lat = "" ;
	String lon ="";
%>

<% 
	lat = request.getParameter("Lat"); 
	lon = request.getParameter("Lon");

%>

<h3>Trend Closest Location at 
<br/>
Latitude: <%= lat %> </h3>
<br/>

	Longitude: <%=lon %>



<%! ApiMain api = new ApiMain(); %>
<% api.application_only_auth(); %>
<%=ApiMain.apiTrendClosest(lat,lon)%> 
</body>
</html>