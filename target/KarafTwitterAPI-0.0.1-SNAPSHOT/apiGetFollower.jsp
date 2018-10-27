<%@ page  language="java"  import="twitter.ApiMain"  contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%!
	String name = ""; 
	%>

	<% name = request.getParameter("follower_name");%>
	
	<h3>
		Search result by follower name
		<%=name %>
		
	</h3>


<%! ApiMain api = new ApiMain(); %>
<% api.application_only_auth(); %>
<%=ApiMain.apiGetFollowerName(name) %>
</body>
</html>