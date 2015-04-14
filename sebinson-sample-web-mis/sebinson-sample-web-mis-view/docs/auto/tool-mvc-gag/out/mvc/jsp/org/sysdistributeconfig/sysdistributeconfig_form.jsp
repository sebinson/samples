<%@ page language="java" contentType="text/html; charset=UTF-8"pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<script type="text/javascript" src="${imgPath}/base/mis/org/sysdistributeconfig/sysdistributeconfig_form.js"></script>
    <form class="edit-form" id="editForm">
	<input type="hidden" name="formType" id="formType" value="${formType}"/>
        <table class="form-table" id="formTable">
		<input type="hidden" id="id" name="id" value="${data.id}"/>               
            <tr>
                <td><label>合作机构编号：</label></td>
                <td><input class="easyui-textbox" id="partnerId" name="partnerId"  value="${data.partnerId}" /></td>
            </tr>
            <tr>
                <td><label>业务类型：</label></td>
                <td><input class="easyui-combobox" id="businessType" name="businessType"  value="${data.businessType}" /></td>
            </tr>
            <tr>
                <td><label>分发类型：</label></td>
                <td><input class="easyui-combobox" id="distributeType" name="distributeType"  value="${data.distributeType}" /></td>
            </tr>
            <tr>
                <td><label>分发地址：</label></td>
                <td><input class="easyui-textbox" id="distributeUrl" name="distributeUrl"  value="${data.distributeUrl}" /></td>
            </tr>
            <tr>
                <td><label>机构秘钥：</label></td>
                <td><input class="easyui-textbox" id="encryptKey" name="encryptKey"  value="${data.encryptKey}" /></td>
            </tr>
            <tr>
                <td><label>权限类型：</label></td>
                <td><input class="easyui-combobox" id="authType" name="authType"  value="${data.authType}" /></td>
            </tr>
            <tr>
                <td><label>商家编号：</label></td>
                <td><input class="easyui-textbox" id="shopId" name="shopId"  value="${data.shopId}" /></td>
            </tr>
            <tr>
                <td><label>实体店编号：</label></td>
                <td><input class="easyui-textbox" id="entityId" name="entityId"  value="${data.entityId}" /></td>
            </tr>
            <tr>
                <td><label>品牌：</label></td>
                <td><input class="easyui-textbox" id="brandId" name="brandId"  value="${data.brandId}" /></td>
            </tr>
            <tr>
                <td><label>地区：</label></td>
                <td><input class="easyui-textbox" id="areaId" name="areaId"  value="${data.areaId}" /></td>
            </tr>
            <tr>
                <td><label>是否删除：</label></td>
                <td><input class="easyui-combobox" id="isDel" name="isDel"  value="${data.isDel}" /></td>
            </tr>
            <tr>
                <td><label>创建时间：</label></td>
                <td><input class="easyui-datetimebox" id="createTime" name="createTime"  value="${data.createTime}" /></td>
            </tr>
        </table>
    </form>
