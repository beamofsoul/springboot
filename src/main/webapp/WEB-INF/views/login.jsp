<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="include.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Login Page</title>
<style>  
.vertical-center{
	position: absolute;
	top: 40%;
	left: 50%;
	-webkit-transform: translate(-50%, -50%);
	-moz-transform:  translate(-50%, -50%);
	-ms-transform:  translate(-50%, -50%);
	-o-transform:  translate(-50%, -50%);
	transform: translate(-50%, -50%);
}
.form-signin{
	max-width: 330px;
	padding: 15px;
	margin: 0 auto;
}
input{
	margin-bottom: 3px;
}
.half-height{
	height: 50px;
	text-align: center;
}
</style> 
</head>
<body>
<div class="container vertical-center">
	<form id="form1" name="form1" method="POST" action="login" class="form-signin">
			<input type="text"  class="form-control" id="username" name="username" required autofocus placeholder="用户名" value="tom" />
			<input type="password" class="form-control x" id="password" name="password" required placeholder="密码" value="123456" />
			<button type="submit" class="btn btn-lg btn-danger btn-block">登录</button>
	</form>
	<hr>
	<div class="alert half-height">
		<c:set var="message" value="${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}" />
		<c:set var="expired" value="${expired}" />
		<c:choose>
			<c:when test="${not empty message}">
				<c:out value="${ message }" />
			</c:when>
			<c:otherwise>
				<c:if test="${not empty expired}">
					<c:out value="${expired }" />
				</c:if>
				<c:out value=" " />
			</c:otherwise>
		</c:choose>
	</div>
	</div>
</body>
</html>
