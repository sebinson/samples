<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="${staticPath}/static/js/main.js"></script>
<link rel="stylesheet" type="text/css" href="${staticPath}/static/css/main.css">
<title>后台管理系统</title>
</head>
<body class="easyui-layout">
	<div class="main-header" data-options="region:'north',border:false">
		<h1>后台管理系统</h1>
		<div class="main-header-info">
			<div class="main-header-info-login">
				<span class="red">欢迎:</span> |<span class="blue"><b>${user.userId}</b></span> |
				<a class="modify-pwd-btn" href="javascript:void(0);">修改密码</a>
				|
				<a class="logout-btn" href="logout.do">退出</a>
			</div>
		</div>
	</div>
	<div class="main-navigate" data-options="region:'west',split:true,title:'导航菜单'" style="width: 280px;">
		<div class="easyui-accordion" id="menu-box" data-options="fit:true,border:false">
			<c:forEach var="item" items="${menuList}">
				<div title="${item.text}">
					<c:forEach var="node" items="${item.children}">
						<a class="menu-item" href="${basePath}${node.url}">${node.text}</a>
					</c:forEach>
				</div>
			</c:forEach>
		</div>
	</div>
	<div class="main-center" data-options="region:'center'">
		<div class="easyui-tabs" id="tab-box" data-options="fit:true,border:false">
			<div title="主页" data-options="closable:true" style="padding: 20px; overflow: hidden;">Welcome</div>
		</div>
	</div>
	<!--  modify password start -->
	<div id="modify-pwd-win" class="easyui-dialog modify-pwd-win" buttons="#modify-pwd-win-btn" title="修改密码"
		data-options="closed:true,iconCls:'icon-save',modal:true" style="width: 350px; height: 200px;">
		<form class="pwd-form" id="pwdForm" action="modifyPwd" method="post">
			<table>
				<tr>
					<td><label>旧密码:</label></td>
					<td><input id="oldPwd" name="oldPwd" type="password" class="easyui-validatebox textbox"
							data-options="required:true" /></td>
				</tr>
				<tr>
					<td><label>新密码:</label></td>
					<td><input id="newPwd" name="newPwd" type="password" class="easyui-validatebox textbox"
							data-options="required:true,validType: 'length[3,8]'" /></td>
				</tr>
				<tr>
					<td><label>重复新密码:</label></td>
					<td><input id="repwd" name="repwd" type="password" class="easyui-validatebox textbox" required="required"
							validType="equals['#newPwd']" /></td>
				</tr>
			</table>
		</form>
		<div id="modify-pwd-win-btn" class="dialog-button .modify-pwd-win-btn">
			<a href="javascript:void(0);" class="easyui-linkbutton" id="pwd-win-btn-submit">提交</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" id="pwd-win-btn-close">关闭</a>
		</div>
	</div>
</body>
</html>