<%@ page  language="java"  import="twitter.ApiMain"  contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>TrendClosestLocation</title>
</head>
<body>
<h3>TrendClosestLocation</h3>
<%! ApiMain api = new ApiMain(); %>
<% api.application_only_auth(); %>
<%=ApiMain.apiTrendClosest("37.781157","-122.400612831116")%> 
</body>
</html>