<%@ page  language="java"  import="twitter.ApiMain"  contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Retweet</title>
</head>
<body>
	<%!
	String id = ""; 
	%>

	<% id = request.getParameter("tweet_id");%>
	

	<h3>
		Search result by twitter ID
		<%=id %>
		
	</h3>
<%! ApiMain api = new ApiMain(); %>
<% api.application_only_auth(); %>
<%=ApiMain.apiStatusesRetweets(id)%>
</body>
</html>