<%@ page  language="java"  import="twitter.ApiMain"  contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Users</title>
</head>
<body>
<%!
	String userName = ""; 
	%>

	<% userName = request.getParameter("user_name");%>
	<h3>
		Search result by User Name
		<%=userName %>
		</h3>

<%! ApiMain api = new ApiMain(); %>
<% api.application_only_auth(); %>
<%=ApiMain.apiGetFollowerName(userName)%>
</body>
</html>