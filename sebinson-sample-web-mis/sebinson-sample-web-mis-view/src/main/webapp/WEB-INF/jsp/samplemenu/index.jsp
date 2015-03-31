<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<script type="text/javascript" src="${staticPath}/static/js/samplemenu.index.js"></script>
<link rel="stylesheet" type="text/css" href="${staticPath}/static/css/samplemenu.index.css">
<title>暂定标题</title>
</head>
<body class="easyui-layout">
	<div class="easyui-panel search-panel" region="north">
		<form id="searchForm" class="search-form">
            <div>
                <label>菜单编码：</label>
                <input class="easyui-textbox" name="menuCode" />
            </div>
            <div>
                <label>菜单类型：0 根菜单，1 级菜单，2 菜单项：</label>
                <input class="easyui-combobox" name="menuType" />
            </div>
            <div>
                <label>菜单名称：</label>
                <input class="easyui-textbox" name="menuName" />
            </div>
            <div>
                <label>菜单状态：0 可用，1 不可用：</label>
                <input class="easyui-combobox" name="menuStatus" />
            </div>
            <div>
                <label>上级菜单标识：</label>
                <input class="easyui-textbox" name="menuParentId" />
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
                <input type="hidden" name="menuId"/>               
                <tr>
                    <td><label>菜单编码：</label></td>
                    <td><input class="easyui-textbox" name="menuCode" /></td>
                </tr>
                <tr>
                    <td><label>菜单类型：0 根菜单，1 级菜单，2 菜单项：</label></td>
                    <td><input class="easyui-combobox" name="menuType" /></td>
                </tr>
                <tr>
                    <td><label>菜单名称：</label></td>
                    <td><input class="easyui-textbox" name="menuName" /></td>
                </tr>
                <tr>
                    <td><label>菜单状态：0 可用，1 不可用：</label></td>
                    <td><input class="easyui-combobox" name="menuStatus" /></td>
                </tr>
                <tr>
                    <td><label>上级菜单标识：</label></td>
                    <td><input class="easyui-textbox" name="menuParentId" /></td>
                </tr>
                <tr>
                    <td><label>同级菜单序号：</label></td>
                    <td><input class="easyui-textbox" name="menuOrder" /></td>
                </tr>
                <tr>
                    <td><label>菜单URI：</label></td>
                    <td><input class="easyui-textbox" name="menuUri" /></td>
                </tr>
                <tr>
                    <td><label>操作项的相对URI，权限随当前菜单，不可定制授权，多操作以'|'分割 eg：add.do|remove.do|update.do：</label></td>
                    <td><input class="easyui-textbox" name="menuActions" /></td>
                </tr>
                <tr>
                    <td><label>菜单描述：</label></td>
                    <td><input class="easyui-textbox" name="menuDesc" /></td>
                </tr>
			</table>
		</form>
	</div>
</body>
</html>