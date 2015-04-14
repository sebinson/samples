<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<script type="text/javascript" src="${'$'}{basePath}/static/js/${fileid_uncap}.index.js" />
<#if (X_FILEPARA1?index_of("!") > -1)>
<script type="text/javascript" src="${'$'}{basePath}/static/js/${fileid_uncap}.form.js" />
</#if>
</head>
<body class="easyui-layout">
	<div class="easyui-panel search-panel" region="north">
        <fieldset>
            <legend> <span>查询条件</span></legend>
            <form id="searchForm" class="search-form">
                <#list list as column>
                    <#if column.x_searchField?lower_case=='y'>
                <div>
                        <#if column.x_cpattern??>                           
                            <#if column.x_cpattern?contains('date') && column.x_cvalue?? && column.x_cvalue=='FT'>
                    <label>${column.x_ccomment}：从</label>
                    <input class="easyui-${column.x_cpattern?lower_case}" id="${column.x_cname?uncap_first}F" name="${column.x_cname?uncap_first}F" />&nbsp;&nbsp;&nbsp;到：
                    <input class="easyui-${column.x_cpattern?lower_case}" id="${column.x_cname?uncap_first}T" name="${column.x_cname?uncap_first}T" />
                            <#else>
                    <label>${column.x_ccomment}：</label>
                    <input class="easyui-${column.x_cpattern?lower_case}" id="${column.x_cname?uncap_first}" name="${column.x_cname?uncap_first}" />
                            </#if>
                        </#if>  
                </div>
                    </#if>
                </#list>				
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
		data-options="closed:true,resizable:true,draggable:true,modal:true<#if (X_FILEPARA1?index_of("!") > -1)>,onLoad:Gooagoo.${fileid_uncap}.form.onLoad"/</#if>>
    <#if (X_FILEPARA1?index_of("!") <= -1)>
        <form class="edit-form" id="editForm">
            <table class="form-table" id="formTable">
                <#list list as column> 
                <#if column.x_showInForm?lower_case=='y'>
                <#if column.x_cpattern?lower_case=='hidden'>
    <input type="hidden" id="${column.x_cname?uncap_first}" name="${column.x_cname?uncap_first}"/>               
                <#else>
                <tr>
                    <td><label>${column.x_ccomment}：</label></td>
                    <td><#if column.x_cpattern??><input class="easyui-${column.x_cpattern?lower_case}" id="${column.x_cname?uncap_first}" name="${column.x_cname?uncap_first}" value="${'$'}{data.${column.x_cname?uncap_first}}"/></#if></td>
                </tr>
                </#if>
                </#if>   
                </#list>
            </table>
        </form>
    </div>
    </#if>
</body>
</html>