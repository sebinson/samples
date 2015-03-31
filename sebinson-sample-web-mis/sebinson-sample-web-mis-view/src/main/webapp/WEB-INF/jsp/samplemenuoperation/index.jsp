<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<script type="text/javascript" src="${staticPath}/static/js/samplemenuoperation.index.js"></script>
<link rel="stylesheet" type="text/css" href="${staticPath}/static/css/samplemenuoperation.index.css">
<title>暂定标题</title>
</head>
<body class="easyui-layout">
	<div class="easyui-panel search-panel" region="north">
		<form id="searchForm" class="search-form">
            <div>
                <label>所属菜单标标识：</label>
                <input class="easyui-textbox" name="menuId" />
            </div>
            <div>
                <label>操作码：</label>
                <input class="easyui-textbox" name="operCode" />
            </div>
            <div>
                <label>操作名称：</label>
                <input class="easyui-textbox" name="operName" />
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
                <input type="hidden" name="operId"/>               
                <tr>
                    <td><label>所属菜单标标识：</label></td>
                    <td><input class="easyui-textbox" name="menuId" /></td>
                </tr>
                <tr>
                    <td><label>操作码：</label></td>
                    <td><input class="easyui-textbox" name="operCode" /></td>
                </tr>
                <tr>
                    <td><label>操作名称：</label></td>
                    <td><input class="easyui-textbox" name="operName" /></td>
                </tr>
                <tr>
                    <td><label>操作类型：</label></td>
                    <td><input class="easyui-textbox" name="operType" /></td>
                </tr>
                <tr>
                    <td><label>操作URI：</label></td>
                    <td><input class="easyui-textbox" name="operActions" /></td>
                </tr>
			</table>
		</form>
	</div>
</body>
</html>