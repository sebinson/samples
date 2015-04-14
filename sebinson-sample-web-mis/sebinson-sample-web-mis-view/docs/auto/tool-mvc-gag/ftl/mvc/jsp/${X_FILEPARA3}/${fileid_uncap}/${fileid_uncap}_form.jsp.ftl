<%@ page language="java" contentType="text/html; charset=UTF-8"pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<script type="text/javascript" src="${'$'}{imgPath}/base/mis/${X_FILEPARA3}/${fileid_uncap}/${fileid_uncap}_form.js"></script>
    <form class="edit-form" id="editForm">
	<input type="hidden" name="formType" id="formType" value="${'$'}{formType}"/>
        <table class="form-table" id="formTable">
            <#list list as column> 
            <#if column.x_showInForm?lower_case=='y'>
            <#if column.x_cpattern?lower_case=='hidden'>
		<input type="hidden" id="${column.x_cname?uncap_first}" name="${column.x_cname?uncap_first}" value="${'$'}{data.${column.x_cname?uncap_first}}"/>               
            <#else>
            <tr>
                <td><label>${column.x_ccomment}：</label></td>
                <td><#if column.x_cpattern??><input class="easyui-${column.x_cpattern?lower_case}" id="${column.x_cname?uncap_first}" name="${column.x_cname?uncap_first}"  value="${'$'}{data.${column.x_cname?uncap_first}}" /></#if></td>
            </tr>
            </#if>
            </#if>   
            </#list>
        </table>
    </form>
