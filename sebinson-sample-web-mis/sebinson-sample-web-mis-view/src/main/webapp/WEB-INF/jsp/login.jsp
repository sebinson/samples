<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="${staticPath}/static/js/login.js"></script>
<title>SAMPLE LOGIN</title>
</head>
<body>
	<div id="Win" class="easyui-window" title="后台管理系统登录" style="width: 320px; height: 200px; padding: 5px;"
		minimizable="false" maximizable="false" resizable="false" collapsible="false" closable="false" draggable="false">
		<div class="easyui-layout" fit="true">
			<div region="center" border="false" style="padding: 5px; background: #fff; border: 1px solid #ccc;">
				<form id="loginForm" method="post">
					<div style="padding: 10px 2px;">
						<label for="login">账号:</label> <input class="easyui-validatebox" type="text" id="account" name="account"
							style="width: 220px;" data-options="required:true,validType:'account'" value="${account}"></input>
					</div>
					<div style="padding: 10px 2px;">
						<label for="password">密码:</label> <input class="easyui-validatebox" type="password" name="pwd"
							style="width: 220px;" data-options="required:true,validType: 'length[3,8]'"></input>
					</div>
				</form>
			</div>
			<div region="south" border="false" style="text-align: right; padding: 5px 0;">
				<a class="easyui-linkbutton" iconCls="icon-ok" onclick="Gooagoo.sample.login.toLogin()">登录</a> <a
					class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#loginForm').resetForm();">重置</a>
			</div>
		</div>
	</div>
</body>
</html>