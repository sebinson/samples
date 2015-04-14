<%@ page language="java" contentType="text/html; charset=UTF-8"pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
    <form class="edit-form" id="editForm">
        <table class="form-table" id="formTable">
            <input type="hidden" id="menuId" name="menuId"/>               
            <tr>
                <td><label>菜单编码：</label></td>
                <td><input class="easyui-textbox" id="menuCode" name="menuCode" value="${data.menuCode}"/></td>
            </tr>
            <tr>
                <td><label>菜单类型：</label></td>
                <td><input class="easyui-combobox" id="menuType" name="menuType" value="${data.menuType}"/></td>
            </tr>
            <tr>
                <td><label>菜单名称：</label></td>
                <td><input class="easyui-textbox" id="menuName" name="menuName" value="${data.menuName}"/></td>
            </tr>
            <tr>
                <td><label>菜单状态：</label></td>
                <td><input class="easyui-combobox" id="menuStatus" name="menuStatus" value="${data.menuStatus}"/></td>
            </tr>
            <tr>
                <td><label>上级菜单标识：</label></td>
                <td><input class="easyui-textbox" id="menuParentId" name="menuParentId" value="${data.menuParentId}"/></td>
            </tr>
            <tr>
                <td><label>同级菜单序号：</label></td>
                <td><input class="easyui-textbox" id="menuOrder" name="menuOrder" value="${data.menuOrder}"/></td>
            </tr>
            <tr>
                <td><label>菜单URI：</label></td>
                <td><input class="easyui-textbox" id="menuUri" name="menuUri" value="${data.menuUri}"/></td>
            </tr>
            <tr>
                <td><label>操作项URI：</label></td>
                <td><input class="easyui-textbox" id="menuActions" name="menuActions" value="${data.menuActions}"/></td>
            </tr>
            <tr>
                <td><label>菜单描述：</label></td>
                <td><input class="easyui-textbox" id="menuDesc" name="menuDesc" value="${data.menuDesc}"/></td>
            </tr>
        </table>
    </form>
</body>
</html>