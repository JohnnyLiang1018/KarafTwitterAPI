<%@ page  language="java"  import="twitter.ApiMain"  contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User Time Line</title>
</head>
<body>

<%! String name = "";
	String count ="";
%>

<% name =request.getParameter("user_name"); %>
<% count =request.getParameter("count"); %>



<h3>User time line with user name: <%=name%> <br/>
	Number of time line: <%=count%> <br/>
 </h3>
<%! ApiMain api = new ApiMain(); %>
<% api.application_only_auth(); %>
<%=ApiMain.apiGetUserTimeline(name,count) %>
</body>
</html>