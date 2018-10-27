<%@ page  language="java"  import="twitter.ApiMain"  contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%!String trendID=""; %>
<% trendID = request.getParameter("trend_id"); %>


<h3>Trends Search by ID <%=trendID%> </h3>
<%! ApiMain api = new ApiMain(); %>
<% api.application_only_auth(); %>
<%=ApiMain.apiGetTrends(trendID) %>
</body>
</html>