<%@ page  language="java"  import="twitter.ApiMain"  contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h3>Hello World</h3>
<%! ApiMain api = new ApiMain(); %>
<% api.application_only_auth(); %>
<%=ApiMain.apiSearch("league%20of%20legends","popular") %>
</body>
</html>