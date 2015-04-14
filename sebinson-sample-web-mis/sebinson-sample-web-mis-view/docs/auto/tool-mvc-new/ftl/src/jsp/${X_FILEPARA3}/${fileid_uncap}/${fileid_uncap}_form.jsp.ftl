<%@ page language="java" contentType="text/html; charset=UTF-8"pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
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
</body>
</html>