<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<script type="text/javascript" src="${basePath}/static/js/samplemenu.index.js" />
<script type="text/javascript" src="${basePath}/static/js/samplemenu.form.js" />
</head>
<body class="easyui-layout">
	<div class="easyui-panel search-panel" region="north">
        <fieldset>
            <legend> <span>查询条件</span></legend>
            <form id="searchForm" class="search-form">
                <div>
                    <label>菜单编码：</label>
                    <input class="easyui-textbox" id="menuCode" name="menuCode" />
                </div>
                <div>
                    <label>菜单类型：</label>
                    <input class="easyui-combobox" id="menuType" name="menuType" />
                </div>
                <div>
                    <label>菜单名称：</label>
                    <input class="easyui-textbox" id="menuName" name="menuName" />
                </div>
                <div>
                    <label>菜单状态：</label>
                    <input class="easyui-combobox" id="menuStatus" name="menuStatus" />
                </div>
                <div>
                    <label>上级菜单标识：</label>
                    <input class="easyui-textbox" id="menuParentId" name="menuParentId" />
                </div>
            </form>
            <div class="search-button">
                <a href="javascript:void(0);" id="btn-search" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                <a href="javascript:void(0);" id="btn-reset" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
            </div>
        </fieldset>
	</div>
	<div class="easyui-panel data-panel" region="center">
		<table id="dataList" class="data-list"></table>
	</div>
	<div class="easyui-dialog edit-win" id="editWin" style="width: 800px; height: 600px;"
		data-options="closed:true,resizable:true,draggable:true,modal:true,onLoad:Gooagoo.samplemenu.form.onLoad"/>
</body>
</html>