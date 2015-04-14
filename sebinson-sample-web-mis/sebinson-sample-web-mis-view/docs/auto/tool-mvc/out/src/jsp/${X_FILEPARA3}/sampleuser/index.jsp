<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<script type="text/javascript" src="${basePath}/static/js/sampleuser.index.js" />
<script type="text/javascript" src="${basePath}/static/js/sampleuser.form.js" />
</head>
<body class="easyui-layout">
	<div class="easyui-panel search-panel" region="north">
        <fieldset>
            <legend> <span>查询条件</span></legend>
            <form id="searchForm" class="search-form">
                <div>
                    <label>用户名：</label>
                    <input class="easyui-textbox" id="userName" name="userName" />
                </div>
                <div>
                    <label>创建日期：从</label>
                    <input class="easyui-datetimebox" id="cdtF" name="cdtF" />&nbsp;&nbsp;&nbsp;到：
                    <input class="easyui-datetimebox" id="cdtT" name="cdtT" />
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
		data-options="closed:true,resizable:true,draggable:true,modal:true,onLoad:Gooagoo.sampleuser.form.onLoad"/>
</body>
</html>