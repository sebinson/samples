<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<script type="text/javascript" src="${imgPath}/base/mis/org/sysdistributeconfig/sysdistributeconfig_index.js"></script>
<script type="text/javascript" src="${imgPath}/base/mis/org/sysdistributeconfig/sysdistributeconfig_form.js"></script>
</head>
<body class="easyui-layout">
	<div class="easyui-panel search-panel" region="north">
        <fieldset>
            <legend> <span>查询条件</span></legend>
            <form id="searchForm" class="search-form">
                <div>
                    <label>合作机构编号：</label>
                    <input class="easyui-textbox" id="partnerId" name="partnerId" />
                </div>
                <div>
                    <label>业务类型：</label>
                    <input class="easyui-combobox" id="businessType" name="businessType" />
                </div>
                <div>
                    <label>分发类型：</label>
                    <input class="easyui-combobox" id="distributeType" name="distributeType" />
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
		data-options="closed:true,resizable:true,draggable:true,modal:true,onLoad:Gooagoo.sysdistributeconfig.form.onLoad"></div>
</body>
</html>