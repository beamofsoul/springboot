<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="include.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>WELCOME</title>
</head>
<body class="container">
	<h1>${CURRENT_USER.username }</h1>
	<hr>
		<a href="user/list">用户列表</a>
	<hr>
	<a href="logout">注销</a>
</body>
</html>
