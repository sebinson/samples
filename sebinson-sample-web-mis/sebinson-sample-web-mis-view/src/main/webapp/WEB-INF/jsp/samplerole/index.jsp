<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<script type="text/javascript" src="${staticPath}/static/js/samplerole.index.js"></script>
<link rel="stylesheet" type="text/css" href="${staticPath}/static/css/samplerole.index.css">
<title>暂定标题</title>
</head>
<body class="easyui-layout">
	<div class="easyui-panel search-panel" region="north">
		<form id="searchForm" class="search-form">
            <div>
                <label>角色编号，规律性编码，要求唯一性：</label>
                <input class="easyui-textbox" name="roleCode" />
            </div>
            <div>
                <label>角色名称：</label>
                <input class="easyui-textbox" name="roleName" />
            </div>
		</form>
        <div class="search-button">
			<a href="javascript:void(0);" id="btn-search" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			<a href="javascript:void(0);" id="btn-reset" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
		</div>
	</div>
	<div class="easyui-panel data-panel" region="center">
		<table id="dataList" class="data-list"></table>
	</div>
	<div id="editWin" class="easyui-dialog edit-win" title="暂定标题" style="width: 800px; height: 400px;"
		data-options="closed:true,resizable:true,draggable:true,modal:true">
		<form class="edit-form" id="editForm">
			<table>
                <input type="hidden" name="roleId"/>               
                <tr>
                    <td><label>角色编号，规律性编码，要求唯一性：</label></td>
                    <td><input class="easyui-textbox" name="roleCode" /></td>
                </tr>
                <tr>
                    <td><label>角色名称：</label></td>
                    <td><input class="easyui-textbox" name="roleName" /></td>
                </tr>
                <tr>
                    <td><label>角色描述：</label></td>
                    <td><input class="easyui-textbox" name="roleDesc" /></td>
                </tr>
			</table>
		</form>
	</div>
</body>
</html>