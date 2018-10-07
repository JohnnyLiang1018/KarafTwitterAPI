<%@ page  language="java"  import="twitter.ApiMain"  contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User Time Line</title>
</head>
<body>
<h3>Followers</h3>
<% ApiMain api = new ApiMain();
   api.application_only_auth();
   api.apiGetUserTimeline("NASA", "2");
%>
</body>
</html>