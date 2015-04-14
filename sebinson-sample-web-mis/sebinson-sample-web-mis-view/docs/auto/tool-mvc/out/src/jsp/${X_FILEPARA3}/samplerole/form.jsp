<%@ page language="java" contentType="text/html; charset=UTF-8"pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
    <form class="edit-form" id="editForm">
        <table class="form-table" id="formTable">
            <input type="hidden" id="roleId" name="roleId"/>               
            <tr>
                <td><label>角色编号：</label></td>
                <td><input class="easyui-textbox" id="roleCode" name="roleCode" value="${data.roleCode}"/></td>
            </tr>
            <tr>
                <td><label>角色名称：</label></td>
                <td><input class="easyui-textbox" id="roleName" name="roleName" value="${data.roleName}"/></td>
            </tr>
            <tr>
                <td><label>角色描述：</label></td>
                <td><input class="easyui-textbox" id="roleDesc" name="roleDesc" value="${data.roleDesc}"/></td>
            </tr>
        </table>
    </form>
</body>
</html>