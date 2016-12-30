<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>USER ADD</title>
<%@ include file="../include.jsp"%>
<style type="text/css">
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
.single-control{
	margin-bottom: 3px;
}
.half-height{
	height: 50px;
	text-align: center;
}
</style>
<script type="text/javascript">
//兼容ie9的placeholder
function isPlaceholder(){
	var input = document.createElement('input');
	return 'placeholder' in input;
}
if (!isPlaceholder()) {
	$(document).ready(function() {
		if(!isPlaceholder()){
			$("input").not("input[type='password']").each(function(){
				if($(this).val()=="" && $(this).attr("placeholder")!=""){
					$(this).val($(this).attr("placeholder"));
					$(this).focus(function(){
						if($(this).val()==$(this).attr("placeholder")) $(this).val("");
					});
					$(this).blur(function(){
						if($(this).val()=="") $(this).val($(this).attr("placeholder"));
					});
				}
			});

			$("input[type='password']").each(function() {
				var pwdField = $(this);
				var pwdVal = pwdField.attr('placeholder');
				pwdField.after('<input class="form-control x" type="text" value='+pwdVal+' autocomplete="off" />');
				var pwdPlaceholder = $(this).siblings(".x");
				pwdPlaceholder.show();
				pwdField.hide();
				
				pwdPlaceholder.focus(function(){
				    pwdPlaceholder.hide();
				    pwdField.show();
				    pwdField.focus();
				});
				
				pwdField.blur(function(){
				    if(pwdField.val() == '') {
				        pwdPlaceholder.show();
				        pwdField.hide();
				    }
				});
			});
		}
	});
}

function sendMessage(msg) {
	BootstrapDialog.show({
        message: msg
    });
}

function validateForm() {
	var username = $('#username').val().trim();
	var password = $('#password').val().trim();
	var repassword = $('#repassword').val().trim();
	var birthday = $('#birthday').val().trim();
	var address = $('#address').val().trim();
	
	//验证必填
	if(username == null || username.length == 0) {
		sendMessage('请输入用户名!');return false;
	}
	if(password == null || password.length == 0) {
		sendMessage('请输入密码!');return false;
	}
	if(repassword == null || repassword.length == 0) {
		sendMessage('请输入密码确认!');return false;
	}
	if(birthday == null || birthday.length == 0) {
		sendMessage('请输入出生日期!');return false;
	}
	if(address == null || address.length == 0) {
		sendMessage('请输入家庭住址!');return false;
	}
	
	//验证输入规则
	var usernameReg = /^[a-zA-Z\d]\w{5,11}[a-zA-Z\d]$/;
	if(!usernameReg.test(username)) {
		sendMessage('用户名必须为长度6至12位之间以字母起始并包含数字的字符串组成!');return false;
	}
	if(password != repassword) {
		sendMessage('密码与密码确认必须一致!');return false;
	} 
	if(password.length < 6) {
		sendMessage('密码必须为长度6位(包括6位)以上的字符串组成!');return false;
	}
	var birthdayReg = /^(\d{4})-(0\d{1}|1[0-2])-(0\d{1}|[12]\d{1}|3[01])$/;
	if(!birthdayReg.test(birthday)) {
		sendMessage('出生日期必须为年月日中间以横杠进行连接的字符串组成,例如:1997-07-01');return false;
	}
	
	//验证唯一性
	var unique = true;
	$.ajax({
	    headers: {
	        'Accept': 'application/json',
	        'Content-Type': 'application/json'
	    },
	    cache: false,
        async: false,
	    type: 'POST',
	    url: 'checkUsernameUnique',
	    data: username,
	    dataType: 'json',
	    success: function(data) {
	        if(!data.status) {
	        	unique = false;
	        	sendMessage('该用户名已经被人使用!');
	        } else {
	        	return true;
	        }
	    }
	});
	return unique;
}

function submitForm() {
	if(validateForm()) $('#addUserForm').submit();
}

function addAndReset() {
	if(validateForm()) {        
		$.ajax({
		    headers: {
		        'Accept': 'application/json',
		        'Content-Type': 'application/json'
		    },
		    type: 'POST',
		    url: 'addAndReset',
		    data: "{\"username\":\"" + $('#username').val().trim() + "\",\"password\":" + $('#password').val().trim() + ",\"birthday\":\"" + $('#birthday').val().trim() + "\",\"sex\":" + $('#sex').val().trim() + ",\"address\":\"" + $('#address').val().trim() + "\"}",
		    //dataType: 'json',
		    success: function(status) {
		        if(status) {
		        	sendMessage('用户添加成功!');
		        	$('#addUserForm')[0].reset();
		        } else {
		        	sendMessage('用户添加失败!');
		        }
		    }
		});
	}
}
</script>
</head>

<body>
	<h1>ADD</h1>
	<hr>
	<a href="list">用户列表</a>
	<div class="containter vertical-center">
		<form id="addUserForm" name="addUserForm" action="add" method="POST" class="form-signin">
			<input type="text" id="username" name="username" class="form-control single-control" autofocus placeholder="用户名">
			<input type="password" id="password" name="password" class="form-control single-control x" placeholder="密码">
			<input type="password" id="repassword" name="repassword" class="form-control single-control x" placeholder="密码确认">
			<div class="input-group date form_date single-control">
				<input class="form-control time-picker" id="birthday" name="birthday" type="text" placeholder="出生日期">
				<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
			</div>
			<select id="sex" name="sex" class="form-control single-control">
				<option value="true">男</option>
				<option value="false">女</option>
			</select>
			<textarea id="address" name="address" class="form-control single-control" rows="3" placeholder="家庭住址"></textarea>
			<button type="button" class="btn btn-primary" style="width: 49%" onclick="submitForm()">添加用户</button>
			<button type="button" class="btn btn-danger" style="width: 49%" onclick="addAndReset()">添加并重置</button>
		</form>
	</div>

<script type="text/javascript">
$('.form_date').datetimepicker({
    language: 'zh-CN',
	format: "yyyy-mm-dd",
    weekStart: 1,
    todayBtn:  1,
	autoclose: 1,
	todayHighlight: 1,
	startView: 2,
	minView: 2,
	forceParse: 0,
	pickerPosition: "bottom-left"
});
</script>

</body>
</html>
